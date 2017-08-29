.. code-tab:: python

    from xenon import (
        Server, PasswordCredential, CopyMode, Path, JobDescription)

    with Server() as xenon:
        #
        # step 1: upload input  files
        #

        # create the local filesystem representation
        local_fs = xenon.create_file_system(adaptor='file')

        # the remote system requires credentials, create them here:
        credential = PasswordCredential(
                username='xenon',
                password='javagat')

        # create the remote filesystem representation and specify the
        # executable's path
        remote_fs = xenon.create_file_system(
                adaptor='sftp',
                location='localhost:10022',
                passwordCred=credential)

        # when waiting for jobs or copy operations to complete, wait
        # indefinitely
        WAIT_INDEFINITELY = 0

        # specify the path of the script file on the local and on the remote
        local_file = Path('/home/tutorial/xenon/sleep.sh')
        remote_file = Path('/home/xenon/sleep.sh')

        # start the copy operation; no recursion, we're just copying a file
        copy_id = local_fs.copy(local_file, remote_fs, remote_file,
                                mode=CopyMode.REPLACE, recursive=False)

        # wait for the copy operation to complete (successfully or otherwise)
        copy_status = local_fs.wait_until_done(copy_id, WAIT_INDEFINITELY)
        if copy_status.error:
            raise RuntimeError(copy_status.error)

        #
        # step 2: submit job and capture its job identifier
        #
        scheduler = xenon.create_scheduler(
                adaptor='slurm',
                location='localhost:10022',
                passwordCred=credential)

        # compose the job description:
        job_description = JobDescription(
                executable='bash',
                arguments=['sleep.sh', '60'],
                stdout='sleep.stdout.txt')

        job_id = scheduler.submit_batch_job(job_description)

        # wait for the job to finish before attempting to copy its output
        # file(s)
        job_status = scheduler.wait_until_done(jobId, WAIT_INDEFINITELY)

        # rethrow the Exception if we got one
        if job_status.errorMessage:
            raise RuntimeError(job_status.errorMessage)

        #
        # step 3: download generated output file(s)
        #

        # specify the path of the stdout file on the remote and on the local
        # machine
        remote_file = Path('/home/xenon/sleep.stdout.txt')
        local_file = Path('/home/tutorial/xenon/sleep.stdout.txt')

        # start the copy operation; no recursion, we're just copying a file
        copy_id = remote_fs.copy(remote_file, local_fs, local_file,
                                 mode=CopyMode.REPLACE, recursive=False)

        # wait for the copy operation to complete (successfully or otherwise)
        copy_status = remote_fs.wait_until_done(copy_id, WAIT_INDEFINITELY)

        # rethrow the Exception if we got one
        if copy_status.error:
            raise RuntimeError(copy_status.error)

        local_fs.close()
        remote_fs.close()
        scheduler.close()
        print('Done')

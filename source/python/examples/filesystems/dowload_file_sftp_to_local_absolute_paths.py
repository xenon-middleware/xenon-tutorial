.. code-tab:: python

    from xenon import Server, PasswordCredential, CopyMode, Path

    with Server() as xenon:
        # use the sftp file system adaptor to create a file system
        # representation; the remote filesystem requires credentials to log in,
        # so we'll have to create those too.
        credential = PasswordCredential(
                username='xenon',
                password='javagat')

        remote_fs = xenon.create_file_system(
                adaptor='sftp',
                location='localhost:10022',
                credential=credential)

        # use the local file system adaptor to create a file system
        # representation
        local_fs = xenon.create_file_system(
                adaptor='file')

        # define which file to download
        remote_file = Path('/home/xenon/sleep.stdout.txt')

        # define what file to download to
        local_dir = Path('/home/tutorial/xenon/')
        local_file = local_dir.joinpath(remote_file.name)

        # create the destination file only if the destination path doesn't
        # exist yet
        mode = CopyMode.CREATE

        # no need to recurse, we're just downloading a file
        recursive = False

        # perform the copy/download and wait 1000 ms for the successful or
        # otherwise completion of the operation
        copy_id = remote_fs.copy(
                remote_file, local_fs, local_file,
                mode=mode, recursive=recursive)
        copy_status = remote_fs.wait_until_done(copy_id, timeout=1000)

        if copy_status.error:
            print(copy_status.error)
        else:
            print('Done')

        # remember to close the FileSystem instances, or use them as
        # context managers
        remote_fs.close()
        local_fs.close()

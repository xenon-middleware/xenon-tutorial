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

        # define which file to upload
        local_file = Path('/home/tutorial/xenon/sleep.sh')
        remote_path = Path('/home/xenon/')
        remote_file = remote_path.joinpath(local_file.name)

        # create the destination file only if the destination path doesn't
        # exist yet
        mode = CopyMode.CREATE

        # no need to recurse, we're just downloading a file
        recursive = False

        # perform the copy/upload and wait 1000 ms for the successful or
        # otherwise completion of the operation
        copy_id = local_fs.copy(
                local_file, remote_fs, remote_file,
                mode=mode, recursive=recursive)
        copy_status = local_fs.wait_until_done(copy_id, timeout=1000)

        if copy_status.error:
            print(copy_status.error)
        else:
            print('Done')

        # remember to close the FileSystem instances, or use them as
        # context managers
        remote_fs.close()
        local_fs.close()

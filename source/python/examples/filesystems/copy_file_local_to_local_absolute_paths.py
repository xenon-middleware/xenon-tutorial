.. code-tab:: python

    from xenon import (Server, FileSystem, Path, CopyMode)

    with Server() as xenon:
        # use the local file system adaptor to create a file system
        # representation
        filesystem = xenon.create_file_system(adaptor='file')

        # create Paths for the source and destination files, using absolute
        # paths
        source_file = filesystem.path('/home/tutorial/xenon/thefile.txt')
        dest_file = filesystem.path('/home/tutorial/xenon/thefile.bak')

        # create the destination file only if the destination path doesn't
        # exist yet; perform the copy and wait 1000 ms for the successful or
        # otherwise completion of the operation
        copy_id = source_file.copy(
                dest_file, mode=CopyMode.CREATE, recursive=False)
        timeout_milli_secs = 1000
        copy_status = copy_id.wait_until_done(timeout_milli_secs)

        if copy_status.error:
            print(copy_status.error)

        filesystem.close()

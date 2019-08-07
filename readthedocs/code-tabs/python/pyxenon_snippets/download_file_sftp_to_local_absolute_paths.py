import xenon
from xenon import FileSystem, PasswordCredential, CopyRequest, Path, CopyStatus


def run_example():

    xenon.init(log_level='INFO')

    # use the sftp file system adaptor to create a file system
    # representation; the remote filesystem requires credentials to log in,
    # so we'll have to create those too.
    credential = PasswordCredential(username='xenon',
                                    password='javagat')

    remote_fs = FileSystem.create(adaptor='sftp',
                                  location='localhost:10022',
                                  password_credential=credential)

    # use the local file system adaptor to create another file system representation
    local_fs = FileSystem.create(adaptor='file')

    # define which file to download
    remote_file = Path('/home/xenon/sleep.stdout.txt')
    local_file = Path('/home/travis/sleep.stdout.txt')

    # create the destination file only if the destination path doesn't exist yet
    mode = CopyRequest.CREATE

    # no need to recurse, we're just uploading a file
    recursive = False

    # perform the copy/download and wait 1000 ms for the successful or
    # otherwise completion of the operation
    copy_id = remote_fs.copy(remote_file, local_fs, local_file,
                             mode=mode, recursive=recursive)

    copy_status = remote_fs.wait_until_done(copy_id, timeout=5000)

    assert copy_status.done

    # rethrow the Exception if we got one
    assert copy_status.error_type == CopyStatus.ErrorType.NONE, copy_status.error_message

    # remember to close the FileSystem instances
    remote_fs.close()
    local_fs.close()

    print('Done')


if __name__ == '__main__':
    run_example()

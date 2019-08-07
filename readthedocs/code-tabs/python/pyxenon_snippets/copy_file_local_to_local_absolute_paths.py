import xenon
from xenon import Path, FileSystem, CopyRequest, CopyStatus


def run_example():

    xenon.init()

    filesystem = FileSystem.create(adaptor='file')
    source_file = Path("/home/travis/thefile.txt")
    dest_file = Path("/home/travis/thefile.bak")

    # start the copy operation; no recursion, we're just copying a file
    copy_id = filesystem.copy(source_file, filesystem, dest_file, mode=CopyRequest.CREATE, recursive=False)

    # wait this many milliseconds for copy operations to complete
    wait_duration = 1000 * 60 * 10

    # wait for the copy operation to complete (successfully or otherwise)
    copy_status = filesystem.wait_until_done(copy_id, wait_duration)

    assert copy_status.done

    # rethrow the Exception if we got one
    assert copy_status.error_type == CopyStatus.ErrorType.NONE, copy_status.error_message

    filesystem.close()


if __name__ == '__main__':
    run_example()

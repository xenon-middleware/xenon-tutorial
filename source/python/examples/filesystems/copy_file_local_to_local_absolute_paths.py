import xenon
from xenon import Path, FileSystem, CopyRequest, CopyStatus

xenon.init()

filesystem = FileSystem.create(adaptor='file')
source_file = Path("/home/daisycutter/tmp/home/tutorial/xenon/thefile.txt")
dest_file = Path("/home/daisycutter/tmp/home/tutorial/xenon/thefile.bak")

# start the copy operation; no recursion, we're just copying a file
copy_id = filesystem.copy(source_file, filesystem, dest_file, mode=CopyRequest.CREATE, recursive=False)

# wait this many milliseconds for copy operations to complete
WAIT_DURATION = 1000 * 60 * 10

# wait for the copy operation to complete (successfully or otherwise)
copy_status = filesystem.wait_until_done(copy_id, WAIT_DURATION)
assert copy_status.done

# rethrow the Exception if we got one
assert copy_status.error_type == CopyStatus.NONE, copy_status.error_message

filesystem.close()

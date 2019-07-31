import xenon
from xenon import Path, FileSystem

xenon.init()

filesystem = FileSystem.create(adaptor='file')
path = Path("/home/alice/fixtures")

listing = filesystem.list(path, recursive=False)

for entry in listing:
    if not entry.path.is_hidden():
        print(entry.path)

filesystem.close()
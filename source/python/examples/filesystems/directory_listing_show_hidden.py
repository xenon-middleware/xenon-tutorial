import xenon
from xenon import Path, FileSystem

xenon.init()

filesystem = FileSystem.create(adaptor='file')
path = Path("/home/tutorial/xenon")

listing = filesystem.list(path, recursive=False)

for entry in listing:
    print(entry.path)

filesystem.close()

import xenon
from xenon import Path, FileSystem


def run_example():
    xenon.init()

    filesystem = FileSystem.create(adaptor='file')
    path = Path("/home/travis/fixtures")

    listing = filesystem.list(path, recursive=False)

    for entry in listing:
        print(entry.path)

    filesystem.close()


if __name__ == '__main__':
    run_example()

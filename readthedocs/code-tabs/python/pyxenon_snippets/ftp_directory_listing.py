import xenon
from xenon import Path, FileSystem, PasswordCredential


def run_example():

    xenon.init()

    credential = PasswordCredential(username='demo',
                                    password='password')

    filesystem = FileSystem.create(adaptor='ftp', location='test.rebex.net', credential=credential)
    path = Path("/home/travis/fixtures")

    listing = filesystem.list(path, recursive=False)

    for entry in listing:
        if not entry.path.is_hidden():
            print(entry.path)

    filesystem.close()


if __name__ == '__main__':
    run_example()

.. code-tab:: python

    from xenon import Server, Path

    with Server() as xenon:
        filesystem = xenon.create_file_system(adaptor='file')

        path = Path('/home/tutorial/xenon')
        listing = filesystem.list(path, recursive=False)

        for entry in listing:
            print(entry)

        filesystem.close()

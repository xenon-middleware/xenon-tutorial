.. code-tab:: python

    from xenon import Server, Path

    with Server() as xenon:
        filesystem = xenon.create_file_system(adaptor='file')
        path = Path("/home/tutorial/xenon")

        for entry in filesystem.list(path, recursive=True):
            if not entry.is_hidden():
                print(entry)

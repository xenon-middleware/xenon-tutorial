.. code-tab:: java

    from xenon import Server

    with Server() as xenon:
        filesystem = xenon.create_file_system(adaptor='file')
        path = filesystem.path("/home/tutorial/xenon")

        for entry in path.list(recursive=False):
            print(entry)

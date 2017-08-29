.. code-tab:: python

    from xenon import Server, Path, PasswordCredential

    with Server() as xenon:
        credential = PasswordCredential(
                username='xenon',
                password='javagat')

        filesystem = xenon.create_file_system(
                adaptor='sftp',
                location='localhost:10022',
                credential=credential)

        path = Path('/home/xenon')
        listing = filesystem.list(path, recursive=False)

        for entry in listing:
            print(entry)

        filesystem.close()

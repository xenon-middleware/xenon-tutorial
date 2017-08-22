.. code-tab:: python

    from xenon import Server, PasswordCredential

    with Server() as xenon:
        location = 'localhost:10022'
        credential = PasswordCredential(
                username='xenon',
                password='javagat')

        scheduler = xenon.create_scheduler(
                adaptor='slurm',
                location=location,
                credential=credential)

        print("Available queues (starred queue is default):")
        default_queue = scheduler.get_default_queue_name()
        for queue_name in scheduler.get_queues():
            if queue_name == default_queue:
                print("{}*".format(queue_name))
            else:
                print(queue_name)

        scheduler.close()

.. code-tab:: python

    from xenon import Server, PasswordCredential

    with Server() as xenon:
        credential = PasswordCredential(
                username='xenon',
                password='javagat')
        location = 'localhost:10022'
        scheduler = xenon.create_scheduler(
                adaptor='slurm',
                location=location,
                credential=credential)

        for queue_name in scheduler.get_queue_names():
            print("List of jobs in queue {queue_name} for SLURM at {location}"
                  .format(queue_name=queue_name, location=location))
            jobs = scheduler.get_jobs(queue_name)
            if not jobs:
                print("(No jobs)")
            else:
                for job in jobs:
                    print("    {}".format(job))

        scheduler.close()

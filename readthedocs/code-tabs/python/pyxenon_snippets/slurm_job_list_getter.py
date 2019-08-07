import xenon
from xenon import PasswordCredential, Scheduler


def run_example():

    xenon.init()

    credential = PasswordCredential(username='xenon',
                                    password='javagat')

    location = 'ssh://localhost:10022'

    scheduler = Scheduler.create(adaptor='slurm',
                                 location=location,
                                 password_credential=credential)

    for queue_name in scheduler.get_queue_names():
        print("List of jobs in queue {queue_name} for SLURM at {location}"
              .format(queue_name=queue_name, location=location))
        jobs = scheduler.get_jobs([queue_name])
        if not jobs:
            print("(No jobs)")
        else:
            for job in jobs:
                print("    {}".format(job))

    scheduler.close()


if __name__ == '__main__':
    run_example()

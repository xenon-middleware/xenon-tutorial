import xenon
from xenon import Scheduler, PasswordCredential


def run_example():
    xenon.init()

    credential = PasswordCredential(username='xenon',
                                    password='javagat')

    scheduler = Scheduler.create(adaptor='slurm',
                                 location='ssh://localhost:10022',
                                 password_credential=credential)

    default_queue = scheduler.get_default_queue_name()

    print("Available queues (starred queue is default):")
    for queue_name in scheduler.get_queue_names():
        if queue_name == default_queue:
            print("{}*".format(queue_name))
        else:
            print(queue_name)

    scheduler.close()


if __name__ == '__main__':
    run_example()

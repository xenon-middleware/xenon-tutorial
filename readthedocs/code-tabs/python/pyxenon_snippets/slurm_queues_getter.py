import subprocess

import xenon
from xenon import Scheduler, PasswordCredential
from xenon.compat import find_xenon_grpc_jar
from xenon.create_keys import create_self_signed_cert


def run_example():

    jar_file = find_xenon_grpc_jar()
    if not jar_file:
        raise RuntimeError("Could not find 'xenon-grpc' jar file.")

    cmd = ['java', '-jar', jar_file, '-vvvvvv']

    crt_file, key_file = create_self_signed_cert()

    cmd.extend([
        '--server-cert-chain', str(crt_file),
        '--server-private-key', str(key_file),
        '--client-cert-chain', str(crt_file)])

    subprocess.Popen(
        cmd,
        bufsize=1,
        universal_newlines=True,
        stdout=subprocess.PIPE,
        stderr=subprocess.PIPE)

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

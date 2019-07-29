package nl.esciencecenter.xenon.examples;

import nl.esciencecenter.xenon.XenonException;
import nl.esciencecenter.xenon.credentials.Credential;
import nl.esciencecenter.xenon.credentials.PasswordCredential;
import nl.esciencecenter.xenon.schedulers.Scheduler;

public class SlurmQueuesGetter {
    public static void main(String[] args) throws XenonException {

        String adaptor = "slurm";
        String location = "ssh://localhost:10022";

        // make the password credential for user 'xenon'
        String username = "xenon";
        String password = "javagat";
        Credential credential = new PasswordCredential(username, password);

        // create the SLURM scheduler
        try (Scheduler scheduler = Scheduler.create(adaptor, location, credential)) {
            // ask SLURM for its queues
            System.out.println("Available queues (starred queue is default):");
            for (String queueName : scheduler.getQueueNames()) {
                if (queueName.equals(scheduler.getDefaultQueueName())) {
                    System.out.println(queueName + "*");
                } else {
                    System.out.println(queueName);
                }
            }
        }
    }
}

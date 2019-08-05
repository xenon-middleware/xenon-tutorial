package nl.esciencecenter.xenon.tutorial;

import java.util.Map;

import nl.esciencecenter.xenon.XenonException;
import nl.esciencecenter.xenon.credentials.Credential;
import nl.esciencecenter.xenon.credentials.PasswordCredential;
import nl.esciencecenter.xenon.schedulers.Scheduler;

public class SlurmQueuesGetter {


    public static void main(String[] args) throws XenonException {

        String host = "localhost";
        String port = "10022";
        Map<String, String> properties = null;

        runExample(host, port, properties);
    }

    public static void runExample(String host, String port, Map<String, String> properties) throws XenonException {

        String adaptor = "slurm";
        String location = "ssh://" + host + ":" + port;

        // make the password credential for user 'xenon'
        String username = "xenon";
        char[] password = "javagat".toCharArray();
        Credential credential = new PasswordCredential(username, password);

        // create the SLURM scheduler
        try (Scheduler scheduler = Scheduler.create(adaptor, location, credential, properties)) {
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




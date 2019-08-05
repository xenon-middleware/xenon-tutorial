package nl.esciencecenter.xenon.tutorial;

import java.util.Map;

import nl.esciencecenter.xenon.XenonException;
import nl.esciencecenter.xenon.credentials.PasswordCredential;
import nl.esciencecenter.xenon.schedulers.Scheduler;

public class SlurmJobListGetter {

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
        PasswordCredential credential = new PasswordCredential(username, password);

        // create the SLURM scheduler
        Scheduler scheduler = Scheduler.create(adaptor, location, credential, properties);

        // ask SLURM for its queues and list the jobs in each
        for (String queueName : scheduler.getQueueNames()) {
            System.out.println("List of jobs in queue '" + queueName + "' for SLURM at " + location + "");
            String[] jobs = scheduler.getJobs(queueName);
            if (jobs.length == 0) {
                System.out.println("(No jobs)");
            } else {
                for (String job : jobs) {
                    System.out.println(job);
                }
            }
        }
    }
}

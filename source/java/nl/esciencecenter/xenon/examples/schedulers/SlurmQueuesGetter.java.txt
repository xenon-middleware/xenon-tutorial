.. code-tab:: java

    import nl.esciencecenter.xenon.credentials.PasswordCredential;
    import nl.esciencecenter.xenon.schedulers.Scheduler;

    public class SlurmQueuesGetter {

        public static void main(String[] args) throws Exception {

            String adaptor = "slurm";
            String location = "localhost:10022";

            // make the password credential for user 'xenon'
            String username = "xenon";
            char[] password = "javagat".toCharArray();
            PasswordCredential credential = new PasswordCredential(username, password);

            // create the SLURM scheduler
            Scheduler scheduler = Scheduler.create(adaptor, location, credential);

            // ask SLURM for its queues
            System.out.println("Available queues (starred queue is default):");
            for (String queueName : scheduler.getQueueNames()) {
                if (queueName == scheduler.getDefaultQueueName()) {
                    System.out.println(queueName + "*");
                } else {
                    System.out.println(queueName);
                }
            }
        }
    }

import nl.esciencecenter.xenon.credentials.PasswordCredential;
import nl.esciencecenter.xenon.filesystems.CopyMode;
import nl.esciencecenter.xenon.filesystems.FileSystem;
import nl.esciencecenter.xenon.filesystems.Path;
import nl.esciencecenter.xenon.schedulers.JobDescription;
import nl.esciencecenter.xenon.schedulers.Scheduler;

public class AllTogetherNowWrong {

    public static void main(String[] args) throws Exception {

        /*
         * step 1: upload input file(s)
         */

        // create the local filesystem representation
        String fileAdaptorLocal = "file";
        FileSystem filesystemLocal = FileSystem.create(fileAdaptorLocal);

        // the remote system requires credentials, create them here:
        String username = "xenon";
        char[] password = "javagat".toCharArray();
        PasswordCredential credential = new PasswordCredential(username, password);

        // create the remote filesystem representation and specify the executable's path
        String fileAdaptorRemote = "sftp";
        String filesystemRemoteLocation = "ssh://localhost:10022";
        FileSystem filesystemRemote = FileSystem.create(fileAdaptorRemote,
                filesystemRemoteLocation, credential);

        {
            // specify the behavior in case the target path exists already
            CopyMode copyMode = CopyMode.CREATE;

            // no recursion, we're just copying a file
            boolean recursive = false;

            // specify the path of the script file on the local and on the remote
            Path fileLocal = new Path("/home/alice/sleep.sh");
            Path fileRemote = new Path("/home/xenon/sleep.sh");

            // start the copy operation
            filesystemLocal.copy(fileLocal, filesystemRemote, fileRemote, copyMode, recursive);

        }



        /*
         * step 2: submit job and capture its job identifier
         */

        // create the SLURM scheduler representation
        String schedulerAdaptor = "slurm";
        String schedulerLocation = "localhost:10022";
        Scheduler scheduler = Scheduler.create(schedulerAdaptor, schedulerLocation, credential);

        // compose the job description:
        JobDescription jobDescription = new JobDescription();
        jobDescription.setExecutable("bash");
        jobDescription.setArguments("sleep.sh", "60");
        jobDescription.setStdout("sleep.stdout.txt");

        scheduler.submitBatchJob(jobDescription);


        /*
         *  step 3: download generated output file(s)
         */

        {
            // specify the behavior in case the target path exists already
            CopyMode copyMode = CopyMode.CREATE;

            // no recursion, we're just copying a file
            boolean recursive = false;

            // specify the path of the stdout file on the remote and on the local machine
            Path fileRemote = new Path("/home/xenon/sleep.stdout.txt");
            Path fileLocal = new Path("/home/tutorial/xenon/sleep.stdout.txt");

            // start the copy operation
            filesystemRemote.copy(fileRemote, filesystemLocal, fileLocal, copyMode, recursive);

        }

        System.out.println("Done.");

    }
}

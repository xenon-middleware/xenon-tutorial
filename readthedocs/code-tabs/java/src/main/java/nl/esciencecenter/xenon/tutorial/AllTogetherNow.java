package nl.esciencecenter.xenon.tutorial;

import nl.esciencecenter.xenon.XenonException;
import nl.esciencecenter.xenon.credentials.PasswordCredential;
import nl.esciencecenter.xenon.filesystems.CopyMode;
import nl.esciencecenter.xenon.filesystems.CopyStatus;
import nl.esciencecenter.xenon.filesystems.FileSystem;
import nl.esciencecenter.xenon.filesystems.Path;
import nl.esciencecenter.xenon.schedulers.JobDescription;
import nl.esciencecenter.xenon.schedulers.JobStatus;
import nl.esciencecenter.xenon.schedulers.Scheduler;

public class AllTogetherNow {

    public static void main(String[] args) throws XenonException {

        String host = "localhost";
        String port = "10022";

        runExample(host, port);
    }

    public static void runExample(String host, String port) throws XenonException {

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
        String filesystemRemoteLocation = host + ":" + port;
        FileSystem filesystemRemote = FileSystem.create(fileAdaptorRemote,
                filesystemRemoteLocation, credential);

        // when waiting for jobs or copy operations to complete, wait indefinitely
        final long WAIT_INDEFINITELY = 0;

        {
            // specify the behavior in case the target path exists already
            CopyMode copyMode = CopyMode.REPLACE;

            // no recursion, we're just copying a file
            boolean recursive = false;

            // specify the path of the script file on the local and on the remote
            Path fileLocal = new Path("/home/travis/sleep.sh");
            Path fileRemote = new Path("/home/xenon/sleep.sh");

            // start the copy operation
            String copyId = filesystemLocal.copy(fileLocal, filesystemRemote, fileRemote, copyMode, recursive);

            // wait for the copy operation to complete (successfully or otherwise)
            CopyStatus status = filesystemLocal.waitUntilDone(copyId, WAIT_INDEFINITELY);

            // rethrow the Exception if we got one
            if (status.hasException()) {
                throw status.getException();
            }

        }



        /*
         * step 2: submit job and capture its job identifier
         */

        // create the SLURM scheduler representation
        String schedulerAdaptor = "slurm";
        String schedulerLocation = "ssh://" + host + ":" + port;
        Scheduler scheduler = Scheduler.create(schedulerAdaptor, schedulerLocation, credential);

        // compose the job description:
        JobDescription jobDescription = new JobDescription();
        jobDescription.setExecutable("bash");
        jobDescription.setArguments("sleep.sh", "60");
        jobDescription.setStdout("sleep.stdout.txt");

        String jobId = scheduler.submitBatchJob(jobDescription);

        // wait for the job to finish before attempting to copy its output file(s)
        JobStatus jobStatus = scheduler.waitUntilDone(jobId, WAIT_INDEFINITELY);

        // rethrow the Exception if we got one
        if (jobStatus.hasException()) {
            throw jobStatus.getException();
        }


        /*
         *  step 3: download generated output file(s)
         */

        {
            // specify the behavior in case the target path exists already
            CopyMode copyMode = CopyMode.REPLACE;

            // no recursion, we're just copying a file
            boolean recursive = false;

            // specify the path of the stdout file on the remote and on the local machine
            Path fileRemote = new Path("/home/xenon/sleep.stdout.txt");
            Path fileLocal = new Path("/home/travis/sleep.stdout.txt");

            // start the copy operation
            String copyId = filesystemRemote.copy(fileRemote, filesystemLocal, fileLocal, copyMode, recursive);

            // wait for the copy operation to complete (successfully or otherwise)
            CopyStatus status = filesystemRemote.waitUntilDone(copyId, WAIT_INDEFINITELY);

            // rethrow the Exception if we got one
            if (status.hasException()) {
                throw status.getException();
            }

        }

        System.out.println("Done.");

    }
}

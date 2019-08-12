package nl.esciencecenter.xenon.tutorial;

import java.util.Map;

import nl.esciencecenter.xenon.XenonException;
import nl.esciencecenter.xenon.credentials.PasswordCredential;
import nl.esciencecenter.xenon.filesystems.CopyMode;
import nl.esciencecenter.xenon.filesystems.CopyStatus;
import nl.esciencecenter.xenon.filesystems.FileSystem;
import nl.esciencecenter.xenon.filesystems.Path;

public class DownloadFileSftpToLocalAbsolutePathsWebdavEquivalent {

    public static void main(String[] args) throws XenonException {

        String host = "localhost";
        String port = "10080";
        Map<String, String> properties = null;

        runExample(host, port, properties);
    }

    public static void runExample(String host, String port, Map<String, String> properties) throws XenonException {


        // use the sftp file system adaptor to create a file system representation; the remote
        // filesystem requires credentials to log in, so we'll have to create those too.
        String adaptorRemote = "webdav";
        String location = "http://" + host + ":" + port;
        String username = "xenon";
        char[] password = "javagat".toCharArray();
        PasswordCredential credential = new PasswordCredential(username, password);
        FileSystem filesystemRemote = FileSystem.create(adaptorRemote, location, credential, properties);

        // define which file to download
        Path fileRemote = new Path("/~xenon/sleep.stdout.txt");

        // use the local file system adaptor to create a file system representation
        String adaptorLocal = "file";
        FileSystem filesystemLocal = FileSystem.create(adaptorLocal);

        // define what file to download to
        Path fileLocal = new Path("/home/travis/sleep.stdout.txt");

        // create the destination file only if the destination path doesn't exist yet
        CopyMode mode = CopyMode.CREATE;

        // no need to recurse, we're just downloading a file
        boolean recursive = false;

        // perform the copy/download and wait 1000 ms for the successful or otherwise
        // completion of the operation
        String copyId = filesystemRemote.copy(fileRemote, filesystemLocal, fileLocal, mode, recursive);
        long timeoutMilliSecs = 1000;
        CopyStatus copyStatus = filesystemRemote.waitUntilDone(copyId, timeoutMilliSecs);

        // print any exceptions
        if (copyStatus.getException() != null) {
            System.out.println(copyStatus.getException().getMessage());
        } else {
            System.out.println("Done");
        }
    }
}

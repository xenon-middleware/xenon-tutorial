package nl.esciencecenter.xenon.tutorial;

import nl.esciencecenter.xenon.credentials.PasswordCredential;
import nl.esciencecenter.xenon.filesystems.FileSystem;
import nl.esciencecenter.xenon.filesystems.Path;
import nl.esciencecenter.xenon.filesystems.PathAttributes;

public class SFTPDirectoryListingShowHidden {

    public static void main(String[] args) throws Exception {

        String adaptor = "sftp";
        String location = "test.rebex.net";
        String username = "demo";
        String password = "password";

        try (FileSystem filesystem = FileSystem.create(adaptor, location, new PasswordCredential(username, password))) {

           Path dir = new Path("/");
           boolean recursive = false;

           Iterable<PathAttributes> listing = filesystem.list(dir, recursive);

           for (PathAttributes elem : listing) {
               System.out.println(elem.getPath());
           }
        }
    }
}

package nl.esciencecenter.xenon.examples;

import nl.esciencecenter.xenon.filesystems.FileSystem;
import nl.esciencecenter.xenon.filesystems.Path;
import nl.esciencecenter.xenon.filesystems.PathAttributes;


public class DirectoryListing {

    public static void main(String[] args) throws Exception {

        String adaptor = "file";
        FileSystem filesystem = FileSystem.create(adaptor);
        Path directory = new Path("/home/alice/fixtures");
        Boolean recursive = false;
        Iterable<PathAttributes> listing = filesystem.list(directory, recursive);

        for (PathAttributes elem : listing) {
            if (!elem.isHidden()) {
                System.out.println(elem.getPath());
            }
        }
    }
}
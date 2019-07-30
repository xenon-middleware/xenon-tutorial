.. code-tab:: java

    import nl.esciencecenter.xenon.filesystems.FileSystem;
    import nl.esciencecenter.xenon.filesystems.Path;
    import nl.esciencecenter.xenon.filesystems.PathAttributes;
    import nl.esciencecenter.xenon.credentials.PasswordCredential;

    public class DirectoryListingWithPasswordCredential {

        public static void main(String[] args) throws Exception {

            String username = "xenon";
            char[] password = "javagat".toCharArray();
            PasswordCredential credential = new PasswordCredential(username, password);

            String adaptor = "sftp";
            String location = "localhost:10022";

            FileSystem filesystem = FileSystem.create(adaptor, location, credential);

            Path dir = new Path("/home/xenon");
            boolean recursive = false;

            Iterable<PathAttributes> listing = filesystem.list(dir, recursive);

            for (PathAttributes elem : listing) {
                if (!elem.isHidden()) {
                    System.out.println(elem.getPath());
                }
            }

        }
    }

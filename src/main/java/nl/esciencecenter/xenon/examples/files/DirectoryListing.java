/**
 * Copyright 2013 Netherlands eScience Center
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package nl.esciencecenter.xenon.examples.files;

import java.net.URI;
import java.net.URISyntaxException;

import nl.esciencecenter.xenon.Xenon;
import nl.esciencecenter.xenon.XenonException;
import nl.esciencecenter.xenon.XenonFactory;
import nl.esciencecenter.xenon.files.DirectoryStream;
import nl.esciencecenter.xenon.files.FileAttributes;
import nl.esciencecenter.xenon.files.FileSystem;
import nl.esciencecenter.xenon.files.Files;
import nl.esciencecenter.xenon.files.Path;
import nl.esciencecenter.xenon.files.RelativePath;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * An example of how to list a directory.
 * 
 * This example assumes the user provides the URI with the location of the directory on the command line.
 * 
 * @version 1.0
 * @since 1.0
 */
public class DirectoryListing {

	private static final Logger LOGGER = LoggerFactory.getLogger(DirectoryListing.class);
	
    public static void main(String[] args) {

        LOGGER.info("Starting " + DirectoryListing.class.getSimpleName() + "..."); 
        
        if (args.length != 1) {
            LOGGER.error("Example requires a URI as parameter!");
            System.exit(1);
        }

        try {
            // We first turn the user provided argument into a URI.
        	LOGGER.debug("Reading the URI...");
            URI uri = new URI(args[0]);

            // We create a new Xenon using the XenonFactory (without providing any properties).
            LOGGER.debug("Creating a Xenon...");
            Xenon xenon = XenonFactory.newXenon(null);

            // Next, we retrieve the Files and Credentials interfaces
            LOGGER.debug("Getting the Files interface...");
            Files files = xenon.files();

            // Next we create a FileSystem. Note that both credential and properties are null (which means: use default)
            LOGGER.debug("Creating a FileSystem...");
            String scheme = uri.getScheme();
            String auth = uri.getAuthority();
            FileSystem fs = files.newFileSystem(scheme, auth, null, null);

            // We now create an Path representing the directory we want to list.
            LOGGER.debug("Creating a new Path...");
            Path path = files.newPath(fs, new RelativePath(uri.getPath()));

            // Retrieve the attributes of the file.
            LOGGER.debug("Getting the directory attributes...");
            FileAttributes att = files.getAttributes(path);

            // Retrieve the attributes of the files in the directory.
            if (att.isDirectory()) {
        		
            	String s = "";
            	String indent = "         ";
        		
        		s += "Directory " + uri + " exists and contains the following:\n";

                DirectoryStream<Path> stream = files.newDirectoryStream(path);

                for (Path p : stream) {
                    s += indent + p.getRelativePath().getFileNameAsString() + "\n";
                }
                
                LOGGER.info(s);

            } else if (att.isRegularFile()) {
            	LOGGER.error(uri + " is a file.");
            } else {
                LOGGER.error("Directory " + uri + " does not exist or is not a directory.");
            }

            // If we are done we need to close the FileSystem
            LOGGER.debug("Closing the File System to free resources...");
            files.close(fs);

            // Finally, we end Xenon to release all resources 
            LOGGER.debug("Closing the Xenon instance to free resources...");
            XenonFactory.endXenon(xenon);
            
            LOGGER.info(DirectoryListing.class.getSimpleName() + " completed.");

        } catch (URISyntaxException | XenonException e) {
            LOGGER.error("DirectoryListing example failed: " + e.getMessage());
            e.printStackTrace();
        }
    }
}

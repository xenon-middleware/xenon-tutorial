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
package nl.esciencecenter.xenon.examples.jobs;

import java.net.URI;
import java.net.URISyntaxException;

import nl.esciencecenter.xenon.Xenon;
import nl.esciencecenter.xenon.XenonException;
import nl.esciencecenter.xenon.XenonFactory;
import nl.esciencecenter.xenon.jobs.Job;
import nl.esciencecenter.xenon.jobs.JobDescription;
import nl.esciencecenter.xenon.jobs.JobStatus;
import nl.esciencecenter.xenon.jobs.Jobs;
import nl.esciencecenter.xenon.jobs.Scheduler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * An example of how to retrieve a list of jobs from a scheduler.
 * 
 * This example assumes the user provides a URI with the scheduler location on
 * the command line.
 *
 * Note: this example assumes the job is submitted to a Linux machine, as it 
 * tries to run "/bin/sleep".
 *
 * @version 1.0
 * @since 1.0
 */
public class ListJobs {

	final static Logger LOGGER = LoggerFactory.getLogger(ListJobs.class);
	
    public static void main(String[] args) {

    	LOGGER.info(ListJobs.class.getSimpleName() + " starting...");
    	
        if (args.length != 1) {
        	LOGGER.error("Example required a scheduler URI as a parameter!");
            System.exit(1);
        }

        try {

        	LOGGER.debug("Convert the command line location to a URI...");
            URI location = new URI(args[0]);

            LOGGER.debug("Create a new Xenon instance...");
            Xenon xenon = XenonFactory.newXenon(null);

            LOGGER.debug("Retrieve the Jobs API...");
            Jobs jobs = xenon.jobs();

            LOGGER.debug("Creating a scheduler to run the job...");
            Scheduler scheduler = jobs.newScheduler(location.getScheme(), location.getAuthority(), null, null);

            LOGGER.debug("Creating the job description...");
            JobDescription description = new JobDescription();
            description.setExecutable("/bin/sleep");
            description.setArguments("20");
            
            LOGGER.debug("Submitting the job...");
            Job job = jobs.submitJob(scheduler, description);

            LOGGER.debug("Retrieving all jobs from all queues...");
            Job[] result = jobs.getJobs(scheduler);
            
            
            String resultString = "";
            String indent = "         ";
            resultString  +=  "The scheduler at " + location + " has " + result.length + " jobs:" + "\n";

            for (Job j : result) {
            	resultString  +=  indent  + j.getIdentifier() + "\n";
            }

            LOGGER.info(resultString);
            
            
            int waitDurationMilliSeconds = 60 * 1000;
            LOGGER.debug("Start waiting for a maximum of " + waitDurationMilliSeconds / 1000 + " seconds");
        	JobStatus jobStatus = jobs.waitUntilDone(job, waitDurationMilliSeconds);

        	LOGGER.debug("Closing the scheduler to free up resources...");
            jobs.close(scheduler);

            LOGGER.debug("Ending the Xenon instance to free up resources..."); 
            XenonFactory.endXenon(xenon);

            if (jobStatus.isDone()) {
            	LOGGER.info(ListJobs.class.getSimpleName() + " completed successfully.");
            }
        	else {
        		LOGGER.info(ListJobs.class.getSimpleName() + " timed out but otherwise completed successfully.");
        	}
            
            
        } catch (URISyntaxException | XenonException e) {
            LOGGER.error(ListJobs.class.getSimpleName() + " example failed: " + e.getMessage());
            e.printStackTrace();
        }
    }
}

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
 * An example of how to create and submit a simple batch job that does not produce output. 
 * 
 * This example assumes the user provides a URI with the scheduler location on the command line.
 * 
 * Note: this example assumes the job is submitted to a machine Linux machine, as it tries to run "/bin/sleep".
 * 
 * @version 1.0
 * @since 1.0
 */
public class SubmitSimpleBatchJob {

	final static Logger LOGGER = LoggerFactory.getLogger(SubmitSimpleBatchJob.class);
	
    public static void main(String[] args) {
        try {
        	
        	LOGGER.info("Starting the " + SubmitSimpleBatchJob.class.getSimpleName() + " example.");
        	
            LOGGER.debug("Convert the command line parameter to a URI...");
            URI location = new URI(args[0]);

            LOGGER.debug("Creating a new Xenon...");
            Xenon xenon = XenonFactory.newXenon(null);

            LOGGER.debug("Retrieving the Jobs API...");
            Jobs jobs = xenon.jobs();

            LOGGER.debug("Creating a JobDescription for the job we want to run...");
            JobDescription description = new JobDescription();
            description.setExecutable("/bin/sleep");
            description.setArguments("5");

            LOGGER.debug("Creating a scheduler to run the job...");
            Scheduler scheduler = jobs.newScheduler(location.getScheme(), location.getAuthority(), null, null);

            LOGGER.debug("Submitting the job...");
            Job job = jobs.submitJob(scheduler, description);

            int nMilliSecondsWait = 60000;
            LOGGER.debug("Waiting for the job to finish using timeout of " + nMilliSecondsWait / 1000 + " seconds..."); 
            JobStatus status = jobs.waitUntilDone(job, nMilliSecondsWait);

            LOGGER.debug("Checking if the job was successful..."); 
            if (!status.isDone()) {
                LOGGER.info("Job timed out but otherwise completed successfully.");
            } else if (status.hasException()) {
                Exception e = status.getException();
                LOGGER.info("Job produced an exception: " + e.getMessage());
                e.printStackTrace();
            } else {
            	LOGGER.info("Job ran succesfully!");
            }

            LOGGER.debug("Closing the scheduler to free up resources...");
            jobs.close(scheduler);

            LOGGER.debug("Ending Xenon to release all resources..."); 
            XenonFactory.endXenon(xenon);
            
            LOGGER.info(SubmitSimpleBatchJob.class.getSimpleName() + " completed.");

        } catch (URISyntaxException | XenonException e) {
            LOGGER.error(SubmitSimpleBatchJob.class.getSimpleName() + " example failed: " + e.getMessage());
            e.printStackTrace();
        }
    }
}

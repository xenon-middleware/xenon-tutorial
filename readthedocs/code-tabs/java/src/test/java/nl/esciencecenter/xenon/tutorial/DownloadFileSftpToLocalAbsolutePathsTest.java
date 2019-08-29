package nl.esciencecenter.xenon.tutorial;

import java.io.IOException;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.testcontainers.containers.FixedHostPortGenericContainer;
import org.testcontainers.containers.wait.strategy.Wait;


public class DownloadFileSftpToLocalAbsolutePathsTest {

    private final String image = "xenonmiddleware/slurm:17";
    private final String host = "localhost";
    private final String port = "10022";

    @Rule
    public FixedHostPortGenericContainer<?> slurm = new FixedHostPortGenericContainer<>(image)
                                                        .withFixedExposedPort(Integer.parseInt(port), 22)
                                                        .waitingFor(Wait.forHealthcheck());

    @Before
    public void setUp() throws UnsupportedOperationException, IOException, InterruptedException {
        // test tries to download this file, so we need to make it first
        slurm.execInContainer("touch", "/home/xenon/sleep.stdout.txt");
    }

    @Test(expected = Test.None.class)
    public void test1() throws Exception {
        DownloadFileSftpToLocalAbsolutePaths.runExample(host, port);
    }

}
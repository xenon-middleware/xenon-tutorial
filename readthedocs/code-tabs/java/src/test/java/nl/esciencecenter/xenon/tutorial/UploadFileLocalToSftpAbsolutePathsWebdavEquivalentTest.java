package nl.esciencecenter.xenon.tutorial;

import org.junit.Rule;
import org.junit.Test;
import org.testcontainers.containers.FixedHostPortGenericContainer;
import org.testcontainers.containers.wait.strategy.Wait;


public class UploadFileLocalToSftpAbsolutePathsWebdavEquivalentTest {

    private final String image = "xenonmiddleware/webdav";
    private final String host = "localhost";
    private final String port = "10080";

    @Rule
    public FixedHostPortGenericContainer<?> slurm = new FixedHostPortGenericContainer<>(image)
                                                        .withFixedExposedPort(Integer.parseInt(port), 80)
                                                        .waitingFor(Wait.forHealthcheck());

    @Test(expected = Test.None.class)
    public void test1() throws Exception {
        UploadFileLocalToSftpAbsolutePathsWebdavEquivalent.runExample(host, port);
    }

}

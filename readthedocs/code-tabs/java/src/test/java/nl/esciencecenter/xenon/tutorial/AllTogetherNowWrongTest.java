package nl.esciencecenter.xenon.tutorial;

import org.junit.Rule;
import org.junit.Test;
import org.testcontainers.containers.FixedHostPortGenericContainer;
import org.testcontainers.containers.wait.strategy.Wait;


public class AllTogetherNowWrongTest {

    private final String image = "xenonmiddleware/slurm:17";
    private final String host = "localhost";
    private final String port = "10022";

    @Rule
    public FixedHostPortGenericContainer<?> slurm = new FixedHostPortGenericContainer<>(image)
                                                        .withFixedExposedPort(Integer.parseInt(port), 22)
                                                        .waitingFor(Wait.forHealthcheck());


    @Test(expected = Test.None.class)
    public void test1() throws Exception {
        AllTogetherNowWrong.runExample(host, port);
    }


}

package nl.esciencecenter.xenon.tutorial;

import org.junit.Rule;
import org.junit.Test;
import org.testcontainers.containers.FixedHostPortGenericContainer;


public class AllTogetherNowNoPropsTest {

    private final String host = "localhost";
    private final int port = 10022;

    @Rule
    public FixedHostPortGenericContainer<?> slurm = new FixedHostPortGenericContainer<>("xenonmiddleware/slurm:17")
                                                        .withFixedExposedPort(port, 22);

    @Test(expected = Test.None.class)
    public void test1() throws Exception {
        AllTogetherNowNoProps.runExample(host, Integer.toString(port));
    }

}

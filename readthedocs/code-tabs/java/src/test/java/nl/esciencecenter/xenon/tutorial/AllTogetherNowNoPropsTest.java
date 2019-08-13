package nl.esciencecenter.xenon.tutorial;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.testcontainers.containers.FixedHostPortGenericContainer;


public class AllTogetherNowNoPropsTest {

    private String host;
    private final String port = "10022";

    @Rule
    public FixedHostPortGenericContainer<?> slurm = new FixedHostPortGenericContainer<>("xenonmiddleware/slurm:17").withFixedExposedPort(10022, 22);

    @Before
    public void setUp() {
        host = slurm.getContainerIpAddress();
    }

    @Test(expected = Test.None.class)
    public void test1() throws Exception {
        AllTogetherNowNoProps.runExample(host, port);
    }

}

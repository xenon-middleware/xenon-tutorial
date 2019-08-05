package nl.esciencecenter.xenon.tutorial;

import java.util.HashMap;
import java.util.Map;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.testcontainers.containers.GenericContainer;


public class SlurmJobListGetterTest {

    private String host;
    private String port;
    private Map<String, String> properties;

    @Rule
    public GenericContainer<?> slurm = new GenericContainer<>("nlesc/xenon-slurm:17").withExposedPorts(22);

    @Before
    public void setUp() {
        host = slurm.getContainerIpAddress();
        port = Integer.toString(slurm.getFirstMappedPort());
        properties = new HashMap<String, String>();
        properties.put("xenon.adaptors.schedulers.ssh.loadSshConfig", "false");
        properties.put("xenon.adaptors.schedulers.ssh.loadKnownHosts", "false");
    }

    @Test(expected = Test.None.class)
    public void test1() throws Exception {
        SlurmJobListGetter.runExample(host, port, properties);
    }

}

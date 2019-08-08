package nl.esciencecenter.xenon.tutorial;

import java.util.HashMap;
import java.util.Map;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.testcontainers.containers.GenericContainer;


public class AllTogetherNowTest {

    private String host;
    private String port;
    private Map<String, String> propertiesSsh;
    private Map<String, String> propertiesSftp;

    @Rule
    public GenericContainer<?> slurm = new GenericContainer<>("xenonmiddleware/slurm:17").withExposedPorts(22);

    @Before
    public void setUp() {
        host = slurm.getContainerIpAddress();
        port = Integer.toString(slurm.getFirstMappedPort());

        propertiesSsh = new HashMap<String, String>();
        propertiesSsh.put("xenon.adaptors.schedulers.ssh.loadSshConfig", "false");
        propertiesSsh.put("xenon.adaptors.schedulers.ssh.loadKnownHosts", "false");

        propertiesSftp = new HashMap<String, String>();
        propertiesSftp.put("xenon.adaptors.filesystems.sftp.loadSshConfig", "false");
        propertiesSftp.put("xenon.adaptors.filesystems.sftp.loadKnownHosts", "false");

    }

    @Test(expected = Test.None.class)
    public void test1() throws Exception {
        AllTogetherNow.runExample(host, port, propertiesSsh, propertiesSftp);
    }

}

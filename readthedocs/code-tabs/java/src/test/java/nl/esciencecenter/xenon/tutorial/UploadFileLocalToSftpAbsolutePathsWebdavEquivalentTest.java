package nl.esciencecenter.xenon.tutorial;

import java.util.HashMap;
import java.util.Map;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.testcontainers.containers.GenericContainer;


public class UploadFileLocalToSftpAbsolutePathsWebdavEquivalentTest {

    private String host;
    private String port;
    private Map<String, String> properties;

    @Rule
    public GenericContainer<?> webdav = new GenericContainer<>("xenonmiddleware/webdav").withExposedPorts(80);

    @Before
    public void setUp() {
        host = webdav.getContainerIpAddress();
        port = Integer.toString(webdav.getFirstMappedPort());
        properties = new HashMap<String, String>();
    }

    @Test(expected = Test.None.class)
    public void test1() throws Exception {
        UploadFileLocalToSftpAbsolutePathsWebdavEquivalent.runExample(host, port, properties);
    }
}
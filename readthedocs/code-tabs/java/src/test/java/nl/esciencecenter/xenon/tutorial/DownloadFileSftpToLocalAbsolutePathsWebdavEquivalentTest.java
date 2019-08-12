package nl.esciencecenter.xenon.tutorial;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.testcontainers.containers.GenericContainer;


public class DownloadFileSftpToLocalAbsolutePathsWebdavEquivalentTest {

    private String host;
    private String port;
    private Map<String, String> properties;

    @Rule
    public GenericContainer<?> webdav = new GenericContainer<>("xenonmiddleware/webdav").withExposedPorts(80);

    @Before
    public void setUp() throws UnsupportedOperationException, IOException, InterruptedException {
        host = webdav.getContainerIpAddress();
        port = Integer.toString(webdav.getFirstMappedPort());
        properties = new HashMap<String, String>();

        // test tries to download this file, so we need to make it first
        webdav.execInContainer("touch", "/home/xenon/sleep.stdout.txt");
    }

    @Test(expected = Test.None.class)
    public void test1() throws Exception {
        DownloadFileSftpToLocalAbsolutePathsWebdavEquivalent.runExample(host, port, properties);
    }
}
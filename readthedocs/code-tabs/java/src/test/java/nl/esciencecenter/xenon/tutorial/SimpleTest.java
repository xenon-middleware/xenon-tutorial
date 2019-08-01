package nl.esciencecenter.xenon.tutorial;

import org.junit.Test;


public class SimpleTest {

    @Test(expected = Test.None.class)
    public void test1() throws Exception {
        AllTogetherNow.main(null);
    }

}

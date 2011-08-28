package com.giantcow.darkmatter.player;

import java.util.Collection;
import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * @author Jeremiah M. Via <jxv911@cs.bham.ac.uk>
 * @version 2011.0320
 */
public class HumanMatterTest extends TestCase {
    
    public HumanMatterTest(String testName) {
        super(testName);
    }

    public static Test suite() {
        TestSuite suite = new TestSuite(HumanMatterTest.class);
        return suite;
    }

    @Override
    protected void setUp() throws Exception {
        super.setUp();
    }

    @Override
    protected void tearDown() throws Exception {
        super.tearDown();
    }

    /**
     * Test of changeMove method, of class HumanMatter.
     */
    public void testChangeMove() {
        System.out.println("changeMove");
        double x = 0.0;
        double y = 0.0;
        Collection<Matter> matterList = null;
        HumanMatter instance = null;
//        Matter expResult = null;
//        Matter result = instance.changeMove(x, y, matterList);
        // TODO review the generated test code and remove the default call to fail.
        assertTrue(true);
    }

}

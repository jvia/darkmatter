/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.giantcow.darkmatter.level;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * Tests all the classes in the level package.
 *
 * @author Jeremiah M. Via <jxv911@cs.bham.ac.uk>
 * @version 2011.0320
 */
public class LevelSuite extends TestCase {

    public LevelSuite(String testName) {
        super(testName);
    }

    public static Test suite() {
        TestSuite suite = new TestSuite("LevelSuite");
        suite.addTest(MusicPlayerTest.suite());
        suite.addTest(LevelLoaderTest.suite());
        suite.addTest(TrackPlayerTest.suite());
        suite.addTest(SpriteFactoryTest.suite());
        suite.addTest(SpriteTest.suite());
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

}

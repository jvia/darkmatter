package com.giantcow.darkmatter.level;

import com.giantcow.darkmatter.player.AIMatter;
import com.giantcow.darkmatter.player.AIMatter2;
import com.giantcow.darkmatter.player.HumanMatter;
import com.giantcow.darkmatter.player.Matter;
import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

import java.util.ArrayList;
import java.util.Collection;

/**
 * Test cases for the LevelLoader class.
 *
 * @author Jeremiah M. Via <jxv911@cs.bham.ac.uk>
 * @version 2011.0320
 */
public class LevelLoaderTest extends TestCase {

    public LevelLoaderTest(String testName) {
        super(testName);
    }

    public static Test suite() {
        return new TestSuite(LevelLoaderTest.class);
    }

    /**
     * We want to reset the LevelLoader for each test.
     *
     * @throws Exception
     */
    @Override
    protected void setUp() throws Exception {
        LevelLoader.readFile("test.lvl");
        super.setUp();
    }

    @Override
    protected void tearDown() throws Exception {
        super.tearDown();
    }

    /** Test of readFile method, of class LevelLoader. */
    public void testReadFile() {
        System.out.println("readFile");
        assertTrue(LevelLoader.loadLevel().size() > 0);
    }

    /**
     * Test of loadPlayer method, of class LevelLoader. We want to make sure that the LevelLoader will replace the
     * Matter instance with a HumanMatter instance. Normally <code>m</code> would be the parameter and the result but
     * for the test it is better to initialize the result as a Matter object and test to see if it is a HumanMatter
     * object.
     */
    public void testLoadPlayer_HumanMatter_int() {
        System.out.println("loadPlayer");
        HumanMatter m = null;
        int player = 0;
        Matter result = LevelLoader.loadPlayer(m, player);
        assertTrue(result instanceof HumanMatter);
    }

    /** Test of loadPlayer method, of class LevelLoader. */
    public void testLoadPlayer_AIMatter_int() {
        System.out.println("loadPlayer");
        AIMatter m = null;
        int player = 0;
        Matter result = LevelLoader.loadPlayer(m, player);
        assertTrue(result instanceof AIMatter);
    }

    /** Test of loadPlayer method, of class LevelLoader. */
    public void testLoadPlayer_AIMatter2_int() {
        System.out.println("loadPlayer");
        AIMatter2 m = null;
        int player = 0;
        Matter result = LevelLoader.loadPlayer(m, player);
        assertTrue(result instanceof AIMatter2);
    }

    /**
     * Test of loadLevel method, of class LevelLoader. Take note of the call to {@link
     * com.giantcow.darkmatter.player.Matter#resetCount()}. This is important because it reset the implicit ID counter
     * in the Matter objects. The {@code LevelLoader} class does this automatically but in this case we need to to it
     * ourselves.
     */
    public void testLoadLevel() {
        System.out.println("loadLevel");

        Matter.resetCount();
        Collection<Matter> expResult = new ArrayList<Matter>();
        expResult.add(new Matter(45.0, 45.0, 40.0, 0.1, 0.2));
        expResult.add(new Matter(100.0, 100.0, 50.0, 0.0, 0.0));

        LevelLoader.readFile("test.lvl");
        Collection result = LevelLoader.loadLevel();
        assertEquals(expResult, result);
    }

    /**
     * Test of loadLevel method, of class LevelLoader. In this instance we leave out call to {@code Matter.resetCount()}
     * which causes the objects to be considered different.
     */
    public void testLoadLevel_fail() {
        System.out.println("loadLevel");

        Collection<Matter> expResult = new ArrayList<Matter>();
        expResult.add(new Matter(45.0, 45.0, 40.0, 0.1, 0.2));
        expResult.add(new Matter(100.0, 100.0, 50.0, 0.0, 0.0));

        LevelLoader.readFile("test.lvl");
        Collection result = LevelLoader.loadLevel();
        assertNotSame(expResult, result);
    }

}

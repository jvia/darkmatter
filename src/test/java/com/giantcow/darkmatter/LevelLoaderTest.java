package com.giantcow.darkmatter;

import com.giantcow.darkmatter.player.AIMatter;
import com.giantcow.darkmatter.player.HumanMatter;
import com.giantcow.darkmatter.player.Matter;
import java.util.ArrayList;
import junit.framework.TestCase;

/**
 *
 * @author Jeremiah M. Via <jxv911@cs.bham.ac.uk>
 */
public class LevelLoaderTest extends TestCase {

    /**
     * Test of getLevel method, of class LevelLoader.
     */
    public void testGetLevel() {
        System.out.println("getLevel");

        LevelLoader.readFile("test.lvl");
        ArrayList<Matter> result = new ArrayList<Matter>(LevelLoader.loadLevel());
        ArrayList<Matter> expected = new ArrayList<Matter>();
        expected.add(new Matter(45.0, 45.0, 40.0, 0.1, 0.2));
        expected.add(new Matter(100.0, 100.0, 50.0, 0.0, 0.0));
        expected.add(new Matter(150.0, 10.0, 30.0, 0.0, 0.0));
        expected.add(new Matter(20.0, 100.0, 20.0, 0.0, 0.0));
        expected.add(new Matter(75.0, 15.0, 20.0, 0.0, 0.0));

        assertTrue(result.containsAll(expected));
        assertTrue(expected.containsAll(result));
    }

    public void testLoadPlayer_Matter() {
        System.out.println("loadPlayer -- Matter");
        LevelLoader.readFile("test.lvl");
        Matter m =  null;
        m = LevelLoader.loadPlayer(m, 0);
        System.out.println(m);
        assertNotNull(m);
    }

    public void testLoadPlayer_HumanMatter() {
        System.out.println("loadPlayer -- Human Matter");
        LevelLoader.readFile("test.lvl");
        HumanMatter m =  null;
        m = LevelLoader.loadPlayer(m, 0);
        System.out.println(m);
        assertNotNull(m);
    }

    public void testLoadPlayer_AIMatter() {
        System.out.println("loadPlayer -- AI Matter");
        LevelLoader.readFile("test.lvl");
        AIMatter m =  null;
        m = LevelLoader.loadPlayer(m, 0);
        System.out.println(m);
        assertNotNull(m);
    }

    public void testWholeLoader() {
        System.out.println("test whole loading process");
        LevelLoader.readFile("test.lvl");
        HumanMatter m = null;
        HumanMatter n = null;
        m = LevelLoader.loadPlayer(m, 0);
        m.update();
        m.update();
        m.update();
        m.update();
        m.update();
        n = LevelLoader.loadPlayer(n, 0);
        assertEquals(m, n);
    }
}

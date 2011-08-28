package com.giantcow.darkmatter.net;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

import java.awt.*;

/**
 * @author Jeremiah M. Via <jxv911@cs.bham.ac.uk>
 * @version 2011.0320
 */
public class GameMsgTest extends TestCase {

    public GameMsgTest(String testName) {
        super(testName);
    }

    public static Test suite() {
        TestSuite suite = new TestSuite(GameMsgTest.class);
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

    /** Test of getMessage method, of class GameMsg. */
    public void testGetMessage() {
        System.out.println("getMessage");
        Point click = new Point(0, 0);
        GameMsg instance = new GameMsg(click, Message.click);
        Point result = (Point) instance.getMessage();
        assertEquals(click, result);
    }


    /** Test of getMessageType method, of class GameMsg. */
    public void testGetMessageType() {
        System.out.println("getMessageType");
       Point click = new Point(0, 0);
        GameMsg instance = new GameMsg(click, Message.click);
        Message result = instance.getMessageType();
        assertEquals(Message.click, result);
    }


}

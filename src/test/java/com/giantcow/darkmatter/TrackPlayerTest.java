/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.giantcow.darkmatter;

import javax.sound.sampled.SourceDataLine;
import junit.framework.TestCase;

/**
 *
 * @author Joss
 */
public class TrackPlayerTest extends TestCase {

    public TrackPlayerTest() {
        super("Track Player Test");
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
     * Test of getFileName method, of class TrackPlayer.
     */
    public void testGetFileName() {
        System.out.println("getFileName");
        TrackPlayer instance = new TrackPlayer("TestTrack.wav");
        String expResult = "TestTrack.wav";
        String result = instance.getFileName();
        assertEquals(expResult, result);
    }

//    /**
//     * Test of setFileName method, of class TrackPlayer.
//     */
//    public void testSetFileName() {
//        System.out.println("setFileName");
//        String fileName = "TestTrack.wav";
//        TrackPlayer instance = new TrackPlayer(fileName);
//        instance.setFileName(fileName);
//        assertEquals(fileName, instance.getFileName());
//    }
//
//    /**
//     * Test of getEXTERNAL_BUFFER_SIZE method, of class TrackPlayer.
//     */
//    public void testGetEXTERNAL_BUFFER_SIZE() {
//        System.out.println("getEXTERNAL_BUFFER_SIZE");
//        TrackPlayer instance = new TrackPlayer(null);
//        int expResult = 524288;
//        int result = instance.getEXTERNAL_BUFFER_SIZE();
//        assertEquals(expResult, result);
//    }
}

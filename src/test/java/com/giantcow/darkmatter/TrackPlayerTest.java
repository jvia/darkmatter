package com.giantcow.darkmatter;

import java.io.File;
import java.io.IOException;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.SourceDataLine;
import javax.sound.sampled.UnsupportedAudioFileException;
import junit.framework.TestCase;

/**
 *
 * @author Joss Greenaway <jtg897@cs.bham.ac.uk>
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
     * Sets the String name attached to a TrackPlayer to TestTrack.wav and makes sure it takes
     */
    public void testGetFileName() {
        System.out.println("getFileName");
        TrackPlayer instance = new TrackPlayer("TestTrack.wav");
        String expResult = "TestTrack.wav";
        String result = instance.getFileName();
        assertEquals(expResult, result);
    }

    /**
     * Test of getFileName method, of class TrackPlayer
     * Sets the String name attached to a TrackPlayer to be null and makes sure it takes
     */
    public void testGetFileName2() {
        System.out.println("getFileName");
        TrackPlayer instance = new TrackPlayer(null);
        String expResult = null;
        String result = instance.getFileName();
        assertEquals(expResult, result);
    }

    /**
     * Test of getFileName method, of class TrackPlayer
     * Sets the String name attached to TrackPlayer to be TestTrack2.wav and makes sure it takes
     */
    public void testGetFileName3() {
        System.out.println("getFileName");
        TrackPlayer instance = new TrackPlayer("%%%%.wav");
        String expResult = "%%%%.wav";
        String result = instance.getFileName();
        assertEquals(expResult, result);
    }

    /**
     * Test of setFileName method, of class TrackPlayer.
     * Sets the String name attached to TrackPlayer to be TestTrack.wav and makes sure it's been set correctly
     */
    public void testSetFileName() {
        System.out.println("setFileName");
        String fileName = "TestTrack.wav";
        TrackPlayer instance = new TrackPlayer(null);
        instance.setFileName(fileName);
        assertEquals(fileName, instance.getFileName());
    }

    /**
     * Test of setFileName method, of class TrackPlayer
     * Sets the String name attached to TrackPlayer to be null and makes sure it's been set correctly
     */
    public void testSetFileName2() {
        System.out.println("setFileName");
        String fileName = null;
        TrackPlayer instance = new TrackPlayer("Hmmm");
        instance.setFileName(fileName);
        assertEquals(fileName, instance.getFileName());


    }

     /**
     * Test of setFileName method, of class TrackPlayer
     * Sets the String name attached to TrackPlayer to be %%%%.wav and makes sure it's been set correctly
     */
    public void testSetFileName3() {
        System.out.println("setFileName");
        String fileName = "%%%%.wav";
        TrackPlayer instance = new TrackPlayer(null);
        instance.setFileName(fileName);
        assertEquals(fileName, instance.getFileName());
    }

    /**
     * Test of getEXTERNAL_BUFFER_SIZE method, of class TrackPlayer.
     * Gets the External Buffer Size and ensures it matches it's the final value it should have of 524288
     */
    public void testGetEXTERNAL_BUFFER_SIZE() {
        System.out.println("getEXTERNAL_BUFFER_SIZE");
        TrackPlayer instance = new TrackPlayer(null);
        int expResult = 524288;
        int result = instance.getEXTERNAL_BUFFER_SIZE();
        assertEquals(expResult, result);
    }

    /**
     * Test of setLine method, of class TrackPlayer
     * Generates a new line and sets it to the TrackPlayer, checking the line generated is the same as the one set to the TrackPlayer
     */
    public void testSetLine() {
        System.out.println("setLine");
        SourceDataLine testLine;
        TrackPlayer instance;
        try {
            instance = new TrackPlayer("SpaceFighterLoop.wav");
            AudioInputStream testAudio = AudioSystem.getAudioInputStream(new File(instance.generateAbsolutePath()));
            DataLine.Info info = new DataLine.Info(SourceDataLine.class, testAudio.getFormat());
            testLine = (SourceDataLine) AudioSystem.getLine(info);
            instance.setLine(testLine);

        } catch (UnsupportedAudioFileException e) {
            e.printStackTrace();
            return;
        } catch (IOException e) {
            e.printStackTrace();
            return;
        } catch (LineUnavailableException e) {
            e.printStackTrace();
            return;
        }
        assertEquals(testLine, instance.getLine());
    }

    /**
     * Test of getLine method, of class TrackPlayer
     * Generates a new line and sets it to the TrackPlayer, checking the line retrieved from the TrackPlayer is the same as the one generated
     */
    public void testGetLine() {
        System.out.println("setLine");
        SourceDataLine testLine;
        TrackPlayer instance;
        try {
            instance = new TrackPlayer("SpaceFighterLoop.wav");
            AudioInputStream testAudio = AudioSystem.getAudioInputStream(new File(instance.generateAbsolutePath()));
            DataLine.Info info = new DataLine.Info(SourceDataLine.class, testAudio.getFormat());
            testLine = (SourceDataLine) AudioSystem.getLine(info);
            instance.setLine(testLine);

        } catch (UnsupportedAudioFileException e) {
            e.printStackTrace();
            return;
        } catch (IOException e) {
            e.printStackTrace();
            return;
        } catch (LineUnavailableException e) {
            e.printStackTrace();
            return;
        }
        assertEquals(instance.getLine(), testLine);
    }

    /**
     * Test of the run method, from class TrackPlayer
     * Generates a new TrackPlayer, with an existing wav file used, and calls run to ensure it plays, asserting that the line
     * has been correctly closed after use
     */
    public void testRun() {
        System.out.println("run");
        TrackPlayer instance = new TrackPlayer("SillyFunThemeA.wav");
        instance.run();
        assertFalse(instance.getLine().isActive());
    }

}

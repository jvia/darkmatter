package com.giantcow.darkmatter.level;

import javax.sound.sampled.*;
import java.io.BufferedInputStream;
import java.io.File;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Plays a track. This class is given the name of a track which is then loaded and played. There is
 * also rudimentary support for fading into the song at the beginning so transitions are less
 * jarring.
 *
 * @author Joss Greenaway <jtg897@cs.bham.ac.uk>
 * @author Jeremiah M. Via <jxv911@cs.bham.ac.uK>
 * @version 2011.0320
 * @since 0.2
 */
public class TrackPlayer implements Runnable {

    private String fileName;
    private Position chPosition;
    private static final int EXTERNAL_BUFFER_SIZE = 524288;
    private SourceDataLine auLine;

    /**
     * Returns the fileName of the current file assigned to the TrackPlayer.
     *
     * @return the fileName of the track
     */
    public String getFileName() {
        return fileName;
    }

    /**
     * Sets the fileName associated with the TrackPlayer to a specified String.
     *
     * @param fileName the String to which the fileName will be set
     */
    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    /**
     * Returns the External Buffer Size.
     *
     * @return the EXTERNAL_BUFFER_SIZE
     */
    public int getEXTERNAL_BUFFER_SIZE() {
        return EXTERNAL_BUFFER_SIZE;
    }

    /** Which audio channels to play through. */
    enum Position {

        LEFT, RIGHT, NORMAL
    }


    /**
     * Constructor for a MusicPlayer object. Automatically sets the Position to {@code NORMAL}.
     *
     * @param wavFile String representing the name of the File to be played
     */
    public TrackPlayer(String wavFile) {
        fileName = wavFile;
        chPosition = Position.NORMAL;
    }

    /**
     * Constructor for a MusicPlayer object with the parameters of the File Name and the Position.
     *
     * @param wavFile String representing the name of the File to be played
     * @param p       Position of the channel the audio will be played through
     */
    public TrackPlayer(String wavFile, Position p) {
        fileName = wavFile;
        chPosition = p;
    }

    /**
     * Retrieves the fileName of the track to be played from the currently instantiated Music
     * Player, and derives an Absolute Path from it by getting the Path from it's form as a URL,
     * removing the initial / and removing any URL space character '%20'.
     *
     * @return a String representing the Absolute Path of the fileName
     */
    public String generateAbsolutePath() {
        File file = new File(getClass().getClassLoader().getResource(fileName).getFile());
        System.out.println(file.getAbsolutePath());
        return file.getAbsolutePath();
    }

    /**
     * Sets the SourceDataLine of the TrackPlayer.
     *
     * @param al the SourceDataLine to be set
     */
    public void setLine(SourceDataLine al) {
        auLine = al;
    }

    /**
     * Returns the SourceDataLine for the TrackPlayer.
     *
     * @return
     */
    public SourceDataLine getLine() {
        return auLine;
    }

    /**
     * Takes the fileName and runs the specified wave file, setting a line for Audio to be played
     * through and subsequently streams the audio file to the user.
     */
    @Override
    public void run() {
        AudioInputStream audioIn;
        try {
            //Creates an AudioInputStream from the Sound File provided
            audioIn =
                    AudioSystem.getAudioInputStream(new BufferedInputStream(getClass().getClassLoader().getResourceAsStream(fileName)));


            //Retrieves a DataLine which can be used to play the audio
            AudioFormat format = audioIn.getFormat();
            setLine(null);
            DataLine.Info info = new DataLine.Info(SourceDataLine.class, format);


            setLine((SourceDataLine) AudioSystem.getLine(info));
            auLine.open(format);

            //Sets the value of the FloatControl depending on the channel selected for the Music Player
            if (auLine.isControlSupported(FloatControl.Type.PAN)) {
                FloatControl pan = (FloatControl) auLine.getControl(FloatControl.Type.PAN);
                if (chPosition == Position.RIGHT) {
                    pan.setValue(1.0f);
                } else if (chPosition == Position.LEFT) {
                    pan.setValue(-1.0f);
                }
            }

            auLine.start();
            if (!auLine.isOpen())
                auLine.open();


//        volCont vol = new volCont();
//        vol.start();

            int nBytesRead = 0;
            byte[] abData = new byte[getEXTERNAL_BUFFER_SIZE()];

            while (nBytesRead != -1) {
                nBytesRead = audioIn.read(abData, 0, abData.length);
                if (nBytesRead >= 0)
                    auLine.write(abData, 0, nBytesRead);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            auLine.drain();
            auLine.close();
        }
    }

    /**
     * Inner class volCont to generate a separate Thread so that volume may be incremented for a
     * fade in on tracks whilst the music is playing.
     */
    private class volCont extends Thread {

        @Override
        public void run() {
            // Uncomment to see what the system supports
            //for (Control c : auLine.getControls())
            //    System.out.println(c.getType());
            FloatControl volume = (FloatControl) auLine.getControl(FloatControl.Type.VOLUME);

            // Get the min and max volumes and a step size
            float max = volume.getMaximum();
            float min = 0.50f * max;
            float step = 1000.0f * volume.getPrecision();

            float newVolume = min;
            volume.setValue(newVolume);

            while (newVolume < max - step) {
                newVolume += step;
                volume.setValue(newVolume);
                try {
                    Thread.sleep(500);
                } catch (InterruptedException ex) {
                    Logger.getLogger(TrackPlayer.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
    }
}



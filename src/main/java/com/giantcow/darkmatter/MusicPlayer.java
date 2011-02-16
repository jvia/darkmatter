package com.giantcow.darkmatter;

import java.io.File;
import java.io.IOException;
import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.FloatControl;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.SourceDataLine;
import javax.sound.sampled.UnsupportedAudioFileException;

/**
 *
 * @author Joss Greenaway <jtg897@cs.bham.ac.uk>
 * @version 2011.0902
 * @since 0.2
 */
public class MusicPlayer extends Thread {

    private String fileName;
    private Position chPosition;
    private final int EXTERNAL_BUFFER_SIZE = 524288;
    private SourceDataLine auline;

    enum Position {

        LEFT, RIGHT, NORMAL
    };

    /**
     * Constructor for a MusicPlayer object where the only parameter is the File Name,
     * automatically sets the Position to normal
     * @param wavFile String representing the name of the File to be played
     */
    public MusicPlayer(String wavFile) {
        fileName = wavFile;
        chPosition = Position.NORMAL;
    }

    /**
     * Constructor for a MusicPlayer object with the parameters of the File Name
     * and the Position
     * @param wavFile String representing the name of the File to be played
     * @param p Position of the channel the audio will be played through
     */
    public MusicPlayer(String wavFile, Position p) {
        fileName = wavFile;
        chPosition = p;
    }

    private String generateAbsolutePath(){
        //Retrieves a URL of the File to be played, and assigns it's URL path to a String

        String filePath = getClass().getClassLoader().getResource(fileName).getPath();

        /* Removes the escape character at the start of the URL path, converting
         * the rest to a character array and iterating through to remove any
         * occurances of '%20', the URL character for spaces
         */
        StringBuilder buf = new StringBuilder(filePath.length() - 1);
        buf.append(filePath.substring(0, 0)).append(filePath.substring(1));
        filePath = buf.toString();
        filePath = filePath.replace('%', ' ');
        char[] ca = filePath.toCharArray();
        char[] ca2 = new char[filePath.length()];

        int counter = 0;
        for (int i = 0; i < ca.length; i++) {

            if (ca[i] == '2' && ca[i + 1] == '0') {
                i = i + 1;
                counter++;
            } else {
                ca2[i - (counter*2)] = ca[i];

            }

        }

        return new String(ca2);

    }

    /**
     * Tells the Line assigned for playing audio to stop playing, drain the contents
     * of itself and then close.
     */
    public void endTrack(){
        auline.stop();
        auline.drain();
        auline.close();
    }

    @Override
    public void run() {  

        // Creates a new File from the Absolute File Path that's been generated and checks it exists
            File soundFile = new File(generateAbsolutePath());
            if (!soundFile.exists()) {
                System.err.println("Wave file not found : " + fileName);
                return;
            }
        

        //Creates an AudioInputStream from the Sound File provided
        AudioInputStream audioIn = null;
        try {
            audioIn = AudioSystem.getAudioInputStream(soundFile);
        } catch (UnsupportedAudioFileException e) {
            e.printStackTrace();
            return;
        } catch (IOException e) {
            e.printStackTrace();
            return;
        }

        //Retrieves a DataLine which can be used to play the audio
        AudioFormat format = audioIn.getFormat();
        auline = null;
        DataLine.Info info = new DataLine.Info(SourceDataLine.class, format);

        try {
            auline = (SourceDataLine) AudioSystem.getLine(info);
            auline.open(format);
        } catch (LineUnavailableException e) {
            e.printStackTrace();
            return;
        } catch (Exception e) {
            e.printStackTrace();
            return;
        }

        //Sets the value of the FloatControl depending on the channel selected for the Music Player
        if (auline.isControlSupported(FloatControl.Type.PAN)) {
            FloatControl pan = (FloatControl) auline.getControl(FloatControl.Type.PAN);
            if (chPosition == Position.RIGHT) {
                pan.setValue(1.0f);
            } else if (chPosition == Position.LEFT) {
                pan.setValue(-1.0f);
            }
        }

        auline.start();
        int nBytesRead = 0;
        byte[] abData = new byte[EXTERNAL_BUFFER_SIZE];

        try {
            while (nBytesRead != -1) {
                nBytesRead = audioIn.read(abData, 0, abData.length);
                if (nBytesRead >= 0) {
                    auline.write(abData, 0, nBytesRead);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
            return;

        } finally {
            auline.drain();
            auline.close();
        }



    }


}

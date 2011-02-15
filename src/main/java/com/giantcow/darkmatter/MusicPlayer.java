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
    private Position curPosition;
    private final int EXTERNAL_BUFFER_SIZE = 524288;

    enum Position {
        LEFT, RIGHT, NORMAL
    };

    public MusicPlayer(String wavFile) {
        fileName = wavFile;
        curPosition = Position.NORMAL;
    }

    public MusicPlayer(String wavFile, Position p) {
        fileName = wavFile;
        curPosition = p;
    }

    @Override
    public void run() {

        //File soundFile = new File((getClass().getClassLoader().getResource(fileName)).getFile());
        File soundFile = new File("C:\\Users\\Joss\\Documents\\Work Directory\\Netbeans Projects\\DarkMatter\\trunk\\target\\classes\\SpaceFighterLoop.wav");
        if (!soundFile.exists()) {
            System.err.println("Wave file not found : " + fileName);
            System.err.println("Target file : " + getClass().getClassLoader().getResource(fileName).getFile());
            return;
        }

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

        AudioFormat format = audioIn.getFormat();
        SourceDataLine auline = null;
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

        if (auline.isControlSupported(FloatControl.Type.PAN)) {
            FloatControl pan = (FloatControl) auline.getControl(FloatControl.Type.PAN);
            if (curPosition == Position.RIGHT) {
                pan.setValue(1.0f);
            } else if (curPosition == Position.LEFT) {
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

    public static void main(String[] args) {
        new MusicPlayer("SpaceFighterLoop.wav").start();
    }
}

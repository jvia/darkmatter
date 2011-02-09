package sanbox;

import java.io.*;
import java.net.URL;
import javax.sound.sampled.*;
import javax.swing.*;
import java.awt.event.*;
import java.util.ArrayList;

/**
 *
 * @author Joss Greenaway <jtg897@cs.bham.ac.uk>
 * @version 2011.0902
 * @since 0.2
 */
public class MusicPlayer extends JFrame {

    //private ArrayList<String> musicList;
    private Clip nowPlaying;

    public MusicPlayer() {
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setTitle("Test Sound Clip");
        this.setSize(300, 200);
        this.setVisible(true);


        //musicList.add("Space Fighter Loop.wav");


        try {
            URL url = this.getClass().getClassLoader().getResource("Space Fighter Loop.wav");
            AudioInputStream audioIn = AudioSystem.getAudioInputStream(url);

            nowPlaying = AudioSystem.getClip();

            nowPlaying.open(audioIn);
            nowPlaying.start();

        } catch (UnsupportedAudioFileException e) {
            e.getMessage();
        } catch (IOException e) {
            e.getMessage();
        } catch (LineUnavailableException e) {
            e.getMessage();
        }




    }

    public static void main(String[] args) {
        MusicPlayer mp = new MusicPlayer();
        mp.repaint();

    }
}

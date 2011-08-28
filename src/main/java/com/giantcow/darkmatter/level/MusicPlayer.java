package com.giantcow.darkmatter.level;

import java.util.ArrayList;

/**
 * Music player for the game. It plays a list of tracks in background threads to add to the ambiance
 * of the game.
 *
 * @author Joss Greenaway <jtg897@cs.bham.ac.uk>
 * @author Jeremiah M. Via <jxv911@cs.bham.ac.uk>
 * @version 2011.0320
 * @since 0.3
 */
public class MusicPlayer extends Thread {

    private final TrackPlayer trackPlayer;
    private final ArrayList<String> trackList;

    /**
     * Constructor for MusicPlayer. Creates TrackPlayer to play {@code wav} files. Tracks are added
     * to an {@code ArrayList} which acts as the playlist for the game.
     */
    public MusicPlayer() {
        trackPlayer = new TrackPlayer(null);
        trackList = new ArrayList<String>();
        trackList.add("SashaXpander.wav");
        trackList.add("SpaceFighterLoop.wav");
    }

    /**
     * Plays each song in the playlist. The playlist is played in a random order so the tracks are
     * shuffled each time. This helps to prevent listener fatigue.
     */
    @Override
    public void run() {
        while (!trackList.isEmpty()) {
            System.out.println(trackList);
            String track = trackList.get((int) (Math.random() * trackList.size()));

            // set the file to play and play it
            trackPlayer.setFileName(track);
            Thread player = new Thread(trackPlayer);
            player.start();

            // wait until song finishes playing
            try {
                player.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            // remove the song from the track list
            trackList.remove(track);
        }
    }
}

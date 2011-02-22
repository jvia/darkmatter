package com.giantcow.darkmatter;

import java.util.ArrayList;

/**
 *
 * @author Joss Greenaway <jtg897@cs.bham.ac.uk>
 * @version 2011.0902
 * @since 0.3
 */
public class MusicPlayer extends Thread {

    private final TrackPlayer trackPlayer;
    private final ArrayList<String> trackList;

    /**
     * Constructor for MusicPlayer, creates TrackPlayer to play wave files, the names
     * of which are provided by an ArrayList
     * @param trackList ArrayList<String> listing the wave files to be played
     */
    public MusicPlayer(ArrayList<String> trackList) {
        trackPlayer = new TrackPlayer(null);
        this.trackList = trackList;

    }

    /**
     * Returns the TrackPlayer object being used by the MusicPlayer to play wave files
     * @return
     */
    public TrackPlayer getTrackPlayer() {
        return trackPlayer;
    }

    /**
     * Returns the ArrayList containing the Strings representing the wave file's names
     * @return ArrayList<String> containing track names
     */
    public ArrayList getTrackList() {
        return trackList;
    }

    /**
     * Takes the ArrayList of tracks belonging to the MusicPlayer, iterating through
     * it and playing each track one at a time
     */
    @Override
    public void run() {
        Object[] tracks = trackList.toArray();

        for (int i = 0; i < tracks.length; i++) {
            getTrackPlayer().setFileName((String) tracks[i]);
            getTrackPlayer().run();
            while(getTrackPlayer().getLine().isActive()){

            }
        }
    }

   
    final public void terminate() {
        getTrackPlayer().getLine().stop();
        getTrackPlayer().getLine().drain();
        getTrackPlayer().getLine().close();
    }
    
/**
    public static void main(String[] args) {
    ArrayList<String> tracks = new ArrayList<String>();
    tracks.add("SillyFunThemeA.wav");
    tracks.add("SpaceFighterLoop.wav");

    MusicPlayer mp = new MusicPlayer(tracks);
    mp.play();


    }
    */
}

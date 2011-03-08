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
     */
    public MusicPlayer() {
        trackPlayer = new TrackPlayer(null);
        trackList = new ArrayList<String>();
        trackList.add("SashaXpander.wav");
        trackList.add("SpaceFighterLoop.wav");

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
        ArrayList playedTracks = new ArrayList<String>();
        Boolean newTrack;
        int playTrack = 0;

        for (int i = 0; i < tracks.length; i++) {
            newTrack = false;
            while (newTrack != true){
            playTrack = (int)(Math.round(Math.random() * tracks.length));
            if (playedTracks.contains(tracks[playTrack])){

            }
            else {
                newTrack = true;
                playedTracks.add(tracks[playTrack]);
            }
            }
            getTrackPlayer().setFileName((String) tracks[playTrack]);
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

package com.giantcow.darkmatter;

import java.awt.*;

/**
 * Holds some constants for the game.
 *
 * @author Jeremiah M Via. <jxv911@cs.bham.ac.uk>
 * @version 2011.0312
 */
public class Utils {
    // Game Dimensions
    public static Dimension gameDimension = new Dimension(1024, 768);

    // Colors for the game
    public static Color player = new Color(252, 58, 81);
    public static Color bigger = new Color(243, 134, 48);
    public static Color muchBigger = new Color(250, 105, 0);
    public static Color smaller = new Color(167, 219, 216);
    public static Color muchSmaller = new Color(105, 210, 231);
    public static Color border = Color.orange;//new Color(203, 232, 107);

    // Game fonts
    public static Font largeFont = new Font("Lucida Typewriter Regular", Font.PLAIN, 60);
    public static Font smallFont = new Font("Lucida Typewriter Regular", Font.PLAIN, 30);

    // Game instructions
    public static String instructions = "\n\n\n"
                                        + "The aim of the game is to absorb matter that is smaller than you "
                                        + "while avoiding matter that is larger than you.\n\n"
                                        + ""
                                        + "Click to expel matter from yourself in order to gain "
                                        + "momentum. But be careful, as you expel matter you shrink.\n\n"
                                        + ""
                                        + "You will have to balance quick movement and your size at all "
                                        + "times if you hope to have any chance of becoming the master of "
                                        + "the universe."
                                        + "\n\n\n";

    //  About text
    public static String about = "\nFounded in 2011, Giant Cow Games was created as the answer "
                                 + " to the question 'Moo?'. After a lot of rumination we are proud "
                                 + "to bring you Darkmatter. Finally, a game the average cow can relate to."
                                 + "\n";

    public static String randomLevel() {
        int min = 2;
        int max = 8;
        return String.format("%02d.lvl", (int) (min + Math.random() * (max - min)));
    }
}

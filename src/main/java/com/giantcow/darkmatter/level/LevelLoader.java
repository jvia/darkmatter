package com.giantcow.darkmatter.level;

import com.giantcow.darkmatter.Utils;
import com.giantcow.darkmatter.player.AIMatter;
import com.giantcow.darkmatter.player.AIMatter2;
import com.giantcow.darkmatter.player.HumanMatter;
import com.giantcow.darkmatter.player.Matter;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Scanner;

/**
 * Loads files containing a level layout. A level can be laid out in a text file like this:
 * <p/>
 * {@literal
 * # x   y   r    dy  dx
 * 0.0, 0.0, 40.0, 0.1, 0.2
 * 3.0, 3.0, 40.0, 0.1, 0.2
 * 5.0, 5.0, 40.0, 0.1, 0.2
 * 7.0, 7.0, 40.0, 0.1, 0.2 }
 * <p/>
 * This class will read them in and return them as a set that the game can use. To make organizing
 * them simple, name your levels like <code>01.lvl</code>
 *
 * @author Jeremiah M. Via <jxv911@cs.bham.ac.uK>
 * @version 2011.0311
 */
public class LevelLoader {

    /** The folder where <code>lvl</code> files are kept. */
    private static final String FOLDER = "levels/";
    /** Matter objects are loaded into the ArrayList */
    private static ArrayList<Matter> matter;
    /** Reference to load path */
    private static LevelLoader loader = new LevelLoader();

    /**
     * Reads a <{@code lvl} file and adds the Matter objects to a file.
     *
     * @param fileName level file
     */
    public static void readFile(final String fileName) {
        System.out.print(fileName + " \\\\ loaded id:");
        matter = new ArrayList<Matter>();
        Matter.resetCount();
        Scanner input = new Scanner(loader.getClass().getClassLoader().getResourceAsStream(
                FOLDER + fileName));
        while (input.hasNextLine()) {
            String l = input.nextLine().replaceAll(" ", "");
            String[] line = l.split(",");

            // ignore commented lines or invalid lines
            if (line[0].startsWith("#") || line.length < 5) continue;


            // parse into matter objects
            Matter m = new Matter(
                    Double.parseDouble(line[0]),
                    Double.parseDouble(line[1]),
                    Double.parseDouble(line[2]),
                    Double.parseDouble(line[3]),
                    Double.parseDouble(line[4]));
            if (m.getMaxX() < Utils.gameDimension.width
                    && m.getMaxY() < Utils.gameDimension.height)
                matter.add(m);

            System.out.print(" " + m.getId());
        }
        System.out.println();
    }

    /**
     * Loads a HumanMatter as the specified player in the level.
     *
     * @param m      human player
     * @param player player number
     * @return human player
     */
    public static HumanMatter loadPlayer(HumanMatter m, int player) {
        Matter ref = matter.get(player);

        m =
                new HumanMatter(ref.getX(), ref.getY(), ref.getRadius(), ref.getDy(), ref.getDx(), ref.getId());
        matter.remove(player);
        matter.add(player, m);
        return m;
    }

    /**
     * Loads a AIMatter as the specified player in the level.
     *
     * @param m      human player
     * @param player player number
     * @return human player
     */
    public static AIMatter loadPlayer(AIMatter m, int player) {
        Matter ref = matter.get(player);

        m = new AIMatter(ref.getX(), ref.getY(), ref.getRadius(), ref.getDy(), ref.getDx());
        matter.remove(player);
        matter.add(player, m);
        return m;
    }

    /**
     * Loads an AIMatter2 object into the level.
     *
     * @param m      ai player
     * @param player player number
     * @return the ai player
     */
    public static AIMatter2 loadPlayer(AIMatter2 m, int player) {
        Matter ref = matter.get(player);

        m = new AIMatter2(ref.getX(), ref.getY(), ref.getRadius(), ref.getDy(), ref.getDx());
        matter.remove(player);
        matter.add(player, m);
        return m;
    }

    /**
     * Gets the level out of the level loader so it can be used in the game.
     *
     * @return the loaded level
     */
    public static Collection<Matter> loadLevel() {
        return matter;
    }
}

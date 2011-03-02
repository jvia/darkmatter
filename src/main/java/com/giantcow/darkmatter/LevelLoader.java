package com.giantcow.darkmatter;

import com.giantcow.darkmatter.player.AIMatter;
import com.giantcow.darkmatter.player.HumanMatter;
import com.giantcow.darkmatter.player.Matter;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Loads files containing a level layout. A level can be laid out in a
 * text file like this:
 *
 * <code>
 *    # x   y   r    dy  dx
 *      0.0, 0.0, 40.0, 0.1, 0.2
 *      3.0, 3.0, 40.0, 0.1, 0.2
 *      5.0, 5.0, 40.0, 0.1, 0.2
 *      7.0, 7.0, 40.0, 0.1, 0.2
 * </code>
 *
 * This class will read them in and return them as a set that the game
 * can use. To make organizing them simple, name your levels like
 * <code>01.level</code>
 *
 * @author Jeremiah M. Via <jxv911@cs.bham.ac.uK>
 */
public class LevelLoader {

    private static final String FOLDER = "/levels/";
    private static ArrayList<Matter> matter;
    private static LevelLoader loader = new LevelLoader();

    public static void readFile(final String fileName) {
        matter = new ArrayList<Matter>();
        try {
            Scanner input = new Scanner(new File(loader.getClass().getResource(FOLDER + fileName).getPath()));
            while (input.hasNextLine()) {
                String l = input.nextLine().replaceAll(" ", "");
                String[] line = l.split(",");

                // ignore commented lines or invalid lines
                if (line[0].startsWith("#") || line.length < 5) {
                    continue;
                }

                // parse into matter objects
                Matter m = new Matter(
                        Double.parseDouble(line[0]),
                        Double.parseDouble(line[1]),
                        Double.parseDouble(line[2]),
                        Double.parseDouble(line[3]),
                        Double.parseDouble(line[4]));

                matter.add(m);
            }
        }
        catch (FileNotFoundException ex) {
            Logger.getLogger(LevelLoader.class.getName()).log(Level.SEVERE, "Could not locate file", ex);
        }
    }

    public static Matter loadPlayer(Matter m, int player) {
        return matter.get(player);
    }

    public static HumanMatter loadPlayer(HumanMatter m, int player) {
        Matter ref = matter.get(player);

        m = new HumanMatter(ref.getX(), ref.getY(), ref.getRadius(), ref.getDy(), ref.getDx());
        matter.remove(player);
        matter.add(player, m);
        return m;
    }

    public static AIMatter loadPlayer(AIMatter m, int player) {
        Matter ref = matter.get(player);

        m = new AIMatter(ref.getX(), ref.getY(), ref.getRadius(), ref.getDy(), ref.getDx());
        matter.remove(player);
        matter.add(player, m);
        return m;
    }

    public static Collection<Matter> loadLevel() {
        return matter;
    }
}

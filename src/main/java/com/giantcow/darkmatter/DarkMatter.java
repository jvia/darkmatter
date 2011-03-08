package com.giantcow.darkmatter;

import com.giantcow.darkmatter.player.AIMatter;
import com.giantcow.darkmatter.player.HumanMatter;
import com.giantcow.darkmatter.player.Matter;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

/**
 * This is the class that runs the whole show. It handles the game mechanics
 *
 * @author Jeremiah M. Via <jxv911@cs.bham.ac.uk>
 * @author Joss Greenaway  <jtg897@cs.bham.ac.uk>
 * @author Yukun Wang      <yxw999@cs.bham.ac.uk>
 */
public class DarkMatter extends JComponent implements KeyListener, MouseListener, MouseWheelListener {

    public static void main(String[] args) {
        DarkMatter darkMatter = new DarkMatter();
        darkMatter.run();
    }

    // GUI Variables
    private static final long DELAY = 20;
    public static int DEFAULT_WIDTH = 800;
    public static int DEFAULT_HEIGHT = 600;
    private Color background = new Color(14, 36, 48);
    private Color player = new Color(252, 58, 81);
    private Color npc = new Color(245, 179, 73);
    private boolean isFullScreen;
    private double zoom = 1;
    private double zoomlevel = 5;
    // the distance pen need to move
    private double x = 0;
    private double y = 0;
    private BufferedImage backgroundP = null;
    // Game Variables
    private Set<Matter> matterList;
    private HumanMatter localPlayer;
    private AIMatter remotePlayer;
    private double goalArea;
    // Game constants
    private MusicPlayer musicPlayer;
    private ArrayList<String> trackList;
    private static final boolean ZOOM_GAME = true;
    Sprite bgSprite;

    /** Create the game object. */
    public DarkMatter() {
        // Setup our component
        setDoubleBuffered(true);
        setOpaque(true);
        setIgnoreRepaint(true);
        addKeyListener(this);
        addMouseListener(this);
        addMouseWheelListener(this);
        setPreferredSize(new Dimension(DEFAULT_WIDTH, DEFAULT_HEIGHT));

        // Add it to the frame
        JFrame frame = new JFrame("DarkMatter");
        frame.setBackground(background);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.add(this);
        frame.pack();
        frame.setVisible(true);
        init();
    }

    /**
     * Creates all the game elements.
     * <p/>
     * TODO Contact network to get this information TODO Use level loader to build
     */
    private void init() {
        LevelLoader.readFile("02.lvl");
        localPlayer = LevelLoader.loadPlayer(localPlayer, 0);
        remotePlayer = LevelLoader.loadPlayer(remotePlayer, 1);
        matterList = Collections.synchronizedSet(new HashSet<Matter>(LevelLoader.loadLevel()));

//        TODO: MusicPlayer is throwing NullPointerException on Linux
        musicPlayer = new MusicPlayer();
        musicPlayer.start();

        //read the background picture
        bgSprite = SpriteFactory.producer().generateSprite("nebulabg.jpg");

        calculateGoalArea();

        if (ZOOM_GAME) {
            zoom = DEFAULT_HEIGHT / localPlayer.getRadius() / 10;
        }
    }

    private void calculateGoalArea() {
        double area = 0.0;
        for (Matter m : matterList) {
            area += m.getArea();
        }
        goalArea = 0.8 * area;
    }

    /**
     * calculate the zoomlevel and the distancae pen should move the initial zoomlevel is 'zoomlevel' times, means the
     * screen width is 5 times of local player matter's diameter if it is possible
     */
    private void zoom() {
        double r = localPlayer.getRadius();
        double cx = localPlayer.getCenterX();
        double cy = localPlayer.getCenterY();
        double currentZoom = DEFAULT_HEIGHT / localPlayer.getRadius() / zoomlevel / 2;

        if (currentZoom > 1 && currentZoom < 10) {
            zoom = (currentZoom - zoom) / zoomlevel + zoom;
        } else {
            zoom = 1;
        }

        if (zoom > 1) {
            if (cy < zoomlevel * r) {
                y = 0;
            } else if (DEFAULT_HEIGHT - cy >= zoomlevel * r) {
                y = cy - DEFAULT_HEIGHT / 2 / zoom;
            } else {
                y = (zoom - 1) * DEFAULT_HEIGHT / zoom;
            }
        } else {
            y = 0;
        }

        if (zoom > 1) {
            if (cx < zoomlevel * r * DEFAULT_WIDTH / DEFAULT_HEIGHT) {
                x = 0;
            } else if (DEFAULT_WIDTH - cx >= zoomlevel * r * DEFAULT_WIDTH / DEFAULT_HEIGHT) {
                x = cx - DEFAULT_WIDTH / 2 / zoom;
            } else {
                x = (zoom - 1) * DEFAULT_WIDTH / zoom;

            }
        } else {
            x = 0;
        }
        if (1 == zoom) {
            zoomlevel = DEFAULT_HEIGHT / localPlayer.getRadius() / zoom / 2;
        }
    }

    /**
     * Paints the current state of the game to the screen.
     *
     * @param g the graphics context object
     */
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                            RenderingHints.VALUE_ANTIALIAS_ON);
        bgSprite.draw(g2, 0, 0);


        g2.scale(zoom, zoom); //enlarge whole map
        g2.translate(-x, -y); //move the pen to the right place       

        // Paint in a sorted order so smaller objects are painted below bigger ones
        ArrayList<Matter> ms = new ArrayList<Matter>(matterList);
        Collections.sort(ms);

        for (Matter m : ms) {
            if (m == null) {
                continue;
            }
            g2.setColor(determineColor(m));
            g2.fill(m);
        }


        g2.scale(1 / zoom, 1 / zoom);
        g2.translate(x, y);
        // Print winning or losing message
        g2.setColor(Color.white);
        if (localPlayer.getArea() <= 16) {
            g2.drawString("You lose", 10, 10);
        }
        if (localPlayer.getArea() >= goalArea) {
            g2.drawString("You win", 10, 10);
        }

        // Sync and dispose Graphics context
        Toolkit.getDefaultToolkit().sync();
        g.dispose();
    }

    /**
     * This method runs the game. It simple updates every object, repaints the screen and then sleep for the specified
     * amount of time.
     */
    public void run() {
        long beforeTime, timeDiff, sleep;
        beforeTime = System.currentTimeMillis();

        while (true) {

            update();
            if (ZOOM_GAME) {
                zoom();
            }
            repaint();

            timeDiff = System.currentTimeMillis() - beforeTime;
            sleep = DELAY - timeDiff;

            if (sleep < 0) {
                sleep = 2;
            }
            try {
                Thread.sleep(sleep);
            } catch (InterruptedException e) {
                System.out.println("interrupted");
            }

            beforeTime = System.currentTimeMillis();
        }
    }

    /**
     * Updates every matter object in the game. This method will get long so it will be best to try and split it up into
     * many smaller methods.
     */
    private void update() {
        // remove too small matter objects
        deleteSmall();

        // Detect collisions
        synchronized (matterList) {
            for (Matter m : matterList) {
                for (Matter n : matterList) {
                    if (m.equals(n)) {
                        continue;
                    }

                    if (m.intersects(n) && m.isBigger(n)) {
                        if (m == localPlayer)
                            System.out.println("Collision: " + System.currentTimeMillis());

                        double d = n.getRadius() + m.getRadius() - m.distance(n);
                        double area = 0.03 * d * n.getArea();
                        m.setArea(m.getArea() + area);
                        n.setArea(n.getArea() - area);
                    }
                }
            }
        }

        updateDirection();
        updateMovement();
    }

    private void updateDirection() {
        synchronized (matterList) {
            // Change dy/dx if necessary
            for (Matter m : matterList) {
                if (m.getX() <= 0 || m.getMaxX() >= DEFAULT_WIDTH) {
                    m.setDx(-m.getDx());
                }
                if (m.getY() <= 0 || m.getMaxY() >= DEFAULT_HEIGHT) {
                    m.setDy(-m.getDy());
                }
                m.update();
            }
        }
    }

    private void updateMovement() {
        ArrayList<Matter> list = new ArrayList<Matter>();
        synchronized (matterList) {
            for (Matter m : matterList) {
                if (m == localPlayer) {
                    continue;
                }
                Matter move = m.changeMove(0, 0, matterList);
                if (move != null) {
                    list.add(move);
                }
            }
        }
        matterList.addAll(list);
    }

    private Color determineColor(Matter m) {
        Color same = new Color(224, 228, 204);
        Color bigger = new Color(243, 134, 48);
        Color biiiger = new Color(250, 105, 0);
        Color smaller = new Color(167, 219, 216);
        Color smaaaller = new Color(105, 210, 231);

        if (m == localPlayer)
            return player;

        double la = localPlayer.getArea();
        double ma = m.getArea();

        if (ma == la) {
            return same;
        }
        if (ma > 2 * la) {
            return bigger;
        }
        if (ma > la) {
            return bigger;
        }
        if (ma * 2 < la) {
            return smaaaller;
        }
        if (ma < la) {
            return smaller;
        }
        return biiiger;
    }

    /**
     * Invoked when a key has been pressed. See the class description for {@link java.awt.event.KeyEvent} for a
     * definition of a key pressed event.
     *
     * @param e the key event
     */
    @Override
    public void keyPressed(final KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_F11) {
            isFullScreen = (!isFullScreen);
            System.out.println("Switch modes");
        }
    }

    /** Invoked when a mouse button has been pressed on a component. */
    @Override
    public void mousePressed(MouseEvent e) {
        Matter m = localPlayer.changeMove(e.getX() / zoom + x, e.getY() / zoom + y, matterList);
        if (m != null)
            matterList.add(m);
    }

    @Override
    public void mouseWheelMoved(MouseWheelEvent e) {
        double n = e.getUnitsToScroll();
        double z = zoomlevel;
        System.out.println("z+n/3: " + z + n / 3);
        if (z + n / 5 > 2)
            zoomlevel = zoomlevel + n / 5;

        z = zoomlevel;
        System.out.println(z);
    }

    // <editor-fold desc="Unused event methods">
    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyReleased(KeyEvent e) {
    }

    /** Invoked when the mouse button has been clicked (pressed and released) on a component. */
    @Override
    public void mouseClicked(MouseEvent e) {
    }

    /** Invoked when a mouse button has been released on a component. */
    @Override
    public void mouseReleased(MouseEvent e) {
    }

    /** Invoked when the mouse enters a component. */
    @Override
    public void mouseEntered(MouseEvent e) {
    }

    /** Invoked when the mouse exits a component. */
    @Override
    public void mouseExited(MouseEvent e) {
    }

    private void deleteSmall() {
        ArrayList<Matter> remove = new ArrayList<Matter>();
        for (Matter m : matterList)
            if (m.getArea() <= 0.1)
                remove.add(m);

        synchronized (matterList) {
            matterList.removeAll(remove);
        }
    }
    // </editor-fold>
}

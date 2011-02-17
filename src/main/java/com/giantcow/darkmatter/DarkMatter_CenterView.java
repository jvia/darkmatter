package com.giantcow.darkmatter;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import javax.imageio.ImageIO;

/**
 * This class use DarkMatter
 *
 * @author Jeremiah M. Via <jxv911@cs.bham.ac.uk>
 * @author Yukun Wang <yxw999@cs.bham.ac.uk>
 */
public class DarkMatter_CenterView extends JComponent implements KeyListener, MouseListener {

    public static void main(String[] args) {
        DarkMatter_CenterView darkMatter = new DarkMatter_CenterView();
        darkMatter.run();
    }
    // GUI Variables
    private static final long DELAY = 10;
    private static int DEFAULT_WIDTH = 800;
    private static int DEFAULT_HEIGHT = 600;
    private BufferedImage background = null;
    private Color player = new Color(252, 58, 81);
    private Color npc = new Color(245, 179, 73);
    private boolean isFullScreen;
    private double zoom = 1; //zoomlevel
    // the distance pen need to move
    private double x = 0;
    private double y = 0;
    // Game Variables
    private Set<Matter> matterList;
    private Matter localPlayer;
    private Matter remotePlayer;
    private double goalArea;
    // Game constants
    private static int INITIAL_BLOBS = 20;

    /**
     * Create the game object.
     */
    public DarkMatter_CenterView() {
        // Setup our component
        setDoubleBuffered(true);
        setOpaque(true);
        setIgnoreRepaint(true);
        addKeyListener(this);
        addMouseListener(this);
        setPreferredSize(new Dimension(DEFAULT_WIDTH, DEFAULT_HEIGHT));


        // Add it to the frame
        JFrame frame = new JFrame("DarkMatter");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.add(this);
        frame.pack();
        frame.setVisible(true);
        init();
    }

    /**
     * Creates all the game elements.
     *
     * TODO Contact network to get this information
     * TODO Use level loader to build
     */
    private void init() {
        matterList = new HashSet<Matter>();
        localPlayer = new Matter(20, 20, 40, 0, 0);
        remotePlayer = new Matter(300, 200, 40, 0, 0);
        matterList.add(localPlayer);
        matterList.add(remotePlayer);

        double area = 0.0;
        while (matterList.size() < INITIAL_BLOBS) {
            // Generate random blob
            Matter m = new Matter(Math.random() * (getWidth() - 50),
                    Math.random() * (getHeight() - 50),
                    Math.random() * 25,
                    -0.1 + Math.random() * 0.2,
                    -0.1 + Math.random() * 0.2);

            // Check to see if it is on top of any other blobs
            // and if not add it to the queue
            if (!m.intersects(matterList)) {
                matterList.add(m);
                area += m.getArea();

            }
        }

        //read the background picture
        try {
            background = ImageIO.read(new File("src/main/resources/Background.png"));
        } catch (IOException e) {
            System.err.print("Failed to open image file");
            System.exit(1);
        }
        ;

        goalArea = 0.85 * area;

    }

    /**
     * calculate the zoomlevel and the distancae pen should move
     * the initial zoomlevel is 5 times, means the screen width is five times of
     * local player matter's diameter if it is possible
     */
    private void zoom() {
        if (DEFAULT_HEIGHT > 10 * localPlayer.getRadius()) {
            zoom = DEFAULT_HEIGHT / localPlayer.getRadius() / 10;
        }

        /**
         if (localPlayer.getCenterX() > 5 * localPlayer.getRadius()
                && DEFAULT_WIDTH - localPlayer.getCenterX() < 5 * localPlayer.getRadius()) {
            x = localPlayer.getCenterX() - 5 * localPlayer.getRadius() * DEFAULT_WIDTH / DEFAULT_HEIGHT;
        }
        if (localPlayer.getCenterY() > 5 * localPlayer.getRadius()
                && DEFAULT_HEIGHT - localPlayer.getCenterY() < 5 * localPlayer.getRadius()) {
            y = localPlayer.getCenterY() - 5 * localPlayer.getRadius();
        }
         */
        x = localPlayer.getCenterX() - 5 * localPlayer.getRadius() * DEFAULT_WIDTH / DEFAULT_HEIGHT;
        y = localPlayer.getCenterY() - 5 * localPlayer.getRadius();

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
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        g2.scale(zoom, zoom); //enlarge whole map
        g2.translate(-x, -y); //move the pen to the right place

        g2.drawImage(background, 0, 0, this); //draw the background
        // Paint in a sorted order so smaller objects are painted below bigger ones
        Matter[] ms = new Matter[matterList.size()];
        matterList.toArray(ms);
        Arrays.sort(ms);

        for (Matter m : ms) {
            if (m.equals(localPlayer) || m.equals(remotePlayer)) {
                g2.setColor(player);
            } else {
                g2.setColor(npc);
            }
            g2.fill(m);
        }

        g2.setColor(Color.white);
        // Print winning or losing message
        if (localPlayer.getArea() <= 0.1) {
            g2.drawString("You lose", 10, 10);
        }
        if (localPlayer.getArea() >= goalArea) {
            g2.drawString("You win", 10, 10);
        }

        Toolkit.getDefaultToolkit().sync();
        g.dispose();
    }

    /**
     * This method runs the game. It simple updates every object,
     * repaints the screen and then sleep for the specified amount
     * of time.
     */
    public void run() {
        long beforeTime, timeDiff, sleep;
        beforeTime = System.currentTimeMillis();

        while (true) {

            zoom();
            update();
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
     * Updates every matter object in the game. This method will get long
     * so it will be best to try and split it up into many smaller methods.
     */
    private void update() {
        // Detect collisions
        for (Matter m : matterList) {
            for (Matter n : matterList) {
                if (m.equals(n)) {
                    continue;
                }

                if (m.intersects(n) && m.isBigger(n)) {
                    if (m.completelyConsumes(n)) {
                        m.setArea(m.getArea() + n.getArea());
                        n.setArea(0.0);
                    } else {
                        double d = n.getRadius() + m.getRadius()
                                - Math.hypot(m.getCenterX() - n.getCenterX(), m.getCenterY() - n.getCenterY());
                        double area = 0.03 * d * n.getArea();
                        //double area = 0.03 * n.getArea();
                        m.setArea(m.getArea() + area);
                        n.setArea(n.getArea() - area);
                    }

                    // Now changeMove the blob if it is outside the the screen
                    double xOffset = m.getBounds2D().getMaxX() - getWidth();
                    if (xOffset > 0) {
                        Rectangle2D boundingBox = m.getBounds2D();
                        boundingBox.setFrame(boundingBox.getX() - xOffset,
                                boundingBox.getY(),
                                boundingBox.getWidth(),
                                boundingBox.getHeight());
                        m.setFrame(boundingBox);
                    }


                    double yOffset = m.getBounds2D().getMaxY() - getHeight();
                    if (yOffset > 0) {
                        Rectangle2D boundingBox = m.getBounds2D();
                        boundingBox.setFrame(boundingBox.getX(),
                                boundingBox.getY() - yOffset,
                                boundingBox.getWidth(),
                                boundingBox.getHeight());
                        m.setFrame(boundingBox);
                    }
                }
            }
        }
        // Change dy/dx if necessary
        for (Matter m : matterList) {
            if (m.getX() <= 0 || m.getMaxX() >= getWidth()) {
                m.setDx(-m.getDx());
            }
            if (m.getY() <= 0 || m.getMaxY() >= getHeight()) {
                m.setDy(-m.getDy());
            }
            m.update();
        }
    }

    /**
     * Invoked when a key has been pressed. See the class description for
     * {@link java.awt.event.KeyEvent} for a definition of a key pressed
     * event.
     *
     * @param e the key event
     */
    @Override
    public void keyPressed(final KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_F11) {
            isFullScreen = (isFullScreen) ? false : true;
            System.out.println("Switch modes");
        }
    }

    /** Invoked when a mouse button has been pressed on a component. */
    @Override
    public void mousePressed(MouseEvent e) {
        Matter m = localPlayer.changeMove(e.getX() / zoom + x, e.getY() / zoom + y);
        if (m != null) {
            matterList.add(m);
        }
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
    // </editor-fold>
}

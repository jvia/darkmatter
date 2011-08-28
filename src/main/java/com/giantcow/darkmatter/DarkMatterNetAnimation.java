package com.giantcow.darkmatter;

import com.giantcow.darkmatter.Utils;
import com.giantcow.darkmatter.net.Client;
import com.giantcow.darkmatter.net.Server;
import com.giantcow.darkmatter.player.Matter;
import java.io.IOException;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.awt.geom.AffineTransform;
import java.awt.geom.Rectangle2D;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.awt.image.BufferedImageOp;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;

/**
 * Runs the whole show. This class can run in two modes: single player and multiplayer.
 * <p/>
 * If running in single player mode, a local server is started. This is required because all game
 * logic actually runs on the server.
 * <p/>
 * If running a multiplayer game then a the client will attempt to connect to the given IP address.
 *
 * @author Jeremiah M. Via <jxv911@cs.bham.ac.uk>
 * @author Joss Greenaway  <jtg897@cs.bham.ac.uk>
 * @author Yukun Wang      <yxw999@cs.bham.ac.uk>
 */
public class DarkMatterNetAnimation extends JComponent implements MouseListener, MouseWheelListener, Runnable {

    /**
     * Runs the game in a thread so it can be called from a menu and not break the Event Dispatch
     * Thread. If the game is single player, a server will be started and then the game will begin.
     */
    @Override
    public void run() {
        try {
            if (singlePlayer) {
                startServer();
            }

            Thread.sleep(500);
            makeConnection();
            Thread.sleep(500);
            try {
                init();
            } catch (IOException ex) {
                Logger.getLogger(DarkMatterNetAnimation.class.getName()).log(Level.SEVERE, null, ex);
            }
            runGame();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
    // GUI Variables
    public static int DEFAULT_WIDTH = (int) Utils.gameDimension.getWidth();
    public static int DEFAULT_HEIGHT = (int) Utils.gameDimension.getHeight();
    private double zoom = 1;
    private double zoomLevel = 5;
    private Image bg = Toolkit.getDefaultToolkit().createImage(getClass().getClassLoader().getResource("nebbg.jpg"));
    private Rectangle2D border = new Rectangle2D.Double(0, 0, DEFAULT_WIDTH - 1, DEFAULT_HEIGHT - 1);
    private JPanel rootPanel;
    // the distance pen need to move
    private double x = 0;
    private double y = 0;
    // Game Variables
    private final List<Matter> matterList = Collections.synchronizedList(new ArrayList<Matter>());
    private int me;
    private boolean singlePlayer;
    private double goalRadius;
    private boolean lose = false;
    private boolean win = false;
    Server server;
    Client client;
    String host;
    int port;
    private double rotateV = 0;
    private float t = (float) 0.1;
    private double dt = 0.005;
    private Image player1 = null;
    private Image player2 = null;
    private Image player3 = null;
    private Image player4 = null;
    private Image bigS = null;
    private Image interS = null;
    private Image smallS = null;
    private Image sparkle = null;

    /**
     * Create the game object.
     *
     * @param panel parent panel to switch back to after finishing
     * @param host  the server host
     * @param port  the server port
     */
    public DarkMatterNetAnimation(JPanel panel, String host, int port) {
        // Setup our component
        setDoubleBuffered(true);
        setOpaque(true);
        setIgnoreRepaint(true);
        addMouseListener(this);
        addMouseWheelListener(this);
        setPreferredSize(new Dimension(DEFAULT_WIDTH, DEFAULT_HEIGHT));

        singlePlayer = true;
        rootPanel = panel;
        this.host = host;
        this.port = port;
    }

    /**
     * Creates a single player version of the game which will create a server on the localhost.
     *
     * @param panel parent panel to switch back to after finishing
     */
    public DarkMatterNetAnimation(JPanel panel) {
        this(panel, "localhost", 1234);
    }

    /**
     * Set the host to connect to.
     *
     * @param host server host
     */
    public void setHost(String host) {
        this.host = host;
    }

    /**
     * Set the port to connect to on the server.
     *
     * @param port server port
     */
    public void setPort(int port) {
        this.port = port;
    }

    /**
     * Resets the game by closing and releasing all resources. This sets the game up to be used
     * again.
     */
    public void reset() {
        client.shutdown();
        server.shutdown();

        client = null;
        server = null;

        matterList.clear();
        lose = false;
        win = false;
        goalRadius = 0.0;
        me = 0;
    }

    /** Starts the server and loads a level to play. */
    public void startServer() {
        server = new Server(port);
        server.loadLevel("02.lvl");
        server.start();
    }

    /** Creates the client and connects to the server. */
    public void makeConnection() {
        // Start up client
        client = new Client(host, port);
        client.waitUntilReady();
        me = client.whoAmI();
        System.out.println("ME: " + me);
        client.start();
    }

    /** Adds all the matter objects to the initial game state. */
    public void init() throws IOException {
        List<Matter> l = client.getGameState();
        synchronized (matterList) {
            matterList.addAll(l);
        }

        calculateGoalArea();
        zoom = DEFAULT_HEIGHT / matterList.get(me).getRadius() / 10;

        player1 = ImageIO.read(getClass().getClassLoader().getResource("GameElements/player1.png"));
        player2 = ImageIO.read(getClass().getClassLoader().getResource("GameElements/player2.png"));
        player3 = ImageIO.read(getClass().getClassLoader().getResource("GameElements/player3.png"));
        player4 = ImageIO.read(getClass().getClassLoader().getResource("GameElements/player4.png"));
        smallS = ImageIO.read(getClass().getClassLoader().getResource("GameElements/simple_small.png"));
        interS = ImageIO.read(getClass().getClassLoader().getResource("GameElements/simple_inter.png"));
        bigS = ImageIO.read(getClass().getClassLoader().getResource("GameElements/simple_big.png"));
        sparkle = ImageIO.read(getClass().getClassLoader().getResource("GameElements/sparkle.png"));
    }

    /** Calculates the area needed to win. */
    private void calculateGoalArea() {
        double radius = 0.0;
        synchronized (matterList) {
            for (Matter m : matterList) {
                double r = m.getArea();
                radius += Double.isNaN(r) ? 0.0 : r;
            }
        }
        goalRadius = 0.6 * radius;
    }

    /**
     * Modifies how the graphics object will paint to give the effect of zooming and scrolling.
     * <p/>
     * Calculate the zoomLevel and the distance pen should move. The initial zoomLevel is
     * 'zoomLevel' times, means the screen width is 5 times of local player matter's diameter if it
     * is possible.
     */
    private void zoom() {
        double r = matterList.get(me).getRadius();
        double cx = matterList.get(me).getCenterX();
        double cy = matterList.get(me).getCenterY();
        double currentZoom = DEFAULT_HEIGHT / matterList.get(me).getRadius() / zoomLevel / 2;

        // Verify that we can change the zoom level
        if (currentZoom > 1 && currentZoom < 10) {
            zoom = (currentZoom - zoom) / zoomLevel + zoom;
        } else {
            zoom = 1;
        }

        if (zoom > 1) {
            if (cy < zoomLevel * r) {
                y = 0;
            } else if (DEFAULT_HEIGHT - cy >= zoomLevel * r) {
                y = cy - DEFAULT_HEIGHT / 2 / zoom;
            } else {
                y = (zoom - 1) * DEFAULT_HEIGHT / zoom;
            }

            if (cx < zoomLevel * r * DEFAULT_WIDTH / DEFAULT_HEIGHT) {
                x = 0;
            } else if (DEFAULT_WIDTH - cx >= zoomLevel * r * DEFAULT_WIDTH / DEFAULT_HEIGHT) {
                x = cx - DEFAULT_WIDTH / 2 / zoom;
            } else {
                x = (zoom - 1) * DEFAULT_WIDTH / zoom;

            }
        } else {
            y = 0;
            x = 0;
        }

        if (1 == zoom) {
            zoomLevel = DEFAULT_HEIGHT / matterList.get(me).getRadius() / zoom / 2;
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
        g2.drawImage(bg, null, this);
        g2.setColor(Color.orange);

        // Nothing else to paint; game likely hasn't started
        if (matterList == null || matterList.isEmpty()) {
            return;
        }


        g2.scale(zoom, zoom); //enlarge whole map
        g2.translate(-x, -y); //move the pen to the right place

        // Paint in a sorted order so smaller objects are painted below bigger ones
        List<Matter> toPaint;
        synchronized (matterList) {
            toPaint = new ArrayList<Matter>(matterList);
        }

        Collections.sort(toPaint);
        for (Matter m : toPaint) {
            if (m == null) {
                continue;
            } else {
                g2.translate(m.getX(), m.getY());
                if (m.equals(matterList.get(me))) {
                    g2.drawImage(player3, imageOp(m, 0), this);
                    g2.drawImage(player1, imageOp(m, -rotateV), this);
                    g2.drawImage(player2, imageOp(m, rotateV), this);
                    //g2.drawImage((BufferedImage) player7, imageOp(m, 2*rotateV), (int) m.getX(), (int) m.getY());
                    g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_ATOP, t));
                    g2.drawImage(player4, imageOp(m, 0), this);
                    g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER));
                } else if (m.getArea() > 1.1 * matterList.get(me).getArea()) {
                    g2.drawImage(bigS, imageOp(m, 0), this);
                    g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_ATOP, t));
                    g2.drawImage(sparkle, imageOp(m, rotateV), this);
                    g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER));
                } else if (m.getArea() < 0.9 * matterList.get(me).getArea()) {
                    g2.drawImage(smallS, imageOp(m, 0), this);
                    g2.drawImage(sparkle, imageOp(m, rotateV), this);
                    g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_ATOP, t));
                    g2.drawImage(sparkle, imageOp(m, rotateV), this);
                    g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER));
                } else {
                    g2.drawImage(interS, imageOp(m, 0), this);
                    g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_ATOP, t));
                    g2.drawImage((BufferedImage) sparkle, imageOp(m, rotateV), this);
                    g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER));
                }
                g2.translate(-m.getX(), -m.getY());
            }
        }

        g2.setColor(Utils.border);
        g2.setStroke(new BasicStroke(2.0f));
        g2.draw(border);

        // Un-scale the graphics context
        g2.scale(1 / zoom, 1 / zoom);
        g2.translate(x, y);



        // Write an endgame message if appropriate
        g2.setFont(Utils.largeFont);
        g2.setColor(Color.white);
        if (win) {
            g2.drawString("You win", DEFAULT_WIDTH / 3, DEFAULT_HEIGHT / 3);
        }
        if (lose) {
            g2.drawString("You lose", DEFAULT_WIDTH / 3, DEFAULT_HEIGHT / 3);
        }

        // Sync and dispose Graphics context
        Toolkit.getDefaultToolkit().sync();
        g.dispose();
    }

    public AffineTransform imageOp(Matter m, double v) {
        double h = 2 * m.getRadius() / 256;
        AffineTransform t = new AffineTransform();
        t.scale(h, h);
        //AffineTransform rotate = new AffineTransform();
        t.rotate(v, 128, 128);
        //BufferedImageOp op = new AffineTransformOp(t, AffineTransformOp.TYPE_NEAREST_NEIGHBOR);
        return t;
    }

    /**
     * This method runs the game. It simple updates every object, repaints the screen and then sleep
     * for the specified amount of time.
     */
    public void runGame() {
        while (isGameRunning()) {
            update();
            zoom();
            repaint();
            try {
                Thread.sleep(20);
            } catch (InterruptedException ex) {
                Logger.getLogger(DarkMatterNetAnimation.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        // End game animation
        long time = System.currentTimeMillis() + 5000;
        while (System.currentTimeMillis() < time) {
            update();
            zoom();
            repaint();
        }

        // Switch back to the main menu
        CardLayout cl = (CardLayout) rootPanel.getLayout();
        cl.show(rootPanel, "menu");

        // Reset everything in the game
        reset();
    }

    public boolean isGameRunning() {
        return !lose && !win;
    }

    /**
     * Updates every matter object in the game. This method will get long so it will be best to try
     * and split it up into many smaller methods.
     */
    private void update() {
        synchronized (matterList) {
            matterList.clear();
            matterList.addAll(client.getGameState());
        }

        // Check for end game condtions
        if (hasLost()) {
            lose = true;
        } else if (hasWon()) {
            win = true;
        }

        if (rotateV + 0.002 < 2 * Math.PI) {
            rotateV = rotateV + 0.005;
        } else {
            rotateV = rotateV + 0.002 - 2 * Math.PI;
        }
        if (t + dt > 0.5 || t + dt < 0.1) {
            dt = -dt;
        } else {
            t = (float) (t + dt);
        }

    }

    /**
     * Checks to see if the local player has met any of the losing conditions.
     *
     * @return true if lost; otherwise false
     */
    private boolean hasLost() {
        return matterList.get(me) == null
                || matterList.get(me).getId() != me
                || Double.isNaN(matterList.get(me).getArea())
                || matterList.get(me).getArea() < 1.0;
    }

    /**
     * Checks to see if the local player has met the winning condition.
     *
     * @return true if won; otherwise false
     */
    private boolean hasWon() {
        double area;
        synchronized (this) {
            area = matterList.get(me).getArea();
        }
        return area >= goalRadius;
    }
    Point paint = new Point(0, 0);

    /**
     * Sends a users click to the client which will be sent to the server. Makes sure to translate
     * the click based on the current level of zoom.
     *
     * @param e the mouse event
     */
    @Override
    public void mousePressed(MouseEvent e) {
        Point pt = e.getPoint();
        // Need to translate or else values will be off
        pt.x = (int) ((pt.x / zoom) + x);
        pt.y = (int) ((pt.y / zoom) + y);
        client.sendClick(pt);
    }

    /**
     * Allows the user to change their level of zoom.
     *
     * @param e mouse event
     */
    @Override
    public void mouseWheelMoved(MouseWheelEvent e) {
        double n = e.getUnitsToScroll();
        double z = zoomLevel;
        if (z + (n / 5) > 2) {
            zoomLevel = zoomLevel + (n / 5);
        }
    }

    /**
     * Switch to make the game a single player game.
     *
     * @param b true if single player
     */
    public void setSinglePlayer(boolean b) {
        singlePlayer = b;
    }

    // <editor-fold desc="Unused event methods">
    @Override
    public void mouseClicked(MouseEvent e) {
    }

    @Override
    public void mouseReleased(MouseEvent e) {
    }

    @Override
    public void mouseEntered(MouseEvent e) {
    }

    @Override
    public void mouseExited(MouseEvent e) {
    }
    // </editor-fold>
}

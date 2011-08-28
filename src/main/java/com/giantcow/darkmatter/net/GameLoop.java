package com.giantcow.darkmatter.net;

import com.giantcow.darkmatter.Utils;
import com.giantcow.darkmatter.player.HumanMatter;
import com.giantcow.darkmatter.player.Matter;

import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * This thread runs the game. It updates the positions of each entity in the game and deals with
 * their interactions.
 *
 * @author Jeremiah M. Via <jxv911@cs.bham.ac.uk>
 * @version 2011.0311
 */
public final class GameLoop extends Thread {

    boolean running = false;
    private static GameLoop gl = new GameLoop();
    public static int WIDTH = Utils.gameDimension.width;
    public static int HEIGHT = Utils.gameDimension.height;

    /** Creates a private GameLoop. There can only be one instance of this class. */
    private GameLoop() {}

    /**
     * Runs the GameLoop. The GameLoop runs in its own thread and constantly updates the position of
     * the players in the game.
     */
    @Override
    public void run() {
        running = true;
        while (running) {
            update();
            try {
                sleep(20);
            } catch (InterruptedException ex) {
                Logger.getLogger(GameLoop.class.getName()).log(Level.SEVERE, null, ex);
            }

//            if (!Server.hasMinimumPlayers()) stopLoop();
        }
    }

    /**
     * Returns the single instance of the GameLoop
     *
     * @return game loop
     */
    public static GameLoop getGL() {
        return gl;
    }

    /** Stops the execution of the game loop. */
    public void stopLoop() {
        System.out.println("Stopping game loop");
        running = false;
    }

    /**
     * Returns the current state of the GameLoop.
     *
     * @return true if running, false otherwise
     */
    public boolean isRunning() {
        return running;
    }

    public static void reset() {
        gl.stopLoop();
        gl = new GameLoop();
        synchronized (Server.GSTATE) {
            Server.GSTATE.clear();
        }
    }

    /** Updates the position of all Matter objects in the game. */
    private void update() {
        // remove too small matter objects
        deleteSmall();

        // Detect collisions
        synchronized (Server.GSTATE) {
            for (Matter m : Server.GSTATE) {
                for (Matter n : Server.GSTATE) {
                    if (m.equals(n)) continue;

                    if (m.intersects(n) && m.isBigger(n)) {
                        if (m.completelyConsumes(n)) {
                            m.setDx(m.getDx() + n.getDx() / m.getRadius());
                            m.setDy(m.getDy() + n.getDy() / m.getRadius());
                            m.setArea(m.getArea() + n.getArea());
                            n.setArea(0);
                        } else {
                            double d = m.distance(n);
                            double a = m.getArea() + n.getArea();
                            double r1 = (2 * d + Math.sqrt(8 * a / Math.PI - 4 * d * d)) / 4;
                            double r2 = (2 * d - Math.sqrt(8 * a / Math.PI - 4 * d * d)) / 4;
                            m.setRadius(r1);
                            n.setRadius(r2);
                        }
                    }
                }
            }
        }

        updateDirection();
        updateMovement();
    }

    /**
     * Reverse the direction in either the x or y axis if the Matter object is about togo out of
     * bounds.
     */
    private void updateDirection() {
        synchronized (Server.GSTATE) {
            // Change dy/dx if necessary
            for (Matter m : Server.GSTATE) {
                if (m.getX() <= 0 || m.getMaxX() >= WIDTH) {
                    m.setDx(-m.getDx());
                }
                if (m.getY() <= 0 || m.getMaxY() >= HEIGHT) {
                    m.setDy(-m.getDy());
                }
                m.update();
            }
        }
    }

    /** Allows each matter object to change the way it is moving by expelling matter. */
    private void updateMovement() {
        synchronized (Server.GSTATE) {
            for (Matter m : Server.GSTATE) {
                // HumanMatter is update by clicks on the client side
                // so we don't do it here
                if (m instanceof HumanMatter) continue;

                // Allow the computer matter objects to move. If they
                // move then add their expelled matter to the game.
                Matter move = m.changeMove(0, 0, Server.GSTATE);
                if (move != null)
                    Server.GSTATE.add(move);
            }
        }
    }

    /** Removes any small matter objects from the game. */
    private void deleteSmall() {
        ArrayList<Matter> remove = new ArrayList<Matter>();

        // Collect any sufficiently small matter objects
        synchronized (Server.GSTATE) {
            for (Matter m : Server.GSTATE) {
                if (m == null) continue;

                if (m.getArea() <= 0.1)
                    remove.add(m);
            }
        }

        // Remove them all in one go
        synchronized (Server.GSTATE) {
            Server.GSTATE.removeAll(remove);
        }
    }
}

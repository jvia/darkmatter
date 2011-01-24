package com.giantcow.darkmatter;

import java.awt.Graphics;
import java.awt.Image;

/**
 * Represents an image in the game.
 *
 * This class should never be created directly but should in fact
 * be created with the SpriteFactory.
 *
 * @author  Jeremiah Via <jxv911@cs.bham.ac.uk>
 * @version 2011.0124
 * @since   0.1
 */
public class Sprite {

    /** The game sprite. */
    private Image sprite;

    /**
     * Creates the game sprite.
     *
     * @param sprite the sprite object
     */
    public Sprite(final Image sprite) {
        this.sprite = sprite;
    }

    /**
     * Returns the height of the sprite.
     *
     * @return the sprite height
     */
    public final int getHeight() {
        return sprite.getHeight(null);
    }

    /**
     * Returns the width of the sprite.
     *
     * @return the sprite width
     */
    public final int getWidth() {
        return sprite.getWidth(null);
    }

    /**
     * Draws the sprite using the given graphics context at the
     * location specified by the x and y coordinates.
     *
     * @param g the graphics context
     * @param x x-coordinate
     * @param y y-coordinate
     */
    public final void draw(final Graphics g, final int x, final int y) {
        g.drawImage(sprite, x, y, null);
    }
}

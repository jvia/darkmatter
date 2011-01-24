package com.giantcow.darkmatter;

import java.awt.GraphicsConfiguration;
import java.awt.GraphicsEnvironment;
import java.awt.Image;
import java.awt.Transparency;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.HashMap;
import javax.imageio.ImageIO;

/**
 * A factory class that creates sprites. This is the class to use
 * when creating sprites because it will look in the cache to see
 * if it has loaded the same image before instead of going out to
 * disk to load it. This should save a considerable amount of time
 * when loading dozens of the same sprite.
 * 
 * @author  Jeremiah Via
 * @version 2011.0124
 * @since   0.1
 * @see     com.giantcow.darkmatter.Sprite
 */
public class SpriteFactory {

    /** The single instance of this class. */
    private static SpriteFactory factory = new SpriteFactory();
    /**
     * The 'cache' for the sprites. A sprite only needs to
     * loaded once from disk
     */
    private HashMap<String, Sprite> sprites;

    /**
     * The constructor is private so no one can initialize it.
     *
     * There is only one instance of this class ever running.
     */
    private SpriteFactory() {
        sprites = new HashMap<String, Sprite>();
    }

    /**
     * Returns a reference to the single instance of this
     * class.
     *
     * @return the sprite factory
     */
    public static SpriteFactory producer() {
        return factory;
    }

    /**
     * Creates a sprite based on a URL to an image.
     *
     * Checks first to see if the sprite has already been loaded
     * and if not will get it from disk and add it to the HashMap.
     * If the sprite is needed again, it will be retrieved from the
     * HashMap instead of going all the way out to disk again.
     * 
     * @param url the location of the image
     * @return a new sprite
     */
    public Sprite generateSprite(String url) {
        if (sprites.containsKey(url))
            return sprites.get(url);

        BufferedImage srcImage = null;

        try {
            srcImage = ImageIO.read(this.getClass().getClassLoader().getResource(url));
        } catch (IOException e) {
            System.err.println("Error reading image: " + e.getLocalizedMessage());
            System.exit(1);
        }

        // Create sprite image
        GraphicsEnvironment environment = GraphicsEnvironment.getLocalGraphicsEnvironment();
        GraphicsConfiguration config = environment.getDefaultScreenDevice().getDefaultConfiguration();
        Image spriteImage = config.createCompatibleImage(srcImage.getWidth(), srcImage.getHeight(), Transparency.BITMASK);
        spriteImage.getGraphics().drawImage(srcImage, 0, 0, null);

        // Create sprite and add it to hash map
        Sprite sprite = new Sprite(spriteImage);
        sprites.put(url, sprite);

        return sprite;
    }
}

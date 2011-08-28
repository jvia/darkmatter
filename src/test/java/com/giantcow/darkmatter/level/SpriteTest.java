package com.giantcow.darkmatter.level;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

/**
 * Tests the Sprite class.
 *
 * @author Jeremiah M. Via <jxv911@cs.bham.ac.uk>
 * @version 2011.0320
 */
public class SpriteTest extends TestCase {

    private Sprite sprite;
    private int expectedHeight;
    private int expectedWidth;

    public SpriteTest() {
        super("Sprite Test");
    }

    public static Test suite() {
        return new TestSuite(SpriteTest.class);
    }

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        String ref = "other.gif";
        BufferedImage sourceImage = null;

        try {
            sourceImage = ImageIO.read(this.getClass().getClassLoader().getResource(ref));
        } catch (IOException e) {
            fail("Failed to load: " + ref);
        }

        GraphicsConfiguration configuration =
                GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice().getDefaultConfiguration();
        Image image =
                configuration.createCompatibleImage(sourceImage.getWidth(), sourceImage.getHeight(), Transparency.BITMASK);
        image.getGraphics().drawImage(sourceImage, 0, 0, null);

        expectedHeight = sourceImage.getHeight();
        expectedWidth = sourceImage.getWidth();
        sprite = new Sprite(image);
    }

    /** Test of getY2 method, of class Sprite. */
    public void testGetHeight() {
        System.out.println("getY2");
        assertEquals(expectedHeight, sprite.getHeight());
    }

    /** Test of getX2 method, of class Sprite. */
    public void testGetWidth() {
        System.out.println("getX2");
        assertEquals(expectedWidth, sprite.getWidth());
    }

    /** Test of draw method, of class Sprite. */
    public void testDraw() {
        System.out.println("draw");
        JFrame f = new JFrame();
        f.setSize(300, 300);
        f.add(new Component() {
            public void paintComponent(Graphics g) {
                sprite.draw(g, 0, 0);
            }
        });
        f.setVisible(true);
        assertEquals(1, f.getComponentCount());
    }

    /** Test of getImage method, of class Sprite. */
    public void testGetImage() {
        System.out.println("getImage");
        Image result = sprite.getImage();
        assertNotNull(result);
    }

}

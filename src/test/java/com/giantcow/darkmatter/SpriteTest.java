package com.giantcow.darkmatter;

import java.awt.Component;
import java.awt.Graphics;
import java.awt.GraphicsConfiguration;
import java.awt.GraphicsEnvironment;
import java.awt.Image;
import java.awt.Transparency;
import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.JFrame;
import junit.framework.TestCase;

/**
 * Tests the Sprite class.
 *
 * @author Jeremiah Via
 */
public class SpriteTest extends TestCase {

    private Sprite sprite;
    private int expectedHeight;
    private int expectedWidth;

    public SpriteTest() {
        super("Sprite Test");
    }

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        String ref = "player.gif";
        BufferedImage sourceImage = null;

        try {
            sourceImage = ImageIO.read(this.getClass().getClassLoader().getResource(ref));
        } catch (IOException e) {
            fail("Failed to load: " + ref);
        }

        GraphicsConfiguration graphconf = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice().getDefaultConfiguration();
        Image image = graphconf.createCompatibleImage(sourceImage.getWidth(), sourceImage.getHeight(), Transparency.BITMASK);
        image.getGraphics().drawImage(sourceImage, 0, 0, null);

        expectedHeight = sourceImage.getHeight();
        expectedWidth = sourceImage.getWidth();
        sprite = new Sprite(image);
    }

    /**
     * Test of getHeight method, of class Sprite.
     */
    public void testGetHeight() {
        System.out.println("getHeight");
        assertEquals(expectedHeight, sprite.getHeight());
    }

    /**
     * Test of getWidth method, of class Sprite.
     */
    public void testGetWidth() {
        System.out.println("getWidth");
        assertEquals(expectedWidth, sprite.getWidth());
    }

    /**
     * Test of draw method, of class Sprite.
     */
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
}

package com.giantcow.darkmatter;

import junit.framework.TestCase;

/**
 * Tests SpriteFactory.
 * 
 * @author Jeremiah M. Via <jxv911@cs.bham.ac.uk>
 */
public class SpriteFactoryTest extends TestCase {
    
    public SpriteFactoryTest(String testName) {
        super(testName);
    }

    @Override
    protected void setUp() throws Exception {
        super.setUp();
    }

    @Override
    protected void tearDown() throws Exception {
        super.tearDown();
    }

    /**
     * Test of producer method, of class SpriteFactory.
     */
    public void testProducer() {
        System.out.println("producer");
        assertNotNull(SpriteFactory.producer());
    }

    /**
     * Test of generateSprite method, of class SpriteFactory.
     */
    public void testGenerateSprite() {
        System.out.println("generateSprite");
        String url = "player.gif";
        assertNotNull(SpriteFactory.producer().generateSprite(url));
    }

    /**
     * Tests generateSprite. Should throw an exception to
     * indicate fault data has been passed in.
     */
    public void testGenerateSpriteNull() {
        System.out.println("generateSprite -- no resource");
        String url = "does_not_exists.gif";
        try {
        Sprite s = SpriteFactory.producer().generateSprite(url);
        } catch (IllegalArgumentException iae) {
            return;
        }
        fail("No exception thrown!");
    }

}

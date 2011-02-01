/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.giantcow.darkmatter;

import java.awt.geom.Ellipse2D;
import java.awt.geom.Ellipse2D.Double;
import junit.framework.TestCase;

/**
 *
 * @author jtg897
 */
public class MatterTest extends TestCase {

    public Matter Test1;
    public Matter Test2;
    
    public MatterTest() {
        super("Matter Tests");
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
     * Test of intersects method, of class Matter.
     */
    public void testIntersects() {
        System.out.println("intersects");
        Test1 = new Matter(0.0, 0.0, 10, 0.0, 0.0);
        Test2 = new Matter(0.0, 0.0, 10, 0.0, 0.0);
        boolean expResult = true;
        boolean result = Test1.intersects(Test2);
        assertEquals(expResult, result);       
        
    }

    /**
     * Test of collision method, of class Matter.
     */
    /*public void testCollision() {
        System.out.println("collision");
        Matter other = null;
        Matter instance = null;
        instance.collision(other);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }
     */

    /**
     * Test of update method, of class Matter.
     */
    public void testUpdate() {
        System.out.println("update");
        Matter instance = new Matter(0.0, 0.0, 10, 5.0, 5.0);
        double x = instance.getBlob().getX();
        double y=  instance.getBlob().getY();
        instance.update();
        double x1 = instance.getBlob().getX();
        double y1 = instance.getBlob().getY();
        assertEquals(x + instance.getDx(), x1);
        assertEquals(y + instance.getDy(), y1);


    }

}

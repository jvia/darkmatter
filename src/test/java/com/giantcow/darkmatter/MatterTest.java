package com.giantcow.darkmatter;

import com.giantcow.darkmatter.player.Matter;
import java.awt.geom.Point2D;
import junit.framework.TestCase;

/**
 * Unit tests for the Matter class.
 * 
 * @author Jeremiah M. Via <jxv911@cs.bham.ac.uk>
 * @author Joss Greenaway <jtg897@cs.bham.ac.uk>
 */
public class MatterTest extends TestCase {

    public MatterTest() {
        super("Matter Tests");
    }

    /**
     * Test of intersects method, of class Matter.
     */
    public void testIntersects() {
        System.out.println("intersects");
        Matter instance = new Matter(0.0, 0.0, 10, 0.0, 0.0);
        Matter other = new Matter(0.0, 0.0, 10, 0.0, 0.0);
        boolean expResult = true;
        boolean result = instance.intersects(other);
        assertEquals(expResult, result);

    }

    /**
     * Test of intersect method when only one point is touching.
     */
    public void testIntersects2() {
        System.out.println("One point intersection");
        Matter other = new Matter(0.0, 0.0, 1.0, 0.0, 0.0);
        Matter instance = new Matter(1.99, 0.0, 1.0, 0.0, 0.0);
        boolean expResult = true;
        boolean result = instance.intersects(other);
        assertEquals(expResult, result);
    }

    /**
     * Test of update method, of class Matter.
     */
    public void testUpdate() {
        System.out.println("update");
        Matter instance = new Matter(0.0, 0.0, 10, 5.0, 5.0);
        double x = instance.getX();
        double y = instance.getY();
        instance.update();
        double x1 = instance.getX();
        double y1 = instance.getY();
        assertEquals(x + instance.getDx(), x1);
        assertEquals(y + instance.getDy(), y1);
    }

    /**
     * Test if getArea if returning the right value.
     */
    public void testGetArea() {
        System.out.println("getArea");
        Matter instance = new Matter(0.0, 0.0, 5.0, 0.0, 0.0);
        double expResult = Math.PI * Math.pow(5.0, 2.0);
        assertEquals(expResult, instance.getArea(), 0.001);
    }

    /**
     * Test if complete intersection is happening. It should in this case
     * it should be true.
     */
    public void testCompletelyConsumes() {
        System.out.println("Completely Consumes");
        Matter instance = new Matter(0.0, 0.0, 100.0, 0.0, 0.0);
        Matter other = new Matter(50.0, 50.0, 6.0, 0.0, 0.0);
        assertTrue(instance.completelyConsumes(other));
    }

    /**
     * Test if complete intersection is happening. It should in this case
     * it should be false because the circle will have a tiny amount
     * just outside the other circle.
     */
    public void testCompletelyConsumes2() {
        System.out.println("Completely Consumes");
        Matter instance = new Matter(0.0, 0.0, 100.0, 0.0, 0.0);
        Matter other = new Matter(-1.0, 0.0, 6.0, 0.0, 0.0);
        assertFalse(instance.completelyConsumes(other));
    }

    public void testVelocity() {
        System.out.println("Velocity test");
        Matter instance = new Matter(0.0, 0.0, 0.0, 3.0, 4.0);
        double expResult = 5.0;
        double result = instance.getVelocity();
        assertEquals(expResult, result, 0.0);
    }

    public void testVelocity2() {
        System.out.println("Velocity test 2");
        Matter instance = new Matter(0.0, 0.0, 0.0, 0.0, 0.0);
        double expResult = 0.0;
        double result = instance.getVelocity();
        assertEquals(expResult, result, 0.0);
    }

    public void testCompareTo1() {
        System.out.println("Compare To 1");
        Matter instance = new Matter(0.0, 0.0, 0.0, 0.0, 0.0);
        Matter other = new Matter(0.0, 0.0, 10.0, 0.0, 0.0);
        int result = instance.compareTo(other);
        assertTrue(result < 0.0);
    }

    public void testCompareTo2() {
        System.out.println("Compare To 2");
        Matter instance = new Matter(0.0, 0.0, 10.0, 0.0, 0.0);
        Matter other = new Matter(0.0, 0.0, 0.0, 0.0, 0.0);
        int result = instance.compareTo(other);
        assertTrue(result > 0.0);
    }

    public void testCompareTo3() {
        System.out.println("Compare To 3");
        Matter instance = new Matter(0.0, 0.0, 0.0, 0.0, 0.0);
        Matter other = new Matter(0.0, 0.0, 0.0, 0.0, 0.0);
        int result = instance.compareTo(other);
        assertTrue(result == 0.0);
    }

    public void testEquals() {
        System.out.println("equals");
        Matter instance = new Matter(0.0, 0.0, 0.0, 0.0, 0.0);
        Matter other = new Matter(0.0, 0.0, 0.0, 0.0, 0.0);
        assertEquals(other, instance);
    }
}

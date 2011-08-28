package com.giantcow.darkmatter.player;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.geom.PathIterator;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.util.Collection;

/**
 * @author Joss Greenaway <jtg897@cs.bham.ac.uk>
 * @author Jeremiah M. Via <jxv911@cs.bham.ac.uk>
 * @version 2011.0320
 */
public class MatterTest extends TestCase {

    public MatterTest(String testName) {
        super(testName);
    }

    public static Test suite() {
        TestSuite suite = new TestSuite(MatterTest.class);
        return suite;
    }

    @Override
    protected void setUp() throws Exception {
        super.setUp();
    }

    @Override
    protected void tearDown() throws Exception {
        super.tearDown();
    }

    /** Test of getId method, of class Matter. */
    public void testGetId() {
        System.out.println("getId");
        Matter.resetCount();
        Matter instance = new Matter(0.0, 0.0, 0.0, 0.0, 0.0);
        int expResult = 0;
        int result = instance.getId();
        assertEquals(expResult, result);
    }

    /** Test of getDx method, of class Matter. */
    public void testGetDx() {
        System.out.println("getDx");
        Matter instance = new Matter(0.0, 0.0, 0.0, 0.0, 5.0);
        double expResult = 5.0;
        double result = instance.getDx();
        assertEquals(expResult, result, 0.0);
    }

    /** Test of setDx method, of class Matter. */
    public void testSetDx() {
        System.out.println("setDx");
        double dx = 0.0;
        Matter instance = new Matter(0.0, 0.0, 0.0, 0.0, 5.0);
        instance.setDx(dx);
        assertEquals(0.0, instance.getDx(), 0.0);
    }

    /** Test of getDy method, of class Matter. */
    public void testGetDy() {
        System.out.println("getDy");
        Matter instance = new Matter(0.0, 0.0, 0.0, 5.0, 0.0);
        double expResult = 5.0;
        double result = instance.getDy();
        assertEquals(expResult, result, 0.0);
    }

    /** Test of setDy method, of class Matter. */
    public void testSetDy() {
        System.out.println("setDy");
        double dy = 0.0;
        Matter instance = new Matter(0.0, 0.0, 0.0, 5.0, 0.0);
        instance.setDy(dy);
        assertEquals(0.0, instance.getDy(), 0.0);
    }

    /** Test of getRadius method, of class Matter. */
    public void testGetRadius() {
        System.out.println("getRadius");
        Matter instance = new Matter(0.0, 0.0, 10.0, 0.0, 0.0);
        double expResult = 10.0;
        double result = instance.getRadius();
        assertEquals(expResult, result, 0.0);
    }

    /** Test of setRadius method, of class Matter. */
    public void testSetRadius() {
        System.out.println("setRadius");
        double r = 5.0;
        Matter instance = new Matter(0.0, 0.0, 10.0, 0.0, 0.0);
        instance.setRadius(r);
        assertEquals(r, instance.getRadius());
    }


//    /** Test of intersects method, of class Matter. */
//    public void testIntersects_Matter() {
//        System.out.println("intersects");
//        Matter other = null;
//        Matter instance = null;
//        boolean expResult = false;
//        boolean result = instance.intersects(other);
//        assertEquals(expResult, result);
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
//    }
//
//    /** Test of distance method, of class Matter. */
//    public void testDistance() {
//        System.out.println("distance");
//        Matter other = null;
//        Matter instance = null;
//        double expResult = 0.0;
//        double result = instance.distance(other);
//        assertEquals(expResult, result, 0.0);
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
//    }
//
//    /** Test of getCenterX method, of class Matter. */
//    public void testGetCenterX() {
//        System.out.println("getCenterX");
//        Matter instance = null;
//        double expResult = 0.0;
//        double result = instance.getCenterX();
//        assertEquals(expResult, result, 0.0);
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
//    }
//
//    /** Test of getCenterY method, of class Matter. */
//    public void testGetCenterY() {
//        System.out.println("getCenterY");
//        Matter instance = null;
//        double expResult = 0.0;
//        double result = instance.getCenterY();
//        assertEquals(expResult, result, 0.0);
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
//    }
//
//    /** Test of isBigger method, of class Matter. */
//    public void testIsBigger() {
//        System.out.println("isBigger");
//        Matter other = null;
//        Matter instance = null;
//        boolean expResult = false;
//        boolean result = instance.isBigger(other);
//        assertEquals(expResult, result);
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
//    }
//
//    /** Test of getVelocity method, of class Matter. */
//    public void testGetVelocity() {
//        System.out.println("getVelocity");
//        Matter instance = null;
//        double expResult = 0.0;
//        double result = instance.getVelocity();
//        assertEquals(expResult, result, 0.0);
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
//    }
//
//
//    /** Test of changeMove method, of class Matter. */
//    public void testChangeMove() {
//        System.out.println("changeMove");
//        double x = 0.0;
//        double y = 0.0;
//        Collection<Matter> matterList = null;
//        Matter instance = null;
//        Matter expResult = null;
//        Matter result = instance.changeMove(x, y, matterList);
//        assertEquals(expResult, result);
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
//    }
//
//    /** Test of setFrameFromCenter method, of class Matter. */
//    public void testSetFrameFromCenter() {
//        System.out.println("setFrameFromCenter");
//        double x = 0.0;
//        double y = 0.0;
//        double x1 = 0.0;
//        double y1 = 0.0;
//        Matter instance = null;
//        instance.setFrameFromCenter(x, y, x1, y1);
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
//    }
//
//    /** Test of expulsionCentres method, of class Matter. */
//    public void testExpulsionCentres() {
//        System.out.println("expulsionCentres");
//        double x = 0.0;
//        double y = 0.0;
//        double radius = 0.0;
//        Matter instance = null;
//        Point2D expResult = null;
//        Point2D result = instance.expulsionCentres(x, y, radius);
//        assertEquals(expResult, result);
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
//    }
//
//    /** Test of getBounds method, of class Matter. */
//    public void testGetBounds() {
//        System.out.println("getBounds");
//        Matter instance = null;
//        Rectangle expResult = null;
//        Rectangle result = instance.getBounds();
//        assertEquals(expResult, result);
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
//    }
//
//    /** Test of getBounds2D method, of class Matter. */
//    public void testGetBounds2D() {
//        System.out.println("getBounds2D");
//        Matter instance = null;
//        Rectangle2D expResult = null;
//        Rectangle2D result = instance.getBounds2D();
//        assertEquals(expResult, result);
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
//    }
//
//    /** Test of contains method, of class Matter. */
//    public void testContains_double_double() {
//        System.out.println("contains");
//        double x = 0.0;
//        double y = 0.0;
//        Matter instance = null;
//        boolean expResult = false;
//        boolean result = instance.contains(x, y);
//        assertEquals(expResult, result);
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
//    }
//
//    /** Test of contains method, of class Matter. */
//    public void testContains_Point2D() {
//        System.out.println("contains");
//        Point2D p = null;
//        Matter instance = null;
//        boolean expResult = false;
//        boolean result = instance.contains(p);
//        assertEquals(expResult, result);
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
//    }
//
//    /** Test of intersects method, of class Matter. */
//    public void testIntersects_4args() {
//        System.out.println("intersects");
//        double x = 0.0;
//        double y = 0.0;
//        double w = 0.0;
//        double h = 0.0;
//        Matter instance = null;
//        boolean expResult = false;
//        boolean result = instance.intersects(x, y, w, h);
//        assertEquals(expResult, result);
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
//    }
//
//    /** Test of intersects method, of class Matter. */
//    public void testIntersects_Rectangle2D() {
//        System.out.println("intersects");
//        Rectangle2D r = null;
//        Matter instance = null;
//        boolean expResult = false;
//        boolean result = instance.intersects(r);
//        assertEquals(expResult, result);
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
//    }
//
//    /** Test of contains method, of class Matter. */
//    public void testContains_4args() {
//        System.out.println("contains");
//        double x = 0.0;
//        double y = 0.0;
//        double w = 0.0;
//        double h = 0.0;
//        Matter instance = null;
//        boolean expResult = false;
//        boolean result = instance.contains(x, y, w, h);
//        assertEquals(expResult, result);
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
//    }
//
//    /** Test of contains method, of class Matter. */
//    public void testContains_Rectangle2D() {
//        System.out.println("contains");
//        Rectangle2D r = null;
//        Matter instance = null;
//        boolean expResult = false;
//        boolean result = instance.contains(r);
//        assertEquals(expResult, result);
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
//    }
//
//    /** Test of getPathIterator method, of class Matter. */
//    public void testGetPathIterator_AffineTransform() {
//        System.out.println("getPathIterator");
//        AffineTransform at = null;
//        Matter instance = null;
//        PathIterator expResult = null;
//        PathIterator result = instance.getPathIterator(at);
//        assertEquals(expResult, result);
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
//    }
//
//    /** Test of getPathIterator method, of class Matter. */
//    public void testGetPathIterator_AffineTransform_double() {
//        System.out.println("getPathIterator");
//        AffineTransform at = null;
//        double flatness = 0.0;
//        Matter instance = null;
//        PathIterator expResult = null;
//        PathIterator result = instance.getPathIterator(at, flatness);
//        assertEquals(expResult, result);
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
//    }
//
//    /** Test of getX method, of class Matter. */
//    public void testGetX() {
//        System.out.println("getX");
//        Matter instance = null;
//        double expResult = 0.0;
//        double result = instance.getX();
//        assertEquals(expResult, result, 0.0);
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
//    }
//
//    /** Test of getY method, of class Matter. */
//    public void testGetY() {
//        System.out.println("getY");
//        Matter instance = null;
//        double expResult = 0.0;
//        double result = instance.getY();
//        assertEquals(expResult, result, 0.0);
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
//    }
//
//    /** Test of setFrame method, of class Matter. */
//    public void testSetFrame() {
//        System.out.println("setFrame");
//        Rectangle2D frame = null;
//        Matter instance = null;
//        instance.setFrame(frame);
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
//    }

    /** Test of getMaxX method, of class Matter. */
    public void testGetMaxX() {
        System.out.println("getMaxX");
        Matter instance = new Matter(20.0, 100.0, 20.0, 0.0, 0.0);
        double expResult = 60.0;
        double result = instance.getMaxX();
        assertEquals(expResult, result, 0.0);
    }

    /** Test of getMaxY method, of class Matter. */
    public void testGetMaxY() {
        System.out.println("getMaxY");
        Matter instance = new Matter(20.0, 100.0, 20.0, 0.0, 0.0);
        double expResult = 140.0;
        double result = instance.getMaxY();
        assertEquals(expResult, result, 0.0);
    }

    /** Test of getMinX method, of class Matter. */
    public void testGetMinX() {
        System.out.println("getMinX");
        Matter instance = new Matter(20.0, 100.0, 20.0, 0.0, 0.0);
        double expResult = 20.0;
        double result = instance.getMinX();
        assertEquals(expResult, result, 0.0);
    }

    /** Test of getMinY method, of class Matter. */
    public void testGetMinY() {
        System.out.println("getMinY");
        Matter instance = new Matter(20.0, 100.0, 20.0, 0.0, 0.0);
        double expResult = 100.0;
        double result = instance.getMinY();
        assertEquals(expResult, result, 0.0);
    }

    /** Test of resetCount method, of class Matter. */
    public void testResetCount() {
        Matter.resetCount();
        Matter m = new Matter(0.0, 0.0, 0.0, 0.0, 0.0);
        int result = m.getId();
        int expResult = 0;
        assertEquals(result, expResult);
    }

    /** Test of intersects method, of class Matter. */
    public void testIntersects() {
        System.out.println("intersects");
        Matter instance = new Matter(0.0, 0.0, 10, 0.0, 0.0);
        Matter other = new Matter(0.0, 0.0, 10, 0.0, 0.0);
        boolean expResult = true;
        boolean result = instance.intersects(other);
        assertEquals(expResult, result);

    }

    /** Test of intersect method when only one point is touching. */
    public void testIntersects2() {
        System.out.println("One point intersection");
        Matter other = new Matter(0.0, 0.0, 1.0, 0.0, 0.0);
        Matter instance = new Matter(1.99, 0.0, 1.0, 0.0, 0.0);
        boolean expResult = true;
        boolean result = instance.intersects(other);
        assertEquals(expResult, result);
    }

    /** Test of update method, of class Matter. */
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

    /** Test if getArea if returning the right value. */
    public void testGetArea() {
        System.out.println("getArea");
        Matter instance = new Matter(0.0, 0.0, 5.0, 0.0, 0.0);
        double expResult = Math.PI * Math.pow(5.0, 2.0);
        assertEquals(expResult, instance.getArea(), 0.001);
    }

    /** Test if complete intersection is happening. It should in this case it should be true. */
    public void testCompletelyConsumes() {
        System.out.println("Completely Consumes: 1");
        Matter instance = new Matter(0.0, 0.0, 100.0, 0.0, 0.0);
        Matter other = new Matter(50.0, 50.0, 6.0, 0.0, 0.0);
        assertTrue(instance.completelyConsumes(other));
    }

    /**
     * Test if complete intersection is happening. It should in this case it should be false because the circle will
     * have a tiny amount just outside the other circle.
     */
    public void testCompletelyConsumes2() {
        System.out.println("Completely Consumes: 2");
        Matter instance = new Matter(0.0, 0.0, 100.0, 0.0, 0.0);
        Matter other = new Matter(-1.0, 0.0, 6.0, 0.0, 0.0);
        assertFalse(instance.completelyConsumes(other));
    }

    public void testVelocity() {
        System.out.println("Velocity test: 1");
        Matter instance = new Matter(0.0, 0.0, 0.0, 3.0, 4.0);
        double expResult = 5.0;
        double result = instance.getVelocity();
        assertEquals(expResult, result, 0.0);
    }

    public void testVelocity2() {
        System.out.println("Velocity test: 2");
        Matter instance = new Matter(0.0, 0.0, 0.0, 0.0, 0.0);
        double expResult = 0.0;
        double result = instance.getVelocity();
        assertEquals(expResult, result, 0.0);
    }

    public void testCompareTo1() {
        System.out.println("Compare To: 1");
        Matter instance = new Matter(0.0, 0.0, 0.0, 0.0, 0.0);
        Matter other = new Matter(0.0, 0.0, 10.0, 0.0, 0.0);
        int result = instance.compareTo(other);
        assertTrue(result < 0.0);
    }

    public void testCompareTo2() {
        System.out.println("Compare To: 2");
        Matter instance = new Matter(0.0, 0.0, 10.0, 0.0, 0.0);
        Matter other = new Matter(0.0, 0.0, 0.0, 0.0, 0.0);
        int result = instance.compareTo(other);
        assertTrue(result > 0.0);
    }

    public void testCompareTo3() {
        System.out.println("Compare To: 3");
        Matter instance = new Matter(0.0, 0.0, 0.0, 0.0, 0.0);
        Matter other = new Matter(0.0, 0.0, 0.0, 0.0, 0.0);
        int result = instance.compareTo(other);
        assertTrue(result == 0.0);
    }

    public void testEquals() {
        System.out.println("equals");
        Matter instance = new Matter(0.0, 0.0, 0.0, 0.0, 0.0, 1);
        Matter other = new Matter(0.0, 0.0, 0.0, 0.0, 0.0, 1);
        assertEquals(other, instance);
    }
}

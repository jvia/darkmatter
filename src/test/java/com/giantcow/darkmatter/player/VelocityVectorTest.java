package com.giantcow.darkmatter.player;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * Testing the VelocityVector class.
 *
 * @author Charlie Horrel <cxh900@cs.bham.ac.uk>
 * @author Yukun Wang <yxw999@cs.bham.ac.uk>
 * @author Jeremiah M. Via <jxv911@cs.bham.ac.uk>
 * @version 2011.0320
 */
public class VelocityVectorTest extends TestCase {

    public VelocityVectorTest(String testName) {
        super(testName);
    }

    public static Test suite() {
        return new TestSuite(VelocityVectorTest.class);
    }

    @Override
    protected void setUp() throws Exception {
        super.setUp();
    }

    @Override
    protected void tearDown() throws Exception {
        super.tearDown();
    }

    /** Test of resultant method, of class VelocityVector. */
    public void testResultant() {
        System.out.println("resultant with +dx and +dy");
        VelocityVector instance = new VelocityVector(3.0, 4.0);
        double expResult = 5.0;
        double result = instance.resultant();
        assertEquals(expResult, result, 0.0);

    }

    public void testResultant2() {
        System.out.println("resultant with -dx and +dy");
        VelocityVector instance = new VelocityVector(-3.0, 4.0);
        double expResult = 5.0;
        double result = instance.resultant();
        assertEquals(expResult, result, 0.0);
    }

    public void testResultant3() {
        System.out.println("resultant with +dx and -dy");
        VelocityVector instance = new VelocityVector(3.0, -4.0);
        double expResult = 5.0;
        double result = instance.resultant();
        assertEquals(expResult, result, 0.0);
    }

    public void testResultant4() {
        System.out.println("resultant with -dx and -dy");
        VelocityVector instance = new VelocityVector(-3.0, -4.0);
        double expResult = 5.0;
        double result = instance.resultant();
        assertEquals(expResult, result, 0.0);
    }

    /** Test of angle method, of class VelocityVector. */
    public void testAngle() {
        System.out.println("angle with +dx and +dy");
        VelocityVector instance = new VelocityVector(2.0, 2.0);
        double expResult = 45.0;
        double result = instance.angle();
        assertEquals(expResult, result, 0);

    }

    public void testAngle2() {
        System.out.println("angle with -dx and +dy");
        VelocityVector instance = new VelocityVector(-2.0, 2.0);
        double expResult = 135.0;
        double result = instance.angle();
        assertEquals(expResult, result, 0);

    }

    public void testAngle3() {
        System.out.println("angle with +dx and -dy");
        VelocityVector instance = new VelocityVector(+2.0, -2.0);
        double expResult = -45.0;
        double result = instance.angle();
        assertEquals(expResult, result, 0);
    }

    public void testAngle4() {
        System.out.println("angle with -dx and -dy");
        VelocityVector instance = new VelocityVector(-2.0, -2.0);
        double expResult = -135.0;
        double result = instance.angle();
        assertEquals(expResult, result, 0);

    }


    /** Test of momentum method, of class VelocityVector. */
    public void testMomentum() {
        System.out.println("momentum with +dx and +dy");
        double mass = 10.0;
        VelocityVector instance = new VelocityVector(3.0, 4.0);
        double expResult = 50.0;
        double result = instance.momentum(mass);
        assertEquals(expResult, result, 0.0);
    }

    public void testMomentum2() {
        System.out.println("momentum with -dx and +dy");
        double mass = 10.0;
        VelocityVector instance = new VelocityVector(-3.0, 4.0);
        double expResult = 50.0;
        double result = instance.momentum(mass);
        assertEquals(expResult, result, 0.0);
    }

    public void testMomentum3() {
        System.out.println("momentum with +dx and -dy");
        double mass = 10.0;
        VelocityVector instance = new VelocityVector(3.0, -4.0);
        double expResult = 50.0;
        double result = instance.momentum(mass);
        assertEquals(expResult, result, 0.0);
    }

    public void testMomentum4() {
        System.out.println("momentum with -dx and -dy");
        double mass = 10.0;
        VelocityVector instance = new VelocityVector(-3.0, -4.0);
        double expResult = 50.0;
        double result = instance.momentum(mass);
        assertEquals(expResult, result, 0.0);
    }
}

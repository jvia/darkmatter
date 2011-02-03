/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.giantcow.darkmatter;

import junit.framework.TestCase;

/**
 *
 * @author Charlie
 */
public class VelocityVectorTest extends TestCase {
    
    public VelocityVectorTest() {
        super("velocity vector test");
    }

    

   
    /**
     * Test of resultant method, of class VelocityVector.
     */
    public void testResultant() {
        System.out.println("resultant");
        VelocityVector instance = new VelocityVector(3.0, 4.0);
        double expResult = 5.0;
        double result = instance.resultant();
        assertEquals(expResult, result, 0.0);
      
    }

    /**
     * Test of angle method, of class VelocityVector.
     */
    public void testAngle() {
        System.out.println("angle");
        VelocityVector instance = new VelocityVector(3.0, 4.0);
        double expResult = 53.0;
        double result = instance.angle();
        assertEquals(expResult, result, 0.25);
       
    }

    /**
     * Test of momentum method, of class VelocityVector.
     */
    public void testMomentum() {
        System.out.println("momentum");
        double mass = 10.0;
        VelocityVector instance = new VelocityVector(3.0, 4.0);
        double expResult = 50.0;
        double result = instance.momentum(mass);
        assertEquals(expResult, result, 0.0);
       
    }

}

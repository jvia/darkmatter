/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sanbox;


import java.awt.geom.Ellipse2D;

/**
 *
 * @author Joss Greenaway <jtg897@cs.bham.ac.uk>
 */
public class CollisionDetection {

    public static void main(String[] args) {

        int KEY_VALUE = 150; //Changing between 150 where there is no collision and 149 where there is shows the accuracy of the collision detection

        Ellipse2D Ellipse1 = new Ellipse2D.Double(100, 100, 50, 50);
        Ellipse2D Ellipse2 = new Ellipse2D.Double(100, KEY_VALUE , 50, 50);

        double x = Ellipse1.getCenterX();
        double y = Ellipse1.getCenterY();
        double x2 = Ellipse2.getCenterX();
        double y2 = Ellipse2.getCenterY();

        double radius1 = Ellipse1.getWidth()/2;
        double radius2 = Ellipse2.getWidth()/2;

        double dx = x-x2;
        double dy = y-y2;

        double distance = (dx*dx)+(dy*dy);

        if (distance < (radius1+radius2)*(radius1+radius2)) {
            for (int i=0; i<6; i++){
                System.out.println("COLLISION!!!!");
            }
        }
    }


}

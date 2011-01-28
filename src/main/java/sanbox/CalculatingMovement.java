/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sanbox;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Line2D;
import java.awt.geom.Point2D;
import javax.swing.JFrame;
import javax.swing.JPanel;

/**
 *
 * @author via
 */
public class CalculatingMovement {

    public static void main(String[] args) {
        JFrame frame = new JFrame();
        CirclePanel panel = new CirclePanel();
        frame.add(panel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 400);
        frame.setVisible(true);
        frame.addMouseListener(panel);
    }
}

class Circle extends Ellipse2D.Double {

    public Circle(int x, int y, int r) {
        super(x, y, r, r);
    }
}

class CirclePanel extends JPanel implements MouseListener {

    private Circle circle;
    private Point2D click = new Point2D.Double();
    private String msg = "HI";

    public CirclePanel() {
        circle = new Circle(185, 185, 30);
    }

    @Override
    public void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.clearRect(0, 0, 400, 400);

        g2.setColor(Color.black);
        g2.fill(circle);

        g2.setColor(Color.red);
        g2.drawString(msg, 10, 290);
        // Draw sexy triangle
        g2.draw(new Line2D.Double(circle.getCenterX(), circle.getCenterY(), click.getX(), click.getY()));
        g2.draw(new Line2D.Double(circle.getCenterX(), circle.getCenterY(), click.getX(), circle.getCenterY()));
        g2.draw(new Line2D.Double(click.getX(), circle.getCenterY(), click.getX(), click.getY()));
    }

    @Override
    public void mouseClicked(MouseEvent e) {
    }

    @Override
    public void mousePressed(MouseEvent e) {
        msg = String.format("Circle: (%.2f, %.2f)    Click: (%d, %d) ",
                            circle.x, circle.y, e.getX(), e.getY());
        click.setLocation(e.getX(), e.getY());
        repaint();
    }

    @Override
    public void mouseReleased(MouseEvent e) {
    }

    @Override
    public void mouseEntered(MouseEvent e) {
    }

    @Override
    public void mouseExited(MouseEvent e) {
    }
}

package sanbox;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.List;

/**
 * @author via
 */
public class CalculatingMovement {
    static JFrame frame;
    static CirclePanel panel;
    public static int WIDTH = 400;
    public static int HEIGHT = 400;

    public CalculatingMovement() {
        frame = new JFrame();
        panel = new CirclePanel();
        panel.setPreferredSize(new Dimension(WIDTH, HEIGHT));
        frame.add(panel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.addMouseListener(panel);
        frame.pack();
        frame.setVisible(true);
    }

    public boolean isOver() {
        return false;
    }

    public static void main(String[] args) throws InterruptedException {
        CalculatingMovement cm = new CalculatingMovement();
        while (!cm.isOver()) {
            updateLogic();
            updateScreen();
            Thread.sleep(10);
        }
    }


    private static void updateScreen() {
        frame.repaint();
    }

    private static void updateLogic() {
        // See if there is a collision
        Circle player = panel.circles.get(0);
        for (Circle npc : panel.circles.subList(1, panel.circles.size())) {
            double x = Math.abs(player.getCenterX() - npc.getCenterX());
            double y = Math.abs(player.getCenterY() - npc.getCenterY());
            double dist = Math.hypot(x, y);

            // check for collision
            if (dist <= player.getHeight() + npc.getHeight()) {
                if (player.getArea() >= npc.getArea())
                    System.out.println("Absorb! @ " + System.nanoTime());
                else
                    System.out.println("Absorbed! @ " + System.nanoTime());

            }
        }


        for (Circle c : panel.circles) {
            c.update();
        }
    }
}


/**
 * Rerpresents a circle object on the screen.
 */
class Circle extends Ellipse2D.Double {
    private double dy;
    private double dx;

    public Circle(final double x, final double y, final double r) {
        super(x, y, r, r);
        dy = 0;
        dx = 0;
    }

    public Circle(final double x, final double y, final double r, final double dy, final double dx) {
        super(x, y, r, r);
        this.dy = dy;
        this.dx = dx;
    }

    public Circle(final double dy, final double dx) {
        this(0.0, 0.0, 0.0, dy, dx);
    }

    public void setRadius(final double r) {
        this.width = r;
        this.height = r;
    }

    public void setDY(final double dy) {
        this.dy = dy;
    }

    public double getDY() {
        return dy;
    }

    public void setDX(final double dx) {
        this.dx = dx;
    }

    public double getDX() {
        return dx;
    }

    public double getArea() {
        return Math.PI * Math.pow(width, 2);
    }

    public void update() {
        if (this.getMaxX() >= CalculatingMovement.WIDTH || x <= 0) {
            dx = -dx;
        }
        if (this.getMaxY() >= CalculatingMovement.HEIGHT || y <= 0) {
            dy = -dy;
        }

        y += dy;
        x += dx;
    }

    public double radiusOfAreaPercentage(double percent) {
        assert (width == height);   // Just for sanity checking

        double area = Math.PI * Math.pow(width, 2);
        area *= percent;
        return Math.sqrt(area / Math.PI);
    }

    public double changeInX(double x2, double y2) {
        double deltaX = x2 - getCenterX();
        double deltaY = y2 - getCenterY();
        double normalizer = deltaX >= deltaY ? Math.abs(deltaX) : Math.abs(deltaY);
        deltaX /= normalizer;
        deltaY /= normalizer;
        return deltaX;
    }

    public double changeInY(double x2, double y2) {
        double deltaX = x2 - getCenterX();
        double deltaY = y2 - getCenterY();
        double normalizer = deltaX >= deltaY ? Math.abs(deltaX) : Math.abs(deltaY);
        deltaX /= normalizer;
        deltaY /= normalizer;
        return deltaY;
    }
}

/**
 *
 */
class CirclePanel extends JPanel implements MouseListener {

    private Circle circle;
    ArrayList<Circle> circles;
    private Point2D click = new Point2D.Double();
    private String msg = "HI";

    public CirclePanel() {
        setBackground(new Color(224, 228, 204));
        circle = new Circle(185, 185, 30);
        circles = new ArrayList<Circle>();
        circles.add(circle);
        this.setSize(CalculatingMovement.WIDTH, CalculatingMovement.HEIGHT);
    }

    private void drawPlayer(Graphics2D g2, Circle player) {
        // Draw player's circle
        g2.setColor(new Color(243, 134, 48));
        g2.fill(player);
        g2.setColor(Color.red);

        // Draw boundng box
        g2.setColor(Color.red);
        g2.draw(player.getBounds2D());
    }

    private void drawNPC(Graphics2D g2, List<Circle> circles) {
        // Draw all other circles on the screen
        for (Circle c : circles) {
            g2.setColor(new Color(167, 219, 216));
            g2.fill(c);
            g2.setColor(Color.red);
            g2.draw(c.getBounds2D());
        }

    }

    @Override
    public void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.clearRect(0, 0, WIDTH, HEIGHT);

        drawPlayer(g2, circles.get(0));
        drawNPC(g2, circles.subList(1, circles.size()));


        g2.setColor(Color.red);
        g2.drawString(msg, 10, 290);


    }

    @Override
    public void mousePressed(MouseEvent e) {
        msg = String.format("Circle: (%.2f, %.2f)    Click: (%d, %d) ",
                circle.x, circle.y, e.getX(), e.getY());
        click.setLocation(e.getX(), e.getY());


        // Create a new circle from the player's circle
        double r = circle.radiusOfAreaPercentage(0.10);
        double dx = circle.changeInX(e.getX(), e.getY());
        double dy = circle.changeInY(e.getX(), e.getY());
        System.out.printf("DX: %5.2f  DY: %5.2f   DY/DX: %5.2f\n", dy, dx, dy / dx);
        Circle matter = new Circle(circle.getX(), circle.getY(), r, dy, dx);
        circles.add(matter);
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        // Not implemented
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        // Not implemented
    }

    @Override
    public void mouseEntered(MouseEvent e) {
        // Not implemented
    }

    @Override
    public void mouseExited(MouseEvent e) {
        // Not implemented
    }
}

package sanbox;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.List;

/**
 * Bugs:
 * - On one occasion circle coordinates became NaN and circle disappeared
 * - Collision detection is painfully bad
 * - Change in momentum calculations are *very* wrong
 * - Circles are generated too far from player's circle
 * - Many crashes due to concurrent modifications of the list structure
 *
 * @author via
 */
public class CalculatingMovement {
    static JFrame frame;
    static CirclePanel panel;
    public static int WIDTH = 400;
    public static int HEIGHT = 400;

    public CalculatingMovement() {         
 		
        GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
        frame = new JFrame();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);
        frame.setBackground(new Color(26, 23, 19));

        // Add key listener
        frame.addKeyListener(new KeyListener() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
                    frame.dispose();
                    System.exit(0);
                }
            }

            @Override
            public void keyTyped(KeyEvent e) {
                // not used
            }

            @Override
            public void keyReleased(KeyEvent e) {
                //not used
            }
        });

        panel = new CirclePanel();
        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        panel.setPreferredSize(dim);
        frame.setUndecorated(true);
        //panel.setPreferredSize(new Dimension(WIDTH, HEIGHT));
        frame.add(panel);
        frame.addMouseListener(panel);
        
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
 		frame.setUndecorated(true);
 		frame.setResizable(false);
 		frame.validate();

 		GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice().setFullScreenWindow(frame);
//
//        frame.pack();
//        frame.setVisible(true);
        
        
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
        double percent = 0.7;
        Circle player = panel.circles.get(0);
        ArrayList<Circle> nextCircles = new ArrayList<Circle>(panel.circles.size());
        nextCircles.add(player);

        for (Circle npc : panel.circles.subList(1, panel.circles.size())) {
            double x = Math.abs(player.getCenterX() - npc.getCenterX());
            double y = Math.abs(player.getCenterY() - npc.getCenterY());
            double dist = Math.hypot(x, y);

            // check for collisions
            if (dist <= player.getHeight() + npc.getHeight()) {
                if (player.getArea() >= npc.getArea()) {
                    System.out.println("Absorb! @ " + System.nanoTime());
                    player.setArea(player.getArea() + (npc.getArea() * percent));
                    player.setDX((player.getDX() + npc.getDX()) / 2);
                    player.setDY((player.getDY() + npc.getDY()) / 2);
                    npc.setArea(npc.getArea() - (npc.getArea() * percent));
                } else {
                    System.out.println("Absorbed! @ " + System.nanoTime());

                }

            }

            if (npc.getArea() > 1.0)
                nextCircles.add(npc);

        }


        panel.circles = nextCircles;
        for (Circle c : panel.circles) {
            c.update();
        }
    }
}


/**
 * Represents a circle object on the screen.
 */
class Circle extends Ellipse2D.Double {
    private double dy;
    private double dx;
    private double radius;

    public Circle(final double x, final double y, final double r) {
        super(x, y, r, r);
        radius = r;
        dy = 0;
        dx = 0;
    }

    public Circle(final double x, final double y, final double r, final double dy, final double dx) {
        super(x, y, r, r);
        this.dy = dy;
        this.dx = dx;
    }

    public void setRadius(final double r) {
        this.width = r;
        this.height = r;
        radius = r;
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
        if (this.getMaxX() >= CalculatingMovement.panel.getWidth() || x <= 0) {
            dx = -dx;
        }
        if (this.getMaxY() >= CalculatingMovement.panel.getHeight() || y <= 0) {
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

    public double getRadius() {
        return radius;
    }

    public void setArea(double area) {
        double radius = Math.sqrt(area / Math.PI);
        setRadius(radius);
    }
}

/**
 *
 */
class CirclePanel extends JPanel implements MouseListener {

    private Circle circle;
    ArrayList<Circle> circles;
    private Point2D click = new Point2D.Double();

    public CirclePanel() {
        setBackground(new Color(20, 47, 48));
        circle = new Circle(400, 600, 100);
        circles = new ArrayList<Circle>();
        circles.add(circle);
        this.setSize(CalculatingMovement.WIDTH, CalculatingMovement.HEIGHT);
    }

    private void drawPlayer(Graphics2D g2, Circle player) {
        // Draw player's circle
        g2.setColor(new Color(243, 134, 48));
        g2.fill(player);
    }

    private void drawNPC(Graphics2D g2, List<Circle> circles) {
        // Draw all other circles on the screen
        for (Circle c : circles) {
            g2.setColor(new Color(167, 219, 216));
            g2.fill(c);
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
        g2.drawString("Hit [ESC] to Quit", 15, 15);
    }

    @Override
    public void mousePressed(MouseEvent e) {
        click.setLocation(e.getX(), e.getY());
        // Create a new circle from the player's circle
        // and shrink player's circle
        Circle matter = createCircle(e.getX(), e.getY(), circle);
        circle.setRadius(circle.radiusOfAreaPercentage(0.90));
        circle.setDY(circle.getDY() - matter.getDY());
        circle.setDX(circle.getDX() - matter.getDX());
        circles.add(matter);
    }

    private Circle createCircle(int clickX, int clickY, Circle circle) {
        double r = circle.radiusOfAreaPercentage(0.10);
        double dx = circle.changeInX(clickX, clickY);
        double dy = circle.changeInY(clickX, clickY);
        // Calculate the X & Y coordinates of the circle
        double centerX = circle.getCenterX();
        double centerY = circle.getCenterY();
        double x1 = circle.getCenterX();
        double y1 = circle.getCenterY();

        if (clickX < centerX)
            x1 -= circle.getRadius() + r;
        if (clickX > centerX)
            x1 += circle.getRadius();
        if (clickY < centerY)
            y1 -= circle.getRadius() + r;
        if (clickY > centerY)
            y1 += circle.getRadius();
        return new Circle(x1, y1, r, dy, dx);
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

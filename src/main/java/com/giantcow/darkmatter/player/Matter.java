package com.giantcow.darkmatter.player;

import java.awt.*;
import java.awt.geom.*;
import java.io.Serializable;
import java.util.Collection;

/**
 * Perfect circle representing a Matter object in space. This is the basic NPC object in the game.
 * It never moves on its own. It has to be interacted with my an intelligent agent to impart change
 * on this object.
 *
 * @author Joss Greenaway <jtg897@cs.bham.ac.uk>
 * @author Jeremiah Via <jxv911@cs.bham.ac.uk>
 * @version 2011.0201
 * @since 0.1
 */
public class Matter implements Shape, Comparable, Serializable {

    // Serialization variable
    /** Unique identifier. */
    private static final long serialVersionUID = 7526472295622776147L;

    // Object identification
    /** Variable which is used in the implicit ID creation. */
    private static int ID_MAKER = 0;
    /** The object's unique ID */
    private int id;

    // Constants
    /** The most an object's position can change in any direction. */
    protected static final double MAX_SPEED = 2.0;
    /** The amount of extra distance to consider when checking if objects collide. */
    private static final double FUDGE_FACTOR = 0.2;

    // Shape and speed
    /** The internal shape of the object. */
    private Ellipse2D.Double blob;
    /** The speed of the object. */
    private VelocityVector velocity;

    /**
     * Constructs a Matter object
     *
     * @param blob the Ellipse2D object
     * @param dy   change in y
     * @param dx   change in x
     * @param id   the object's id
     */
    public Matter(Ellipse2D.Double blob, double dy, double dx, int id) {
        this.blob = blob;
        velocity = new VelocityVector(dy, dx);
        this.id = id;
    }

    /**
     * Constructs a Matter object
     *
     * @param x      Top left corner of the bounding rectangle
     * @param y      Top left corner of the bounding rectangle
     * @param radius the radius
     * @param dy     change in y
     * @param dx     change in x
     * @param id     the object's id
     */
    public Matter(double x, double y, double radius, double dy, double dx, int id) {
        this(new Ellipse2D.Double(x, y, 2 * radius, 2 * radius), dy, dx, id);
    }

    /**
     * Constructs a Matter object
     *
     * @param x      Top left corner of the bounding rectangle
     * @param y      Top left corner of the bounding rectangle
     * @param radius the radius
     * @param dy     change in y
     * @param dx     change in x
     */
    public Matter(double x, double y, double radius, double dy, double dx) {
        this(new Ellipse2D.Double(x, y, 2 * radius, 2 * radius), dy, dx, ID_MAKER++);
    }

    /**
     * Returns change in x-axis.
     *
     * @return change in x-axis
     */
    public double getDx() {
        return velocity.getDx();
    }

    /**
     * Sets change in x-axis.
     *
     * @param dx change in x-axis
     */
    public void setDx(double dx) {
        velocity.setDx(dx);
    }

    /**
     * Returns change in y-axis.
     *
     * @return change in y-axis
     */
    public double getDy() {
        return velocity.getDy();
    }

    /**
     * Sets change in y-axis
     *
     * @param dy change in y-axis
     */
    public void setDy(double dy) {
        velocity.setDy(dy);
    }

    /**
     * Gets the radius of the matter object.
     *
     * @return the matter's radius
     */
    public double getRadius() {
        return blob.width / 2;
    }

    /**
     * Sets the radius of the matter object.
     *
     * @param r the new radius
     */
    public void setRadius(double r) {
        blob.width = r * 2;
        blob.height = blob.width;
    }

    /**
     * Gets the object's unique id.
     *
     * @return object id
     */
    public int getId() {
        return id;
    }

    /**
     * Returns the area of the object.
     *
     * @return the current area
     */
    public double getArea() {
        return Math.PI * Math.pow(getRadius(), 2);
    }

    /**
     * Sets the area of the object.
     *
     * @param area the new area
     */
    public void setArea(double area) {
        double radius = Math.sqrt(area / Math.PI);
        setRadius(radius);
    }

    /**
     * Checks whether Matter object intersects 'other'.
     *
     * @param other other Matter object
     * @return 'True' if objects intersects, 'False' if otherwise
     */
    public boolean intersects(Matter other) {
        return blob.intersects(other.getBounds2D())
               && (distance(other)
                   <= (getRadius() + other.getRadius()));
    }

    /**
     * Return the distance between two matters center.
     *
     * @param other other Matter object
     * @return the distance between two matters center
     */
    public double distance(Matter other) {
        return Math.hypot(blob.getCenterX() - other.getCenterX(),
                          blob.getCenterY() - other.getCenterY());
    }

    /**
     * Checks whether this Matter object intersects with a Collection of other Matter objects.
     *
     * @param others the Collection  of Matter objects
     * @return true if a collision, false otherwise
     */
    public boolean intersects(Collection<Matter> others) {
        for (Matter other : others) {
            if (this.intersects(other)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Checks if a Matter object is completely inside this Matter object.
     *
     * @param other other Matter object.
     * @return true iff other object is within this one
     */
    public boolean completelyConsumes(Matter other) {
        if (this.isBigger(other)) {
            return distance(other) <= getRadius() - other.getRadius()+4;
        } else {
            return false;
        }


    }

    /**
     * Returns the center point on the x-axis of the object.
     *
     * @return center x-coordinate
     */
    public double getCenterX() {
        return blob.getCenterX();
    }

    /**
     * Returns the center point on the y-axis of the object.
     *
     * @return center y-coordinate
     */
    public double getCenterY() {
        return blob.getCenterY();
    }

    /**
     * Returns true if this object is bigger than the other.
     *
     * @param other other Matter object
     * @return true iff this object is bigger
     */
    public boolean isBigger(Matter other) {
        return getRadius() > other.getRadius();
    }

    /**
     * Returns the velocity of this object.
     *
     * @return velocity
     */
    public double getVelocity() {
        return velocity.resultant();
    }

    /** Updates the position of the Ellipse2D object 'blob' */
    public void update() {
        blob.x = blob.getX() + velocity.getDx();
        blob.y = blob.getY() + velocity.getDy();
    }

    /**
     * Alters the matter object and creates a new Matter object  that is expelled out.
     *
     * @param x          x-coordinate of click
     * @param y          y-coordinate of click
     * @param matterList the list of all other objects
     * @return the expelled matter object
     */
    public Matter changeMove(double x, double y, Collection<Matter> matterList) {
        return null;
    }

    /**
     * Sets the framing rectangle of this Shape based on the specified center point coordinates and
     * corner point coordinates. The framing rectangle is used by the subclasses of RectangularShape
     * to define their geometry.
     *
     * @param centerX the X coordinate of the specified center point
     * @param centerY the Y coordinate of the specified center point
     * @param cornerX the X coordinate of the specified corner point
     * @param cornerY the Y coordinate of the specified corner point
     */
    public void setFrameFromCenter(double centerX, double centerY, double cornerX, double cornerY) {
        blob.setFrameFromCenter(centerX, centerY, cornerX, cornerY);
    }

    /**
     * Creates Point2D object holding x and y coordinates which represent the centre position of a
     * new expelled Matter object.
     *
     * @param x      x-coordinate of mouse click
     * @param y      y-coordinate of mouse click
     * @param radius radius of expelled matter object
     * @return Point2D object holding x and y coordinates which represent the centre position of the
     *         expelled Matter object
     */
    protected Point2D expulsionCentres(double x, double y, double radius) {
        Point2D.Double expulsionCentre = new Point2D.Double(0.0, 0.0);
        double theta = Math.atan(Math.abs(y - getCenterY()) / Math.abs(x - getCenterX()));

        double hyp = radius + getRadius() + FUDGE_FACTOR;
        double y1 = Math.sin(theta) * hyp;
        double x1 = Math.cos(theta) * hyp;

        if (x <= getCenterX() & y <= getCenterY()) {
            expulsionCentre.setLocation(getCenterX() - x1, getCenterY() - y1);
        } else if (x > getCenterX() & y <= getCenterY()) {
            expulsionCentre.setLocation(getCenterX() + x1, getCenterY() - y1);
        } else if (x <= getCenterX() & y > getCenterY()) {
            expulsionCentre.setLocation(getCenterX() - x1, getCenterY() + y1);
        } else if (x > getCenterX() & y > getCenterY()) {
            expulsionCentre.setLocation(getCenterX() + x1, getCenterY() + y1);
        }
        return expulsionCentre;
    }


    /**
     * Returns an integer Rectangle that completely encloses the Shape. Note that there is no
     * guarantee that the returned Rectangle is the smallest bounding box that encloses the Shape,
     * only that the Shape lies entirely within the indicated Rectangle. The returned Rectangle
     * might also fail to completely enclose the Shape if the Shape overflows the limited range of
     * the integer data type. The getBounds2D method generally returns a tighter bounding box due to
     * its greater flexibility in representation.
     *
     * @return an integer Rectangle that completely encloses the Shape
     */
    public Rectangle getBounds() {
        return blob.getBounds();
    }

    /**
     * Returns a high precision and more accurate bounding box of the Shape than the getBounds
     * method. Note that there is no guarantee that the returned Rectangle2D is the smallest
     * bounding box that encloses the Shape, only that the Shape lies entirely within the indicated
     * Rectangle2D. The bounding box returned by this method is usually tighter than that returned
     * by the getBounds method and never fails due to overflow problems since the return value can
     * be an instance of the Rectangle2D that uses double precision values to store the dimensions.
     *
     * @return an instance of Rectangle2D that is a high-precision bounding box of the Shape.
     */
    public Rectangle2D getBounds2D() {
        return blob.getBounds2D();
    }

    /**
     * Tests if the specified coordinates are inside the boundary of the Shape.
     *
     * @param x the specified X coordinate to be tested
     * @param y the specified Y coordinate to be tested
     * @return true if the specified coordinates are inside the Shape boundary; false otherwise.
     */
    public boolean contains(double x, double y) {
        return blob.contains(x, y);
    }

    /**
     * Tests if a specified Point2D is inside the boundary of the Shape.
     *
     * @param p the specified Point2D to be tested
     * @return true if the specified Point2D is inside the boundary of the Shape; false otherwise.
     */
    public boolean contains(Point2D p) {
        return blob.contains(p);
    }

    /**
     * Tests if the interior of the Shape intersects the interior of a specified rectangular area.
     * The rectangular area is considered to intersect the Shape if any point is contained in both
     * the interior of the Shape and the specified rectangular area. The Shape.intersects() method
     * allows a Shape implementation to conservatively return true when:
     * <p/>
     * <ul> <li>there is a high probability that the rectangular area and the Shape intersect,
     * but</li> <li>the calculations to accurately determine this intersection are prohibitively
     * expensive.</li> </ul>
     * <p/>
     * This means that for some Shapes this method might return true even though the rectangular
     * area does not intersect the Shape. The Area class performs more accurate computations of
     * geometric intersection than most Shape objects and therefore can be used if a more precise
     * answer is required.
     *
     * @param x the X coordinate of the upper-left corner of the specified rectangular area
     * @param y the Y coordinate of the upper-left corner of the specified rectangular area
     * @param w the width of the specified rectangular area
     * @param h the height of the specified rectangular area
     * @return true if the interior of the Shape and the interior of the rectangular area intersect,
     *         or are both highly likely to intersect and intersection calculations would be too
     *         expensive to perform; false otherwise.
     */
    public boolean intersects(double x, double y, double w, double h) {
        return blob.intersects(x, y, w, h);
    }

    /**
     * Tests if the interior of the Shape intersects the interior of a specified rectangular area.
     * The rectangular area is considered to intersect the Shape if any point is contained in both
     * the interior of the Shape and the specified rectangular area. The Shape.intersects() method
     * allows a Shape implementation to conservatively return true when:
     * <p/>
     * <ul> <li>there is a high probability that the rectangular area and the Shape intersect,
     * but</li> <li>the calculations to accurately determine this intersection are prohibitively
     * expensive.</li> </ul>
     * <p/>
     * This means that for some Shapes this method might return true even though the rectangular
     * area does not intersect the Shape. The Area class performs more accurate computations of
     * geometric intersection than most Shape objects and therefore can be used if a more precise
     * answer is required.
     *
     * @param r other rectangle
     * @return true if the interior of the Shape and the interior of the rectangular area intersect,
     *         or are both highly likely to intersect and intersection calculations would be too
     *         expensive to perform; false otherwise.
     */
    public boolean intersects(Rectangle2D r) {
        return blob.intersects(r);
    }

    /**
     * Tests if the interior of the Shape entirely contains the specified rectangular area. All
     * coordinates that lie inside the rectangular area must lie within the Shape for the entire
     * rectanglar area to be considered contained within the Shape. The Shape.contains() method
     * allows a Shape implementation to conservatively return false when:
     * <p/>
     * <ul><li>the intersect method returns true and</li> <li>the calculations to determine whether
     * or not the Shape entirely contains the rectangular area are prohibitively expensive.</li>
     * </ul>
     * <p/>
     * This means that for some Shapes this method might return false even though the Shape contains
     * the rectangular area. The Area class performs more accurate geometric computations than most
     * Shape objects and therefore can be used if a more precise answer is required.
     *
     * @param x the X coordinate of the upper-left corner of the specified rectangular area
     * @param y the Y coordinate of the upper-left corner of the specified rectangular area
     * @param w the width of the specified rectangular area
     * @param h the height of the specified rectangular area
     * @return true if the interior of the Shape entirely contains the specified rectangular area;
     *         false otherwise or, if the Shape contains the rectangular area and the intersects
     *         method returns true and the containment calculations would be too expensive to
     *         perform.
     */
    public boolean contains(double x, double y, double w, double h) {
        return blob.contains(x, y, w, h);
    }

    /**
     * Tests if the interior of the Shape entirely contains the specified rectangular area. All
     * coordinates that lie inside the rectangular area must lie within the Shape for the entire
     * rectanglar area to be considered contained within the Shape. The Shape.contains() method
     * allows a Shape implementation to conservatively return false when:
     * <p/>
     * <ul><li>the intersect method returns true and</li> <li>the calculations to determine whether
     * or not the Shape entirely contains the rectangular area are prohibitively expensive.</li>
     * </ul>
     * <p/>
     * This means that for some Shapes this method might return false even though the Shape contains
     * the rectangular area. The Area class performs more accurate geometric computations than most
     * Shape objects and therefore can be used if a more precise answer is required.
     *
     * @param r other rectangle
     * @return true if the interior of the Shape entirely contains the specified rectangular area;
     *         false otherwise or, if the Shape contains the rectangular area and the intersects
     *         method returns true and the containment calculations would be too expensive to
     *         perform.
     */
    public boolean contains(Rectangle2D r) {
        return blob.contains(r);
    }

    /**
     * Returns an iteration object that defines the boundary of this Ellipse2D. The iterator for
     * this class is multi-threaded safe, which means that this Ellipse2D class guarantees that
     * modifications to the geometry of this Ellipse2D object do not affect any iterations of that
     * geometry that are already in process.
     *
     * @param at n optional AffineTransform to be applied to the coordinates as they are returned in
     *           the iteration, or null if untransformed coordinates are desired
     * @return the PathIterator object that returns the geometry of the outline of this Ellipse2D,
     *         one segment at a time.
     */
    public PathIterator getPathIterator(AffineTransform at) {
        return blob.getPathIterator(at);
    }

    /**
     * Returns an iterator object that iterates along the Shape boundary and provides access to a
     * flattened view of the Shape outline geometry. Only SEG_MOVETO, SEG_LINETO, and SEG_CLOSE
     * point types are returned by the iterator. If an optional AffineTransform is specified, the
     * coordinates returned in the iteration are transformed accordingly. The amount of subdivision
     * of the curved segments is controlled by the flatness parameter, which specifies the maximum
     * distance that any point on the unflattened transformed curve can deviate from the returned
     * flattened path segments. Note that a limit on the accuracy of the flattened path might be
     * silently imposed, causing very small flattening parameters to be treated as larger values.
     * This limit, if there is one, is defined by the particular implementation that is used. Each
     * call to this method returns a fresh PathIterator object that traverses the Shape object
     * geometry independently from any other PathIterator objects in use at the same time. It is
     * recommended, but not guaranteed, that objects implementing the Shape interface isolate
     * iterations that are in process from any changes that might occur to the original object's
     * geometry during such iterations.
     *
     * @param at       an optional AffineTransform to be applied to the coordinates as they are
     *                 returned in the iteration, or null if untransformed coordinates are desired.
     * @param flatness the maximum distance that the line segments used to approximate the curved
     *                 segments are allowed to deviate from any point on the original curve
     * @return a PathIterator object that provides access to the Shape object's flattened geometry.
     */
    public PathIterator getPathIterator(AffineTransform at, double flatness) {
        return blob.getPathIterator(at, flatness);
    }

    /**
     * Returns the X coordinate of the upper-left corner of the framing rectangle in double
     * precision.
     *
     * @return the X coordinate of the upper-left corner of the framing rectangle.
     */
    public double getX() {
        return blob.getX();
    }

    /**
     * Returns the Y coordinate of the upper-left corner of the framing rectangle in double
     * precision.
     *
     * @return the Y coordinate of the upper-left corner of the framing rectangle.
     */
    public double getY() {
        return blob.getY();
    }

    /**
     * Returns the largest X coordinate of the framing rectangle of the Shape in double precision.
     *
     * @return the largest X coordinate of the framing rectangle of the Shape
     */
    public double getMaxX() {
        return blob.getMaxX();
    }

    /**
     * Returns the largest Y coordinate of the framing rectangle of the Shape in double precision.
     *
     * @return the largest Y coordinate of the framing rectangle of the Shape
     */
    public double getMaxY() {
        return blob.getMaxY();
    }

    /**
     * Returns the smallest X coordinate of the framing rectangle of the Shape in double precision.
     *
     * @return the smallest X coordinate of the framing rectangle of the Shape.
     */
    public double getMinX() {
        return blob.getMinX();
    }

    /**
     * Returns the smallest Y coordinate of the framing rectangle of the Shape in double precision.
     *
     * @return the smallest Y coordinate of the framing rectangle of the Shape.
     */
    public double getMinY() {
        return blob.getMinY();
    }

    /**
     * Compares this object to another.
     *
     * @param o the other object
     * @return negative if less than; 0 if equal; &gt positive if greater than
     */
    @Override
    public int compareTo(Object o) {
        try {
            Matter m = (Matter) o;
            return (int) (this.getRadius() - m.getRadius());
        } catch (ClassCastException ex) {
            throw new ClassCastException("Object not of type Matter");
        }
    }

    /**
     * Checks to see if two objects are equal. This happens when they have the same ID. This is the
     * case because the object will constantly be changing on the Server so there needs to be some
     * unchanging component to compare to.
     *
     * @param obj other object
     * @return true iff they have the same ID
     */
    @Override
    public boolean equals(Object obj) {
        try {
            if (obj == null || !(obj instanceof Matter)) return false;

            Matter m = (Matter) obj;
            return id == m.id;
        } catch (ClassCastException ex) {
            throw new ClassCastException("Object not of type Matter");
        }
    }

    /**
     * Returns a String representation of the object. It details the (X,Y) coordinate of the
     * top-left corner of the bounding rectangle, the radius of the circle and the change in y and x
     * of the object.
     *
     * @return string representation
     */
    @Override
    public String toString() {
        return String.format("X: %6.2f, Y: %6.2f, R: %6.2f, DY: %6.2f, DX: %6.2f, ID: %4d",
                             getX(), getY(), getRadius(), getDy(), getDx(), getId());
    }

    /** Resets the ID_MAKER count back to 0. */
    public static void resetCount() {
        ID_MAKER = 0;
    }
}

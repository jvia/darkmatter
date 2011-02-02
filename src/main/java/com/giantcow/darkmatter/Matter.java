package com.giantcow.darkmatter;

import java.awt.*;
import java.awt.geom.*;

/**
 * Perfect circle representing a Matter object in space.
 *
 * @author Joss Greenaway <jtg897@cs.bham.ac.uk>
 * @author Jeremiah Via <jxv911@cs.bham.ac.uk>
 * @version 2011.0201
 * @since 0.1
 */
public class Matter implements Shape {

    private Ellipse2D.Double blob;
    private double dy;
    private double dx;

    /**
     * Constructs a Matter object
     * @param blob the Ellipse2D object
     * @param dy change in y
     * @param dx change in x
     */
    public Matter(Ellipse2D.Double blob, double dy, double dx) {
        this.blob = blob;
        this.dy = dy;
        this.dx = dx;
    }

    /**
     * Constructs a Matter object
     * @param blob the Ellipse2D object
     */
    public Matter(Ellipse2D.Double blob) {
        this(blob, 0.0, 0.0);
    }

    /**
     * Constructs a Matter object
     * @param x Top left corner of the bounding rectangle
     * @param y Top left corner of the bounding rectangle
     * @param radius the radius
     * @param dy change in y
     * @param dx change in x
     */
    public Matter(double x, double y, double radius, double dy, double dx) {
        this(new Ellipse2D.Double(x, y, radius, radius), dy, dx);
    }

    /**
     * Returns Ellipse2D object
     * @return
     */
    public Ellipse2D getBlob() {
        return blob;
    }

    /**
     * sets Ellipse2D object
     * @param blob the Ellipse2D object
     */
    public void setBlob(Ellipse2D.Double blob) {
        this.blob = blob;
    }

    /**
     * Returns change in x
     * @return change in x
     */
    public double getDx() {
        return dx;
    }

    /**
     * Sets change in x
     * @param dx change in x
     */
    public void setDx(double dx) {
        this.dx = dx;
    }

    /**
     * returns change in y
     * @return change in y
     */
    public double getDy() {
        return dy;
    }

    /**
     * sets change in y
     * @param dy change in y
     */
    public void setDy(double dy) {
        this.dy = dy;
    }

    public double getRadius() {
        return blob.width / 2;
    }

    public void setRadius(double r) {
        blob.width = r * 2;
        blob.height = blob.width;
    }

    public double getDiameter() {
        return blob.width;
    }

    public void setDiamater(double d) {
        blob.width = d;
        blob.height = blob.width;
    }

    /**
     * Checks whether Matter object intersects 'other'
     * @param other other Matter object
     * @return 'True' if objects intersects, 'False' if otherwise
     */
    public boolean intersects(Matter other) {
        return blob.intersects(other.getBlob().getX(), other.getBlob().getY(),
                other.getBlob().getWidth(), other.getBlob().getHeight());
    }

    /**
     * Modifies Matter object based on the other Matter object it has intersected with
     * @param other other Matter object
     */
    public void collision(Matter other) {
        throw new UnsupportedOperationException("Not done");
    }

    /**
     * Updates the position of the Ellipse2D object 'blob'
     */
    public void update(){
        blob.x = (dx + blob.getX());
        blob.y = (dy + blob.getY());
    }

    /**
     * Returns an integer {@link java.awt.Rectangle} that completely encloses the
     * <code>Shape</code>.  Note that there is no guarantee that the
     * returned <code>Rectangle</code> is the smallest bounding box that
     * encloses the <code>Shape</code>, only that the <code>Shape</code>
     * lies entirely within the indicated  <code>Rectangle</code>.  The
     * returned <code>Rectangle</code> might also fail to completely
     * enclose the <code>Shape</code> if the <code>Shape</code> overflows
     * the limited range of the integer data type.  The
     * <code>getBounds2D</code> method generally returns a
     * tighter bounding box due to its greater flexibility in
     * representation.
     *
     * @return an integer <code>Rectangle</code> that completely encloses
     *         the <code>Shape</code>.
     * @see #getBounds2D
     * @since 1.2
     */
    @Override
    public Rectangle getBounds() {
        return blob.getBounds();    }

    /**
     * Returns a high precision and more accurate bounding box of
     * the <code>Shape</code> than the <code>getBounds</code> method.
     * Note that there is no guarantee that the returned
     * {@link java.awt.geom.Rectangle2D} is the smallest bounding box that encloses
     * the <code>Shape</code>, only that the <code>Shape</code> lies
     * entirely within the indicated <code>Rectangle2D</code>.  The
     * bounding box returned by this method is usually tighter than that
     * returned by the <code>getBounds</code> method and never fails due
     * to overflow problems since the return value can be an instance of
     * the <code>Rectangle2D</code> that uses double precision values to
     * store the dimensions.
     *
     * @return an instance of <code>Rectangle2D</code> that is a
     *         high-precision bounding box of the <code>Shape</code>.
     * @see #getBounds
     * @since 1.2
     */
    @Override
    public Rectangle2D getBounds2D() {
        return blob.getBounds2D();
    }

    /**
     * Tests if the specified coordinates are inside the boundary of the
     * <code>Shape</code>.
     *
     * @param x the specified X coordinate to be tested
     * @param y the specified Y coordinate to be tested
     * @return <code>true</code> if the specified coordinates are inside
     *         the <code>Shape</code> boundary; <code>false</code>
     *         otherwise.
     * @since 1.2
     */
    @Override
    public boolean contains(double x, double y) {
        return blob.contains(x, y);
    }

    /**
     * Tests if a specified {@link java.awt.geom.Point2D} is inside the boundary
     * of the <code>Shape</code>.
     *
     * @param p the specified <code>Point2D</code> to be tested
     * @return <code>true</code> if the specified <code>Point2D</code> is
     *         inside the boundary of the <code>Shape</code>;
     *         <code>false</code> otherwise.
     * @since 1.2
     */
    @Override
    public boolean contains(Point2D p) {
        return blob.contains(p);
    }

    /**
     * Tests if the interior of the <code>Shape</code> intersects the
     * interior of a specified rectangular area.
     * The rectangular area is considered to intersect the <code>Shape</code>
     * if any point is contained in both the interior of the
     * <code>Shape</code> and the specified rectangular area.
     * <p/>
     * The {@code Shape.intersects()} method allows a {@code Shape}
     * implementation to conservatively return {@code true} when:
     * <ul>
     * <li>
     * there is a high probability that the rectangular area and the
     * <code>Shape</code> intersect, but
     * <li>
     * the calculations to accurately determine this intersection
     * are prohibitively expensive.
     * </ul>
     * This means that for some {@code Shapes} this method might
     * return {@code true} even though the rectangular area does not
     * intersect the {@code Shape}.
     * The {@link java.awt.geom.Area Area} class performs
     * more accurate computations of geometric intersection than most
     * {@code Shape} objects and therefore can be used if a more precise
     * answer is required.
     *
     * @param x the X coordinate of the upper-left corner
     *          of the specified rectangular area
     * @param y the Y coordinate of the upper-left corner
     *          of the specified rectangular area
     * @param w the width of the specified rectangular area
     * @param h the height of the specified rectangular area
     * @return <code>true</code> if the interior of the <code>Shape</code> and
     *         the interior of the rectangular area intersect, or are
     *         both highly likely to intersect and intersection calculations
     *         would be too expensive to perform; <code>false</code> otherwise.
     * @see java.awt.geom.Area
     * @since 1.2
     */
    @Override
    public boolean intersects(double x, double y, double w, double h) {
        return blob.intersects(x, y, w, h);
    }

    /**
     * Tests if the interior of the <code>Shape</code> intersects the
     * interior of a specified <code>Rectangle2D</code>.
     * The {@code Shape.intersects()} method allows a {@code Shape}
     * implementation to conservatively return {@code true} when:
     * <ul>
     * <li>
     * there is a high probability that the <code>Rectangle2D</code> and the
     * <code>Shape</code> intersect, but
     * <li>
     * the calculations to accurately determine this intersection
     * are prohibitively expensive.
     * </ul>
     * This means that for some {@code Shapes} this method might
     * return {@code true} even though the {@code Rectangle2D} does not
     * intersect the {@code Shape}.
     * The {@link java.awt.geom.Area Area} class performs
     * more accurate computations of geometric intersection than most
     * {@code Shape} objects and therefore can be used if a more precise
     * answer is required.
     *
     * @param r the specified <code>Rectangle2D</code>
     * @return <code>true</code> if the interior of the <code>Shape</code> and
     *         the interior of the specified <code>Rectangle2D</code>
     *         intersect, or are both highly likely to intersect and intersection
     *         calculations would be too expensive to perform; <code>false</code>
     *         otherwise.
     * @see #intersects(double, double, double, double)
     * @since 1.2
     */
    @Override
    public boolean intersects(Rectangle2D r) {
        return blob.intersects(r);
    }

    /**
     * Tests if the interior of the <code>Shape</code> entirely contains
     * the specified rectangular area.  All coordinates that lie inside
     * the rectangular area must lie within the <code>Shape</code> for the
     * entire rectanglar area to be considered contained within the
     * <code>Shape</code>.
     * <p/>
     * The {@code Shape.contains()} method allows a {@code Shape}
     * implementation to conservatively return {@code false} when:
     * <ul>
     * <li>
     * the <code>intersect</code> method returns <code>true</code> and
     * <li>
     * the calculations to determine whether or not the
     * <code>Shape</code> entirely contains the rectangular area are
     * prohibitively expensive.
     * </ul>
     * This means that for some {@code Shapes} this method might
     * return {@code false} even though the {@code Shape} contains
     * the rectangular area.
     * The {@link java.awt.geom.Area Area} class performs
     * more accurate geometric computations than most
     * {@code Shape} objects and therefore can be used if a more precise
     * answer is required.
     *
     * @param x the X coordinate of the upper-left corner
     *          of the specified rectangular area
     * @param y the Y coordinate of the upper-left corner
     *          of the specified rectangular area
     * @param w the width of the specified rectangular area
     * @param h the height of the specified rectangular area
     * @return <code>true</code> if the interior of the <code>Shape</code>
     *         entirely contains the specified rectangular area;
     *         <code>false</code> otherwise or, if the <code>Shape</code>
     *         contains the rectangular area and the
     *         <code>intersects</code> method returns <code>true</code>
     *         and the containment calculations would be too expensive to
     *         perform.
     * @see java.awt.geom.Area
     * @see #intersects
     * @since 1.2
     */
    @Override
    public boolean contains(double x, double y, double w, double h) {
        return blob.contains(x, y, w, h);
    }

    /**
     * Tests if the interior of the <code>Shape</code> entirely contains the
     * specified <code>Rectangle2D</code>.
     * The {@code Shape.contains()} method allows a {@code Shape}
     * implementation to conservatively return {@code false} when:
     * <ul>
     * <li>
     * the <code>intersect</code> method returns <code>true</code> and
     * <li>
     * the calculations to determine whether or not the
     * <code>Shape</code> entirely contains the <code>Rectangle2D</code>
     * are prohibitively expensive.
     * </ul>
     * This means that for some {@code Shapes} this method might
     * return {@code false} even though the {@code Shape} contains
     * the {@code Rectangle2D}.
     * The {@link java.awt.geom.Area Area} class performs
     * more accurate geometric computations than most
     * {@code Shape} objects and therefore can be used if a more precise
     * answer is required.
     *
     * @param r The specified <code>Rectangle2D</code>
     * @return <code>true</code> if the interior of the <code>Shape</code>
     *         entirely contains the <code>Rectangle2D</code>;
     *         <code>false</code> otherwise or, if the <code>Shape</code>
     *         contains the <code>Rectangle2D</code> and the
     *         <code>intersects</code> method returns <code>true</code>
     *         and the containment calculations would be too expensive to
     *         perform.
     * @see #contains(double, double, double, double)
     * @since 1.2
     */
    @Override
    public boolean contains(Rectangle2D r) {
        return blob.contains(r);
    }

    /**
     * Returns an iterator object that iterates along the
     * <code>Shape</code> boundary and provides access to the geometry of the
     * <code>Shape</code> outline.  If an optional {@link java.awt.geom.AffineTransform}
     * is specified, the coordinates returned in the iteration are
     * transformed accordingly.
     * <p/>
     * Each call to this method returns a fresh <code>PathIterator</code>
     * object that traverses the geometry of the <code>Shape</code> object
     * independently from any other <code>PathIterator</code> objects in use
     * at the same time.
     * <p/>
     * It is recommended, but not guaranteed, that objects
     * implementing the <code>Shape</code> interface isolate iterations
     * that are in process from any changes that might occur to the original
     * object's geometry during such iterations.
     *
     * @param at an optional <code>AffineTransform</code> to be applied to the
     *           coordinates as they are returned in the iteration, or
     *           <code>null</code> if untransformed coordinates are desired
     * @return a new <code>PathIterator</code> object, which independently
     *         traverses the geometry of the <code>Shape</code>.
     * @since 1.2
     */
    @Override
    public PathIterator getPathIterator(AffineTransform at) {
        return blob.getPathIterator(at);
    }

    /**
     * Returns an iterator object that iterates along the <code>Shape</code>
     * boundary and provides access to a flattened view of the
     * <code>Shape</code> outline geometry.
     * <p/>
     * Only SEG_MOVETO, SEG_LINETO, and SEG_CLOSE point types are
     * returned by the iterator.
     * <p/>
     * If an optional <code>AffineTransform</code> is specified,
     * the coordinates returned in the iteration are transformed
     * accordingly.
     * <p/>
     * The amount of subdivision of the curved segments is controlled
     * by the <code>flatness</code> parameter, which specifies the
     * maximum distance that any point on the unflattened transformed
     * curve can deviate from the returned flattened path segments.
     * Note that a limit on the accuracy of the flattened path might be
     * silently imposed, causing very small flattening parameters to be
     * treated as larger values.  This limit, if there is one, is
     * defined by the particular implementation that is used.
     * <p/>
     * Each call to this method returns a fresh <code>PathIterator</code>
     * object that traverses the <code>Shape</code> object geometry
     * independently from any other <code>PathIterator</code> objects in use at
     * the same time.
     * <p/>
     * It is recommended, but not guaranteed, that objects
     * implementing the <code>Shape</code> interface isolate iterations
     * that are in process from any changes that might occur to the original
     * object's geometry during such iterations.
     *
     * @param at       an optional <code>AffineTransform</code> to be applied to the
     *                 coordinates as they are returned in the iteration, or
     *                 <code>null</code> if untransformed coordinates are desired
     * @param flatness the maximum distance that the line segments used to
     *                 approximate the curved segments are allowed to deviate
     *                 from any point on the original curve
     * @return a new <code>PathIterator</code> that independently traverses
     *         a flattened view of the geometry of the  <code>Shape</code>.
     * @since 1.2
     */
    @Override
    public PathIterator getPathIterator(AffineTransform at, double flatness) {
        return blob.getPathIterator(at, flatness);
    }
}

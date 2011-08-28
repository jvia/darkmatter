package com.giantcow.darkmatter.level.editor;

/**
 *
 * @author yukun
 */
public class Picture {

    private static final int DEFAULT_MAX_ELEMENTS = 100;
    private PictureElement[] elementList;
    private int elements, maxElements;

    public Picture() {
        this(DEFAULT_MAX_ELEMENTS);
        elements = 0;
    }

    /**
     *
     * @param max the max number of elements in the picture
     */
    public Picture(int max) {
        elementList = new PictureElement[max];
        maxElements = max;
        elements = 0;
    }

    /**
     * add new element in the picture
     * @param comp: the new picture element
     */
    public void add(PictureElement comp) {
        if (elements < maxElements) {
            elementList[elements] = comp;
            elements++;
        }
    }

    /**
     * for undo
     * delete the last element in elementList
     */
    public void minus() {
        if (elements > 0) {
            elementList[elements - 1] = null;
            elements--;
        } else {
            System.out.println("error");
        }
    }

    /**
     * get the number of elements in the picture
     * @return the number of elements
     */
    public int getNumberOfDrawingElements() {
        return elements;
    }

    /**
     * get the nth element
     * @param n the number of which element
     * @return the nth element
     */
    public PictureElement getDrawingElement(int n) {
        return elementList[n];
    }

    public void clear() {
        elementList = new PictureElement[DEFAULT_MAX_ELEMENTS];
        maxElements = DEFAULT_MAX_ELEMENTS;
        elements = 0;
    }

    @Override
    public String toString() {
        String picture = "";
        for (int i = 0; i < elements; i++) {
            double x = 0;
            double y =0;
            double r=0;
            double dx=0.0;
            double dy=0.0;
            if (elementList[i].getX1()<elementList[i].getWidth()){
                x=elementList[i].getX1();
            } else {
                x=elementList[i].getWidth();
            }
            if (elementList[i].getY1()<elementList[i].getHeight()){
                y=elementList[i].getY1();
            } else {
                y=elementList[i].getHeight();
            }
            if (Math.abs(elementList[i].getX1()-elementList[i].getWidth())>Math.abs(elementList[i].getY1()-elementList[i].getHeight())){
                r=Math.abs(elementList[i].getX1()-elementList[i].getWidth());
            } else {
                r=Math.abs(elementList[i].getY1()-elementList[i].getHeight());
            }
            String a = x+", "+y+", "+r+", "+dx+", "+dy+"\n";
            picture = picture + a;
        }
        return picture;
    }
}


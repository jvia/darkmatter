package com.giantcow.darkmatter.level.editor;

import com.giantcow.darkmatter.Utils;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.ArrayDeque;
import java.util.Deque;
import java.util.Scanner;

/** @author yukun */
public class MapPanel extends JPanel implements MouseListener, MouseMotionListener, ActionListener {

    private int lastX, lastY, startX, startY;
    private Oval rubberElement;
    private Deque<Oval> circles;


    public MapPanel() {
        setPreferredSize(Utils.gameDimension);
        setOpaque(false);

        addMouseListener(this);
        addMouseMotionListener(this);

        circles = new ArrayDeque<Oval>();
    }

    /**
     * implements three button: line, rectangle, oval if press button line, type=1 if press button
     * rectangle, type=2 if press button oval, type=3 if press button save, save the picture's data
     * to picture.txt if press button load, read the picture.txt and print the picture
     *
     * @param event
     */
    public void actionPerformed(ActionEvent event) {
        if (event.getActionCommand().equals("clear")) {
            Graphics g = getGraphics();
            g.setColor(Color.orange);
            g.drawRect(0, 0, getSize().width, getSize().height);
            circles.clear();
        } else if (event.getActionCommand().equals("save")) {
            try {
                Writer out = new FileWriter("map.lvl");
                for (Oval pe : circles)
                    out.write(pe.toString() + "\n");
                out.close();
            } catch (IOException e) {
                System.out.println("can not save file");
            }
        } else if (event.getActionCommand().equals("load")) {
            try {
                Scanner input = new Scanner(new FileReader("map.lvl"));
                Graphics g = getGraphics();
                g.clearRect(0, 0, getSize().width, getSize().height);
                circles.clear();
                while (input.hasNextLine()) {
                    String l = input.nextLine().replaceAll(" ", "");
                    String[] line = l.split(",");

                    // ignore commented lines or invalid lines
                    if (line[0].startsWith("#") || line.length < 5) continue;


                    int x, y, r;
                    x = (int) Double.parseDouble(line[0]);
                    y = (int) Double.parseDouble(line[1]);
                    r = (int) Double.parseDouble(line[2]);
                    circles.addLast(new Oval(x, y, x + r, y + r));
                }


                repaint();
            } catch (FileNotFoundException ex) {
                System.out.println("can not find file");
            }
        } else if (event.getActionCommand() == "undo") {
            circles.removeLast();
            repaint();
        }


    }

    /**
     * implements mouse pressed the rubberElement is currently selected pictureElement type
     *
     * @param e
     */
    public void mousePressed(MouseEvent e) {
        startX = e.getX();
        startY = e.getY();
        lastX = startX;
        lastY = startY;
        rubberElement = new Oval(startX, startY, lastX, lastY);
        Graphics g = getGraphics();
        g.setColor(Color.RED);
        g.setXORMode(Color.white);
        rubberElement.draw(g); //draw red line
    }

    /**
     * implements mouse dragges when mouse moved, get new x and y at actual time
     *
     * @param e
     */
    public void mouseDragged(MouseEvent e) {
        int newX, newY;
        newX = e.getX();
        newY = e.getY();

        Graphics g = getGraphics();
        g.setColor(Color.RED);
        g.setXORMode(Color.white);
        rubberElement.draw(g);  //erase red line

        rubberElement.setPosition(startX, startY, newX, newY);  //draw red line in new position
        rubberElement.draw(g);
        lastX = newX;
        lastY = newY;
    }

    /**
     * implements mouse released to draw final picture add new pictureElement in the picture arr
     *
     * @param e
     */
    public void mouseReleased(MouseEvent e) {
        circles.addLast(rubberElement);
        repaint();
    }

    /**
     * draw the picture again
     *
     * @param g
     */
    @Override
    public void paint(Graphics g) {
        g.setColor(Color.orange);
        g.fillRect(0, 0, Utils.gameDimension.width, Utils.gameDimension.height);
        for (Oval oval : circles) {
            g.setColor(Color.BLACK);
            oval.draw(g);
            g.setColor(Color.red);
        }
    }

    public void mouseClicked(MouseEvent e) { }

    public void mouseEntered(MouseEvent e) { }

    public void mouseExited(MouseEvent e) { }

    public void mouseMoved(MouseEvent e) { }
}


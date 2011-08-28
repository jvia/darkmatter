package com.giantcow.darkmatter.level.editor;

import com.giantcow.darkmatter.Utils;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/** @author yukun */
public class MapFrame extends JFrame implements ActionListener {

    /**
     * set picture frame at botton add four buttons: exit, line, rectangle, oval at center add a
     * drawing canvas enable the window close button
     *
     * @param w: the frame width
     * @param h: the frame height
     */
    public MapFrame(int w, int h) {
        setTitle("Drawing demonstration");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(w, h);


        MapPanel myPanel = new MapPanel();
        myPanel.setPreferredSize(Utils.gameDimension);
        add("Center", myPanel);

        JButton oval, exit, clear, save, load, undo;
        exit = new JButton("exit");
        exit.setActionCommand("exit");

        oval = new JButton("matter");
        oval.setActionCommand("oval");

        clear = new JButton("clear");
        clear.setActionCommand("clear");

        save = new JButton("save");
        save.setActionCommand("save");

        load = new JButton("load");
        load.setActionCommand("load");

        undo = new JButton("undo");
        undo.setActionCommand("undo");

        Panel south = new Panel();
        south.add(oval);
        south.add(exit);
        south.setBackground(Color.black);

        Panel north = new Panel();
        north.add(undo);
        north.add(clear);
        north.add(save);
        north.add(load);
        north.setBackground(Color.black);

        add("South", south);
        add("North", north);

        exit.addActionListener(this);
        oval.addActionListener(myPanel);
        clear.addActionListener(myPanel);
        save.addActionListener(myPanel);
        load.addActionListener(myPanel);
        undo.addActionListener(myPanel);

        pack();
        setVisible(true);
    }

    /**
     * implements the "exit" button when clicks the "exit" button, closes the program down
     *
     * @param e
     */
    public void actionPerformed(ActionEvent e) {
        System.exit(0);
    }
}

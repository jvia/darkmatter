package com.giantcow.darkmatter.level.editor;

import java.awt.*;

/** @author yukun */
public class Tool {

    public static void main(String args[]) {
        MapFrame r;
        r = new MapFrame(1280, 720);
        r.setPreferredSize(new Dimension(1280, 820));
        r.pack();
        r.setVisible(true);
    }
}

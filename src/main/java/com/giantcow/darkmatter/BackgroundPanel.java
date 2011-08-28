package com.giantcow.darkmatter;

import javax.swing.*;
import java.awt.*;

/**
 * @author Jeremiah M. Via <jxv911@cs.bham.ac.uk>
 */
public class BackgroundPanel extends JPanel {

        Image img;

        public BackgroundPanel() {
            super();
            img =
                    Toolkit.getDefaultToolkit().createImage(getClass().getClassLoader().getResource("nebbg_occluded.png"));
        }

        @Override
        public void paintComponent(Graphics g) {
            super.paintComponent(g);
            Graphics2D g2 = (Graphics2D) g;
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                    RenderingHints.VALUE_ANTIALIAS_ON);
            g2.drawImage(img, 0, 0, this);
        }
}
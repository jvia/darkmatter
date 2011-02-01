/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sanbox.test;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.swing.JFrame;

/**
 *
 * @author Yukun
 */
public class main {

    public static void main(String[] args) throws InterruptedException {
        JFrame frame = new JFrame("physice");
        frame.setSize(615, 650);
        frame.setTitle("example");
        frame.addWindowListener(new WindowAdapter() {

            @Override
            public void windowClosing(WindowEvent e) {
                System.exit(0);
            }
        });
        frame.setVisible(true);
        panel p = new panel();
        frame.add(p);
        p.requestFocus();
        int n = 0;
        while (n == 0) {
            p.repaint();
            Thread.sleep(10);
        }
    }
}

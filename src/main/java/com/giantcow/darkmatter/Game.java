/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.giantcow.darkmatter;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import javax.swing.JFrame;
import javax.swing.JPanel;

/**
 * Class to define game.
 *
 * @author Yukun Wang <yxw999@cs.bham.ac.uk>
 * @author Jeremiah Via <jxv911@cs.bham.ac.uk>
 * @version 2011.0203
 * @since 0.1
 */
public class Game {

    Matter player1;
    Matter player2;
    ArrayList<Matter> NPC;
    JPanel panel;
    double ScreenWidth;
    double ScreenHeight;
    JPanel GamePanel;

    public Game(int width, int height) {
        ScreenWidth = width;
        ScreenHeight = height;
        NPC = new ArrayList<Matter>();
        for (int n = 0; n < 10; n++) {
            NPC.add(new Matter(ScreenWidth * Math.random(), ScreenHeight * Math.random(), 50 * Math.random(), 2 * Math.random(), 2 * Math.random()));
        }
        player1 = new Matter(ScreenWidth / 3, ScreenHeight / 3, 30, 0, 0);
        player2 = new Matter(2 * ScreenWidth / 3, 2 * ScreenHeight / 3, 30, 0, 0);
        GamePanel = new GamePanel();
    }

    public void update(){
        for (int n = 0; n < NPC.size(); n++) {
            if(NPC.get(n).getBlob().getMaxX()>ScreenWidth||NPC.get(n).getBlob().getMinX()<0){
                NPC.get(n).setDx(-NPC.get(n).getDx());
            }
            if(NPC.get(n).getBlob().getMaxY()>ScreenHeight||NPC.get(n).getBlob().getMinY()<0){
                NPC.get(n).setDy(-NPC.get(n).getDy());
            }
            NPC.get(n).update();
        }
        player1.update();
        player2.update();

    }



    private class GamePanel extends JPanel{


        @Override
        public void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;
        g2.setColor(Color.GRAY);
        g2.fillRect(0, 0, (int)ScreenWidth, (int)ScreenHeight);
        g2.setColor(Color.RED);

        for (int n = 0; n < NPC.size(); n++) {
            g2.fill(NPC.get(n));
        }

        g2.setColor(Color.BLACK);
        g2.fill(player1);
        g2.fill(player2);

    }

    }

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
        Game game=new Game(615,650);
        frame.add(game.GamePanel);
        while (true){
            game.update();         
            game.GamePanel.repaint();
            Thread.sleep(10);
        }
    }
}

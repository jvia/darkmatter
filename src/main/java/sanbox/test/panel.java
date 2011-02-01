package sanbox.test;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.JPanel;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author Yukun
 */
public class panel extends JPanel implements KeyListener, MouseListener {

    game game;

    public panel() {
        this.setSize(610, 610);
        game = new game(600, 600);
        this.addKeyListener(this);
        this.addMouseListener(this);
    }

    @Override
    public void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;
        g2.setColor(Color.GRAY);
        g2.fillRect(0, 0, 615, 615);
        g2.setColor(Color.RED);

        for (int n = 0; n < game.NPC.size(); n++) {
            g2.fill(game.NPC.get(n));
        }
        game.update();
        g2.setColor(Color.BLACK);
        g2.fill(game.player);

    }

    public void keyTyped(KeyEvent ke) {
    }

    public void keyReleased(KeyEvent ke) {
    }

    public void keyPressed(KeyEvent ke) {
    }

    public void mouseClicked(MouseEvent me) {
        game.player.mouse(me.getX(), me.getY());

    }

    public void mousePressed(MouseEvent me) {
    }

    public void mouseReleased(MouseEvent me) {
    }

    public void mouseEntered(MouseEvent me) {
    }

    public void mouseExited(MouseEvent me) {
        game.player.slow();
    }
}

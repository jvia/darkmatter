/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sanbox.test;

import java.util.ArrayList;

/**
 *
 * @author yukun
 */
public class game {

    physics m;
    ArrayList<matter> NPC;
    matter player;
    int w;
    int h;

    public game(int width, int height) {
        w = width;
        h = height;
        NPC = new ArrayList<matter>();
        for (int n = 0; n < 10; n++) {
            NPC.add(new matter(w * Math.random(), h * Math.random(), 50 * Math.random(), 2 * Math.random(), 2 * Math.random()));
        }
        player = new matter(w / 2, h / 2, 15, 0, 0);
        m = new physics(w, h);
    }

    public void update() {
        for (int n = 0; n < NPC.size(); n++) {
            m.move(NPC.get(n));
        }
        for (int n = 0; n < NPC.size(); n++) {
            for (int i = n + 1; i < NPC.size(); i++) {
                matter a = NPC.get(n);
                matter b = NPC.get(i);
                if (a.intersects(b)) {
                    if (a.eat(b) || b.eat(a)) {
                        NPC.add(m.eat(a, b));
                        NPC.remove(a);
                        NPC.remove(b);
                    } else {
                        NPC.remove(a);
                        NPC.remove(b);
                        NPC.addAll(m.absorb(a, b));
                    }
                }
            }
        }
        //m.move(player);
        /**for (int n = 0; n < NPC.size(); n++) {
            if (player.intersects(NPC.get(n))) {
                player = m.eat(player, NPC.get(n));
                NPC.remove(NPC.get(n));
            }
        }*/
        //m.move(player);
    }
}

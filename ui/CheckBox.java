package ui;

import java.awt.Font;
import java.awt.Graphics;

import raccoon.PVector;

/**
 * CheckBox
 * This class aport a checkbox in a position
 * 
 * @author JaviLeL
 * @version 1.0
 */
public class CheckBox {
    PVector loc;
    String text;
    boolean state;
    int size;

    /**
     * This constructor make the Checkbox in the position
     * 
     * @param x     set x position
     * @param y     set y position
     * @param text  set text
     * @param state set state at start (true checked false unchecked)
     * @param size  set the size of the font and box
     */
    public CheckBox(int x, int y, String text, boolean state, int size) {
        this.loc = new PVector(x, y);
        this.text = text;
        this.state = state;
        this.size = size;
    }

    /**
     * 
     * @param g
     */
    public void draw(Graphics g) {
        g.fillRect((int) loc.x, (int) (loc.y - (size / 1.3)), size * 2 / 3, size * 2 / 3);
        Text.drawText(g, text, loc.x + size, loc.y - size / 10, false, new Font("Dialog", Font.PLAIN, size));
    }

    /**
     * 
     * @return state return the actual state of this checkbox
     */
    public boolean getState() {
        return state;
    }
}
package ui;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

import inputs.Mouse;
import raccoon.PVector;
import settings.Constants;

/**
 * Class how can make a button in screen and exec a block of code.
 * 
 * @author JaviLeL
 * @version 1.1
 */
public class Button {
    private boolean buttonPressed;
    private String text;
    PVector loc;
    Color col;
    Acttion acttion;

    /**
     * This is the more basic button configuration
     * 
     * @param x       Position in x
     * @param y       Position in y
     * @param text    Set the text in the midel of button
     * @param acttion Set the block command how it wana run when press
     */
    public Button(int x, int y, String text, Acttion acttion) {
        this.loc = new PVector(x, y);
        this.text = text;
        this.col = new Color(51, 51, 51);
        this.buttonPressed = false;
        this.acttion = acttion;
    }

    /**
     * This function draw the shape of button
     * 
     * @param g Graphics of window
     */
    public void draw(Graphics g) {
        g.setColor(col);
        g.fillRect((int) loc.x - Constants.tamButomX / 2, (int) loc.y - Constants.tamButomY / 2, Constants.tamButomX,
                Constants.tamButomY);

        g.setColor(new Color(255, 255, 255));
        Text.drawText(g, text, loc.x, loc.y - 5, true,
                new Font("Dialog", Font.PLAIN, 18));

    }

    /**
     * This function make the funtionality of button like
     * read the mouse is in it...
     */
    public void update() {
        if (Mouse.x >= loc.x - Constants.tamButomX / 2
                && Mouse.x <= loc.x - Constants.tamButomX / 2 + Constants.tamButomX &&
                Mouse.y >= loc.y - Constants.tamButomY / 2
                && Mouse.y <= loc.y - Constants.tamButomY / 2 + Constants.tamButomY) {
            col = new Color(100, 100, 100);
            if (Mouse.left) {
                buttonPressed = true;
            } else {
                buttonPressed = false;
            }
        } else {
            col = new Color(51, 51, 51);
        }

        if (buttonPressed) {
            acttion.accionARealizar();
        }
    }
}
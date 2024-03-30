package states;

import java.awt.Color;
import java.awt.Graphics;

import inputs.Mouse;
import raccoon.PVector;
import settings.Settings;

/**
 * CameraState
 * This is a class how Im prove the camera
 * 
 * @author JaviLeL
 * @version 1.0
 */
public class CameraState extends State {
    PVector loc, lastClick;
    boolean isPressed;

    public CameraState() {
        this.loc = new PVector(0, 0);
        this.isPressed = false;
    }

    @Override
    public void update() {
        if (Mouse.mousePressed && Mouse.right) {
            if (!isPressed) {
                lastClick = new PVector(Mouse.x, Mouse.y);
                isPressed = true;
            }
        } else {
            lastClick = null;
            isPressed = false;
        }
        if (lastClick != null) {
            // System.out.println(loc.x + " // " + loc.y);
            loc.add(new PVector(Mouse.x - lastClick.x, Mouse.y - lastClick.y));
            lastClick = new PVector(Mouse.x, Mouse.y);
        }
    }

    @Override
    public void draw(Graphics g) {
        g.setColor(new Color(0));
        g.fillRect(0, 0, Settings.width, Settings.height);
        g.setColor(new Color(255, 255, 255));
        // Move Camera
        // g.drawLine((int) loc.x - 5, (int) loc.y, (int) loc.x + 5, (int) loc.y);
        // g.drawLine((int) loc.x, (int) loc.y - 5, (int) loc.x, (int) loc.y + 5);
        g.translate((int) loc.x, (int) loc.y);
        g.fillRect(0, 0, 100, 100);
    }

}

package inputs;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;

/**
 * Class how can get alot infomation of the mouse
 * 
 * @author Javi.LeL
 * @version 1.2
 */
public class Mouse extends MouseAdapter {
    /*
     * x return the postion in x of the mouse
     * y return the postion in y of the mouse
     * By default the position of mouse is (0, 0)
     */
    public static int x, y;
    /*
     * mousePressed return true if one of the mouse button is pressed at the moment
     * mouseWheelUp return true if mouse wheel is move up
     * mouseWheelDown return true if mouse wheel is move down
     */
    public static boolean mousePressed, mouseWheelUp, mouseWheelDown;
    /*
     * left return a true if button pressed left of mouse is pressed
     * center return a true if button pressed center of mouse is pressed
     * right return a true if button pressed right of mouse is pressed
     * By default all of then is false (not pressed)
     */
    public static boolean left, center, right;

    /**
     * This funcion only is active when the mouse is pressed
     * 
     * @param e
     */
    @Override
    public void mousePressed(MouseEvent e) {
        // If one of the button of mouse is pressed
        if (e.getButton() == MouseEvent.BUTTON1
                || e.getButton() == MouseEvent.BUTTON2
                || e.getButton() == MouseEvent.BUTTON3) {
            // Set mousePressed true
            mousePressed = true;
        }
        // If button pressed is BUTTON1 then is the button left
        if (e.getButton() == MouseEvent.BUTTON1) {
            // Set the left true
            left = true;
        }
        // If button pressed is BUTTON1 then is the button center
        if (e.getButton() == MouseEvent.BUTTON2) {
            // Set the center true
            center = true;
        }
        // If button pressed is BUTTON1 then is the button right
        if (e.getButton() == MouseEvent.BUTTON3) {
            // Set the right true
            right = true;
        }
    }

    /**
     * This funcion only is active when the mouse is released
     * 
     * @param e
     */
    @Override
    public void mouseReleased(MouseEvent e) {
        // If one of the buttons is released
        if (e.getButton() == MouseEvent.BUTTON1
                || e.getButton() == MouseEvent.BUTTON2
                || e.getButton() == MouseEvent.BUTTON3) {
            // Set mousePressed false
            mousePressed = false;
        }
        // If button released is BUTTON1 then is the button left
        if (e.getButton() == MouseEvent.BUTTON1) {
            // Set the left false
            left = false;
        }
        if (e.getButton() == MouseEvent.BUTTON2) {
            // Set the center false
            center = false;
        }
        if (e.getButton() == MouseEvent.BUTTON3) {
            // Set the right false
            right = false;
        }
    }

    /**
     * This funcion only is active when the mouse is dragging
     * 
     * @param e
     */
    @Override
    public void mouseDragged(MouseEvent e) {
        // When then the x and y get the position of mouse
        x = e.getX();
        y = e.getY();
    }

    /**
     * This funcion only is active when the mouse is moving
     * 
     * @param e
     */
    @Override
    public void mouseMoved(MouseEvent e) {
        // When then the x and y get the position of mouse
        x = e.getX();
        y = e.getY();
    }

    /**
     * This funcion only is active when the wheel mouse is moving
     * 
     * @param e
     */

    @Override
    public void mouseWheelMoved(MouseWheelEvent e) {
        // Always need to be resset
        if (e.getWheelRotation() < 0) {
            // System.out.println("mouse wheel Up");
            mouseWheelUp = true;
        } else if (e.getWheelRotation() > 0) {
            // System.out.println("mouse wheel Down");
            mouseWheelDown = true;
        }
    }
}
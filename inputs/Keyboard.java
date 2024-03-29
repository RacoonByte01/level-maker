package inputs;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

/**
 * Class how can read all the inputs of the key board
 * 
 * @author JaviLeL
 * @version 1.2
 */
public class Keyboard implements KeyListener {
    /* This Character return the key how is pressed in the moment */
    public static Character key = null;
    /* This boolean return if one key is pressed in th moment */
    public static boolean keyPressed;

    /**
     * This funcition only start when typed key is pressed
     * 
     * @param e
     */
    @Override
    public void keyTyped(KeyEvent e) {
        // TODO Read more about it
    }

    /**
     * This funcition only start when key is pressed
     * 
     * @param e
     */
    @Override
    public void keyPressed(KeyEvent e) {
        // Set the keyPressed true
        keyPressed = true;
        // Set the key to the key pressed
        key = e.getKeyChar();
    }

    /**
     * This funcition only start when key is released
     * 
     * @param e
     */
    @Override
    public void keyReleased(KeyEvent e) {
        // Set the keyPressed false
        keyPressed = false;
    }
}

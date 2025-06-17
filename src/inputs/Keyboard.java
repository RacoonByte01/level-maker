package inputs;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

/**
 * class Keyboard
 * Clase que lee la entrada de teclado
 * 
 * @author JaviLeL
 * @version 1.2
 */
public class Keyboard implements KeyListener {
    /* Este variable obtiene el valor de la tecla presinada en el momento */
    public static Character key = null;
    /*
     * Devolvera un true o false dependiendo de si se ha presionado la tecla
     * correspondiente o no
     */
    public static boolean right, left, jump;
    /* Esta varible devuleve su se esta presionando una tecla del teclado */
    public static boolean keyPressed;

    /**
     * Este metodo se lanza al presionar una tecla
     * 
     * @param e
     */
    @Override
    public void keyTyped(KeyEvent e) {
    }

    /**
     * El metodo solo se lanza al presionar una tecla del teclado
     * 
     * @param e
     */
    @Override
    public synchronized void keyPressed(KeyEvent e) {
        // Pone la varible keyPressed a true
        keyPressed = true;
        // Revisa se un de las teclas pulsadas es la de realizar una accion
        key = e.getKeyChar();
        if (e.getKeyCode() == 32) {
            jump = true;
        }
        if (e.getKeyCode() == KeyEvent.VK_A) {
            left = true;
        }
        if (e.getKeyCode() == KeyEvent.VK_D) {
            right = true;
        }
    }

    /**
     * Esta funcion solo se lanza al soltar una tecla del teclado
     * 
     * @param e
     */
    @Override
    public void keyReleased(KeyEvent e) {
        // Pone keyPressed como false
        keyPressed = false;
        if (e.getKeyCode() == 32) {
            jump = false;
        }
        if (e.getKeyCode() == KeyEvent.VK_A) {
            left = false;
        }
        if (e.getKeyCode() == KeyEvent.VK_D) {
            right = false;
        }
    }
}

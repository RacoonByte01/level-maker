package inputs;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;

/**
 * class Mouse
 * clase que aporta entradas de raton
 * 
 * @author Javi.LeL
 * @version 1.2
 */
public class Mouse extends MouseAdapter {
    /*
     * x devulve la posicion en x del raton
     * y devulve la posicion en y del raton
     * Por defeecto la posicion del raton es (0, 0)
     */
    public static int x, y;
    /*
     * mousePressed devuleve un true si se esta presionado un boton del raton
     * mouseWheelUp devuleve un turu si la rueda del raton esta moviendose arriba
     * mouseWheelDown devuleve un turu si la rueda del raton esta moviendose abajo
     */
    public static boolean mousePressed, mouseWheelUp, mouseWheelDown;
    /*
     * left devuelve un true si el boton izquierdo del raton se esta presionado
     * center devuelve un true si el boton centro del raton se esta presionado
     * right devuelve un true si el boton derevho del raton se esta presionado
     * Por defecto ninguno se estara presionando
     */
    public static boolean left, center, right;

    /**
     * Este metodo solo se lanza la presionar el raton
     * 
     * @param e
     */
    @Override
    public void mousePressed(MouseEvent e) {
        // Si uno de los botones del raton se presiona
        if (e.getButton() == MouseEvent.BUTTON1
                || e.getButton() == MouseEvent.BUTTON2
                || e.getButton() == MouseEvent.BUTTON3) {
            // La varible mousePressed se pondra como true
            mousePressed = true;
        }
        // Si el boton izquierdo se presiona
        if (e.getButton() == MouseEvent.BUTTON1) {
            // La varible left se pondra en true
            left = true;
        }
        // Si el boton central se presiona
        if (e.getButton() == MouseEvent.BUTTON2) {
            // La varible center se pondra en true
            center = true;
        }
        // Si el boton right se presiona
        if (e.getButton() == MouseEvent.BUTTON3) {
            // La varible right se pondra en true
            right = true;
        }
    }

    /**
     * Este metodo solo se lanza cundo los botones del raton se sueltan
     * 
     * @param e
     */
    @Override
    public void mouseReleased(MouseEvent e) {
        // Si uno de los botones del raton deja de estar presionado
        if (e.getButton() == MouseEvent.BUTTON1
                || e.getButton() == MouseEvent.BUTTON2
                || e.getButton() == MouseEvent.BUTTON3) {
            // La variable mousePressed se pondra en falso
            mousePressed = false;
        }
        // Si el boton izquierdo deja de estar presionado
        if (e.getButton() == MouseEvent.BUTTON1) {
            // La variable left se pondra en false
            left = false;
        }
        // Si el boton central deja de estar presionado
        if (e.getButton() == MouseEvent.BUTTON2) {
            // La variable center se pondra en false
            center = false;
        }
        // Si el boton derecho deja de estar presionado
        if (e.getButton() == MouseEvent.BUTTON3) {
            // La variable right se pondra en false
            right = false;
        }
    }

    /**
     * Esta funcion solo se lanza si el se esta arrastrando (Se presiona y se
     * mueve)
     * 
     * @param e
     */
    @Override
    public void mouseDragged(MouseEvent e) {
        // Se obtine su posiocion en x e y
        x = e.getX();
        y = e.getY();
    }

    /**
     * Este metodo solo se lanza si el raton se esta moviendo
     * 
     * @param e
     */
    @Override
    public void mouseMoved(MouseEvent e) {
        // Se obtiene su posiocion x e y
        x = e.getX();
        y = e.getY();
    }

    /**
     * Este metodo solo se lanza cuando la rueda del raton se mueve
     * Esta necesita ser reseteada por el programador
     * 
     * @param e
     */

    @Override
    public void mouseWheelMoved(MouseWheelEvent e) {

        if (e.getWheelRotation() < 0) { // Si es inferiror a 0 significara que se mueve para arriba
            mouseWheelUp = true;
        } else if (e.getWheelRotation() > 0) { // Si es superiror a 0 significara que se mueve para abajo
            mouseWheelDown = true;
        }
    }
}
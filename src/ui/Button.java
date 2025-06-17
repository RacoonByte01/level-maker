package ui;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

import inputs.Mouse;
import raccoon.PVector;
import settings.Constants;

/**
 * class Button
 * Clase que probee de un boton funcional
 * 
 * @author Javi.LeL
 * @version 1.1
 */
public class Button {
    protected boolean buttonPressed, isEnable;
    protected String text;
    PVector loc;
    Color col, coltext;
    Acttion acttion;

    /**
     * Constructor basico para generar un boton
     * 
     * @param x       Position in x
     * @param y       Position in y
     * @param text    Set the text in the midel of button
     * @param acttion Set the block command how it wana run when press
     */
    public Button(int x, int y, String text, Acttion acttion) {
        this.loc = new PVector(x, y);
        this.text = text;
        this.buttonPressed = false;
        this.acttion = acttion;
        this.isEnable = true;
        this.coltext = new Color(255, 255, 255);
        LevelCard.restartIdSelected();
    }

    /**
     * Este metodo se encargara de realizar todas las operaciones graficas
     * 
     * @param g Graphics of window
     */
    public void draw(Graphics g) {
        // Dibujara un fondo
        g.setColor(col);
        g.fillRect((int) loc.x - Constants.tamButomX / 2, (int) loc.y - Constants.tamButomY / 2, Constants.tamButomX,
                Constants.tamButomY);
        // Pondra un texto por encima
        g.setColor(coltext);
        Text.drawText(g, text, loc.x, loc.y - 5, true,
                new Font("Dialog", Font.PLAIN, 18));

    }

    /**
     * Metodo que realizara todas las operaciones relacionadas con las matematicas
     */
    public void update() {
        if (isEnable) { // Si el boton esta activado
            coltext = new Color(255, 255, 255);
            // Si el raton esta en su caja de colision
            if (Mouse.x >= loc.x - Constants.tamButomX / 2
                    && Mouse.x <= loc.x - Constants.tamButomX / 2 + Constants.tamButomX &&
                    Mouse.y >= loc.y - Constants.tamButomY / 2
                    && Mouse.y <= loc.y - Constants.tamButomY / 2 + Constants.tamButomY) {
                // El fondo cambiara de color
                col = Constants.cols[7];
                if (Mouse.left && Mouse.mousePressed) { // Y es presionado
                    // Se presionara
                    buttonPressed = true;
                } else {
                    buttonPressed = false;
                }
            } else {
                // El fondo cambiara de color
                col = Constants.cols[4];
            }
            if (buttonPressed) { // Si el raton se presiona
                // Realizara la accion
                acttion.accionARealizar();
                // Se reiniciaran las acciones del raton
                Mouse.mousePressed = false;
                // Y del boton
                buttonPressed = false;
            }
        } else { // Si no esta activado
            // El color del texto y boton se volveran opacos
            col = new Color(155, 155, 155, 20);
            coltext = new Color(255, 255, 255, 20);

        }
    }

    /**
     * Permite cambiar el estado de activo por true o false
     * 
     * @param isEnable
     */
    public void setEnable(boolean isEnable) {
        this.isEnable = isEnable;
    }
}
package ui;

import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;

import raccoon.PVector;

/**
 * Class how print a text in the screen
 * 
 * @author Javi.LeL
 * @version 1.1
 */
public class Text {
    /**
     * Constructor basico para crear in Text
     * 
     * @param g      Graphics of window
     * @param text   The text how want to print
     * @param x      The position in X
     * @param y      The position in Y
     * @param center If is centered or not
     * @param font   The font who use
     */
    public static void drawText(Graphics g, String text, float x, float y, boolean center, Font font) {
        /*
         * Font Example
         * Font menuFont = new Font("Dialog", Font.PLAIN, size);
         */
        g.setFont(font);
        // Obtine la metricas de la fuente
        FontMetrics fontMetrics = g.getFontMetrics();
        PVector loc = new PVector(x, y);

        if (center) { // Si esta centrado
            // El texto se mueve haci la izquierda la mitad de su tamano
            loc.x = loc.x - fontMetrics.stringWidth(text) / 2;
            loc.y = loc.y + fontMetrics.getHeight() / 2;
        }
        // Dibuja el resultado
        g.drawString(text, (int) loc.x, (int) loc.y);
    }
}

package ui;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;

import inputs.Keyboard;
import inputs.Mouse;
import raccoon.PVector;
import settings.Constants;

/**
 * class TextBox
 * Clase que probee de una caja de texto
 * 
 * @author Javi.LeL
 * @version 1.0
 */
public class TextBox {
    private PVector loc, tam;
    private Color colText, colBox, colSelect, colNotSelect;
    private String text, textInicio;
    private int size;
    private boolean isSelected;
    private boolean isPassword;

    /**
     * Constructor minimo para una caja de texto
     * 
     * @param x
     * @param y
     * @param tamX
     * @param tamY
     * @param textInicio
     * @param size
     */
    public TextBox(float x, float y, float tamX, float tamY, String textInicio, int size) {
        this(x, y, tamX, tamY, textInicio, size, false);
    }

    /**
     * Constructor que establece si es una contrasena o no
     * 
     * @param x
     * @param y
     * @param tamX
     * @param tamY
     * @param textInicio
     * @param size
     * @param isPassword
     */
    public TextBox(float x, float y, float tamX, float tamY, String textInicio, int size, boolean isPassword) {
        this.loc = new PVector(x, y);
        this.tam = new PVector(tamX, tamY);
        this.textInicio = textInicio;
        this.text = "";
        this.size = size;
        this.isPassword = isPassword;
        // Set some parameters by default
        this.colText = new Color(255, 255, 255);
        this.colNotSelect = Constants.cols[4];
        this.colSelect = Constants.cols[7];
        this.colBox = Constants.cols[4];
        this.isSelected = false;
    }

    /**
     * Este metodo se encargara de realizar todas las operaciones graficas
     * 
     * @param g
     */
    public void draw(Graphics g) {
        g.setColor(colBox);
        g.fillRect((int) (loc.x - tam.x / 2), (int) (loc.y - tam.y / 2), (int) tam.x, (int) tam.y);
        FontMetrics fontMetrics = g.getFontMetrics();
        if (text.length() == 0) {
            g.setColor(Constants.cols[8]);
            Text.drawText(g, textInicio, loc.x - tam.x / 2 + 10, loc.y + fontMetrics.getHeight() / 3, false,
                    new Font("Dialog", Font.PLAIN, size));
        } else {
            g.setColor(colText);
            if (!isPassword) {
                Text.drawText(g, text, loc.x - tam.x / 2 + 10, loc.y + fontMetrics.getHeight() / 3, false,
                        new Font("Dialog", Font.PLAIN, size));
            } else {
                Text.drawText(g, genAsterisc(text.length()), loc.x - tam.x / 2 + 10,
                        loc.y + fontMetrics.getHeight() / 3 + 8, false,
                        new Font("Dialog", Font.PLAIN, (int) (size * 1.5)));
            }
            Text.drawText(g, "", 0, 0, false, new Font("Dialog", Font.PLAIN, (int) (size)));
        }
    }

    /**
     * Metodo que realizara todas las operaciones relacionadas con las matematicas
     */
    public void update() {
        if (isSelected) {
            if (Keyboard.key != null) {
                // If press the key return delete the last character
                if ((int) Keyboard.key == 8) {
                    if (text.length() >= 1) { // And if more or equals 1
                        text = text.substring(0, text.length() - 1); // Delete the last key
                    }
                } else if ((int) Keyboard.key == 65535) {
                    // Especials keys Like alt, altgr, shift, keyDirections...
                } else if ((int) Keyboard.key == 10) {
                    // Enter Press
                    isSelected = false;
                } else {
                    // Press any key
                    // System.out.println((int) Keyboard.key);
                    text += Keyboard.key;
                }
                Keyboard.key = null;
            }
        } else {
            colBox = colNotSelect;
        }
        if (Mouse.x >= loc.x - tam.x / 2 && Mouse.x <= loc.x + tam.x / 2 &&
                Mouse.y >= loc.y - tam.y / 2 && Mouse.y <= loc.y + tam.y / 2) {
            colBox = colSelect;
            if (Mouse.left) {
                Keyboard.key = null;
                isSelected = true;
            }
        } else if (!(Mouse.x >= loc.x - tam.x / 2 && Mouse.x <= loc.x + tam.x / 2 &&
                Mouse.y >= loc.y - tam.y / 2 && Mouse.y <= loc.y + tam.y / 2) && Mouse.left) {
            isSelected = false;
        }
    }

    /**
     * Devuele lo escrito por el usuario
     * 
     * @return text
     */
    public String getText() {
        return text;
    }

    /**
     * Establece un texto
     * 
     * @return text
     */
    public void setText(String text) {
        this.text = text;
    }

    /**
     * Metodo que devuelve un numero de asteriscos en un array
     * 
     * @param numAsterisc
     * @return
     */
    private String genAsterisc(int numAsterisc) {
        String resultadoString = "";
        for (int i = 0; i < numAsterisc; i++) {
            resultadoString += "*";
        }
        return resultadoString;
    }

    /**
     * Estableze si es de tipo contrasena o no
     */
    public void togglePassword() {
        isPassword = !isPassword;
    }
}
package ui;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

import inputs.Keyboard;
import inputs.Mouse;
import raccoon.PVector;

/**
 * TextBox
 * This class aport a textbox in a position how can be write by th user
 * 
 * @author JaviLeL
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
     * Constructor of the minimal textbox
     * 
     * @param x          set the position in x
     * @param y          set the position in y
     * @param tamX       set the size in x
     * @param tamY       set the size in y
     * @param textInicio set the text at start
     * @param size       set the size of the text
     */
    public TextBox(float x, float y, float tamX, float tamY, String textInicio, int size) {
        this.loc = new PVector(x, y);
        this.tam = new PVector(tamX, tamY);
        this.textInicio = textInicio;
        this.text = "";
        this.size = size;
        // Set some parameters by default
        this.colText = new Color(255, 255, 255);
        this.colNotSelect = new Color(51, 51, 51);
        this.colSelect = new Color(100, 100, 100);
        this.colBox = colNotSelect;
        this.isSelected = false;
    }

    /**
     * Constructor of the minimal textbox
     * 
     * @param x          set the position in x
     * @param y          set the position in y
     * @param tamX       set the size in x
     * @param tamY       set the size in y
     * @param textInicio set the text at start
     * @param size       set the size of the text
     * @param isPassword if set true write asterisc
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
        this.colNotSelect = new Color(51, 51, 51);
        this.colSelect = new Color(100, 100, 100);
        this.colBox = colNotSelect;
        this.isSelected = false;
    }

    /**
     * This function draw the textbox
     * 
     * @param g A grapics pass of windows
     */
    public void draw(Graphics g) {
        g.setColor(colBox);
        g.fillRect((int) (loc.x - tam.x / 2), (int) (loc.y - tam.y / 2), (int) tam.x, (int) tam.y);
        if (text.length() == 0) {
            g.setColor(new Color(155, 155, 155));
            Text.drawText(g, textInicio, loc.x, loc.y - 5, true, new Font("Dialog", Font.PLAIN, size));
        } else {
            g.setColor(colText);
            if (!isPassword) {
                Text.drawText(g, text, loc.x, loc.y - 5, true, new Font("Dialog", Font.PLAIN, size));
            } else {
                Text.drawText(g, genAsterisc(text.length()), loc.x, loc.y, true,
                        new Font("Dialog", Font.PLAIN, (int) (size * 1.5)));
            }
        }
    }

    /**
     * This function calculate all necessary operations
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
     * This function return the text wrote by the user
     * 
     * @return text
     */
    public String getText() {
        return text;
    }

    /**
     * This is a function that write a number of asterisc
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
     * Change visibility of password
     */
    public void togglePassword() {
        isPassword = !isPassword;
    }
}
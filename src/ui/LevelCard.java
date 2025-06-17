package ui;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;

import db.dto.LevelDTO;
import inputs.Keyboard;
import inputs.Mouse;
import raccoon.PVector;
import settings.Constants;
import settings.Settings;

/**
 * class LevelCard
 * Clase que probee de un sistema visual para representar los datos de un nivel
 * dentro de una carta
 * 
 * @author Javi.LeL
 * @version 1.0
 */
public class LevelCard {
    private PVector loc, tam;
    private LevelDTO level;
    private boolean isSelected;
    private Color colText, colBox, colSelect, colNotSelect;
    private static Integer idSelected = null;

    /**
     * Consructor dependiendo de cuando se creo la carta esta se dibujara mas arriba
     * o mas abajo
     * 
     * @param y
     * @param level
     */
    public LevelCard(float y, LevelDTO level) {
        this.tam = new PVector(Settings.width * 4 / 5 - 10, 100);
        this.loc = new PVector(Settings.width / 2, y * (this.tam.y + 5) + (this.tam.y * 2 / 3 + 5));
        this.level = level;
        this.colText = new Color(255, 255, 255);
        this.colNotSelect = Constants.cols[4];
        this.colSelect = Constants.cols[7];
        this.colBox = Constants.cols[4];
        this.isSelected = false;
    }

    /**
     * Metodo que realizara todas las operaciones relacionadas con las matematicas
     * 
     * @param scroly
     */
    public void update(int scroly) {
        // Si no esta seleccionado
        if (!(isSelected && idSelected == level.getId())) {
            // El color del fondo cambiara
            colBox = colNotSelect;
        }
        if (Mouse.x >= loc.x - tam.x / 2 && Mouse.x <= loc.x + tam.x / 2 &&
                Mouse.y >= loc.y + scroly - tam.y / 2 && Mouse.y <= loc.y + scroly + tam.y / 2) {
            // El color del fondo cambiara
            colBox = colSelect;
            if (Mouse.left) { // Si es presionado
                // Se reiniciaran algunos valores
                Keyboard.key = null;
                // Se marcara como seleccionado
                isSelected = true;
                // La id que se maneja sera la sulla
                idSelected = level.getId();
            }
        }
    }

    /**
     * Este metodo se encargara de realizar todas las operaciones graficas
     * 
     * @param g
     * @param scroly
     */
    public void draw(Graphics g, int scroly) {
        g.setColor(colBox);
        g.fillRect((int) (loc.x - tam.x / 2), (int) (loc.y + scroly - tam.y / 2), (int) tam.x, (int) tam.y);
        FontMetrics fontMetrics = g.getFontMetrics();
        g.setColor(colText);
        Text.drawText(g, level.getNombre(), loc.x - tam.x / 2 + 20, loc.y + scroly + fontMetrics.getHeight() / 3, false,
                new Font("Dialog", Font.PLAIN, 25));
        fontMetrics = g.getFontMetrics();
        Text.drawText(g, level.getFechaCreacion(),
                loc.x + tam.x / 2 - fontMetrics.stringWidth(level.getFechaCreacion()) + 70,
                loc.y + scroly + fontMetrics.getHeight() * 2 / 3, false,
                new Font("Dialog", Font.PLAIN, 14));
    }

    /**
     * Metodo que devolvera la id del nivel seleccionado
     * 
     * @return
     */
    public static Integer getIdSelected() {
        return idSelected;
    }

    /**
     * Metodo que restaurara la id a nulo
     */
    public static void restartIdSelected() {
        idSelected = null;
    }

    /**
     * Mrtodo que cabiara el color de los no seleccionados
     */
    public void notMouse() {
        if (idSelected != level.getId()) {
            colBox = colNotSelect;
        }
    }
}

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
import states.LeverCreatorState;

/**
 * LevelCard
 * This class read one level and represent it
 * 
 * @author JaviLeL
 * @version 1.0
 */
public class LevelCard {
    private PVector loc, tam;
    private LevelDTO level;
    private boolean isSelected;
    private Color colText, colBox, colSelect, colNotSelect;
    private static Integer idSelected = null;

    public LevelCard(float y, LevelDTO level) {
        this.tam = new PVector(Settings.width * 4 / 5 - 10, 100);
        this.loc = new PVector(Settings.width / 2, y * (this.tam.y + 5) + (this.tam.y * 2 / 3 + 5));
        this.level = level;
        // Set some parameters by default
        this.colText = new Color(255, 255, 255);
        this.colNotSelect = Constants.cols[4];
        this.colSelect = Constants.cols[7];
        this.colBox = Constants.cols[4];
        this.isSelected = false;
    }

    public void update(int scroly) {
        if (!(isSelected && idSelected == level.getId())) {
            colBox = colNotSelect;
        }
        if (Mouse.x >= loc.x - tam.x / 2 && Mouse.x <= loc.x + tam.x / 2 &&
                Mouse.y >= loc.y + scroly - tam.y / 2 && Mouse.y <= loc.y + scroly + tam.y / 2) {
            colBox = colSelect;
            if (Mouse.left) {
                Keyboard.key = null;
                isSelected = true;
                idSelected = level.getId();
                LeverCreatorState.angle = 0;
            }
        }
    }

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

    public static Integer getIdSelected() {
        return idSelected;
    }

    public static void restartIdSelected() {
        idSelected = null;
    }

    public void notMouse() {
        if (idSelected != level.getId()) {
            colBox = colNotSelect;
        }
    }
}

package ui;

import java.awt.Color;
import java.awt.Graphics;

import inputs.Keyboard;
import inputs.Mouse;
import raccoon.PVector;
import settings.Settings;

/**
 * BlockCard
 * 
 * @author JaviLeL
 * @version 1.0
 */
public class BlockCard {
    private PVector loc, tam;
    private boolean isSelected;
    private Color colBox, colSelect, colNotSelect;
    private static String assetSeleceted;
    private String asset;

    public BlockCard(float y, String asset) {
        this.tam = new PVector(Settings.cellSize * 5 / 3, Settings.cellSize * 5 / 3);
        this.loc = new PVector(this.tam.x / 2 + this.tam.x / 10, y * (this.tam.y + 5) + (this.tam.y * 2 / 3 + 5));
        // Set some parameters by default
        this.colNotSelect = new Color(51, 51, 51);
        this.colSelect = new Color(100, 100, 100);
        this.colBox = colNotSelect;
        this.isSelected = false;
        this.asset = asset;
        assetSeleceted = null;
    }

    public void update(int scroly) {
        if (!(isSelected && (assetSeleceted.equals(asset)))) {
            // assetSeleceted = asset;
            colBox = colNotSelect;
        }
        if (Mouse.x >= loc.x - tam.x / 2 && Mouse.x <= loc.x + tam.x / 2 &&
                Mouse.y >= loc.y + scroly - tam.y / 2 && Mouse.y <= loc.y + scroly + tam.y / 2) {
            colBox = colSelect;
            if (Mouse.left) {
                Keyboard.key = null;
                isSelected = true;
                assetSeleceted = asset;
            }
        }
        // else if (!(Mouse.x >= loc.x - tam.x / 2 && Mouse.x <= loc.x + tam.x / 2 &&
        // Mouse.y >= loc.y + scroly - tam.y / 2 && Mouse.y <= loc.y + scroly + tam.y /
        // 2) && Mouse.left) {
        // isSelected = false;
        // }
    }

    public void draw(Graphics g, int scroly) {
        g.setColor(colBox);
        g.fillRect((int) (loc.x - tam.x / 2), (int) (loc.y + scroly - tam.y / 2), (int) tam.x, (int) tam.y);
    }

    public static String getAssetSeleceted() {
        return assetSeleceted;
    }

}

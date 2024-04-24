package ui;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import inputs.Keyboard;
import inputs.Mouse;
import raccoon.PVector;
import settings.Settings;
import states.LeverCreatorState;

/**
 * BlockCard
 * 
 * @author JaviLeL
 * @version 1.0
 */
public class BlockCard {
    protected AffineTransform at;
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
                LeverCreatorState.angle = 0;
            }
        }
        // else if (!(Mouse.x >= loc.x - tam.x / 2 && Mouse.x <= loc.x + tam.x / 2 &&
        // Mouse.y >= loc.y + scroly - tam.y / 2 && Mouse.y <= loc.y + scroly + tam.y /
        // 2) && Mouse.left) {
        // isSelected = false;
        // }
    }

    public void draw(Graphics g, int scroly, int angle) {
        g.setColor(colBox);
        g.fillRect((int) (loc.x - tam.x / 2), (int) (loc.y + scroly - tam.y / 2), (int) tam.x, (int) tam.y);
        if (asset != null) {
            Graphics2D g2d = (Graphics2D) g;
            BufferedImage img;
            try {
                img = ImageIO.read(new File(asset));
            } catch (IOException e) {
                try {
                    img = ImageIO.read(new File("assets/error/miss-assets.png"));
                } catch (IOException c) {
                    System.out.println("Error to load img:\n" + c);
                    img = null;
                    System.out.println("Erro to load img:\n" + c);
                    // this.col = new Color(255, 255, 255);
                }
            }
            at = AffineTransform.getTranslateInstance(loc.x - tam.x * 2 / 6, loc.y + scroly - tam.y * 2 / 6);
            if (!(isSelected && (assetSeleceted.equals(asset)))) {
                at.rotate((Math.PI * 0 / 2), Settings.cellSize / 2, Settings.cellSize / 2);
            } else {
                at.rotate((Math.PI * angle / 2), Settings.cellSize / 2, Settings.cellSize / 2);
            }
            g2d.drawImage(img, at, null);
            // g.drawImage(asset, (int) loc.x, (int) loc.y, Main.mainWindow);
        }
    }

    public static String getAssetSeleceted() {
        return assetSeleceted;
    }

}

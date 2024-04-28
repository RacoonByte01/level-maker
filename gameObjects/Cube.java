package gameObjects;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import raccoon.PVector;
import settings.Settings;

public class Cube extends GameObjet {
    public Color col;

    // private BufferedImage asset;
    private String asset;
    protected AffineTransform at;
    protected int angle;

    public Cube(PVector loc, Color col) {
        super(new PVector(loc.x * Settings.cellSize, loc.y * Settings.cellSize));
        // super(loc);
        this.col = col;

    }

    public Cube(PVector loc, String asset) {
        this(loc, asset, 0);
    }

    public Cube(PVector loc, String asset, int angle) {
        super(new PVector(loc.x * Settings.cellSize, loc.y * Settings.cellSize));
        File image = new File(asset);
        // super(loc);
        if (asset != null && image.isFile()) {
            this.asset = asset;
        } else {
            this.asset = "assets/blocks/error/miss-assets.png";
        }

        this.angle = angle;
    }

    @Override
    public void draw(Graphics g) {
        if (col != null) {
            g.setColor(col);
            g.fillRect((int) loc.x, (int) loc.y, Settings.cellSize, Settings.cellSize);
            g.setColor(col.darker());
            g.drawRect((int) loc.x, (int) loc.y, Settings.cellSize, Settings.cellSize);
        } else if (asset != null) {
            Graphics2D g2d = (Graphics2D) g;
            BufferedImage img;
            try {
                img = ImageIO.read(new File(asset));
            } catch (IOException e) {
                System.out.println("Error to load img:\n" + e);
                img = null;
                System.out.println("Erro to load img:\n" + e);
                this.col = new Color(255, 255, 255);
            }
            at = AffineTransform.getTranslateInstance(loc.x, loc.y);
            at.rotate((Math.PI * angle / 2), Settings.cellSize / 2, Settings.cellSize / 2);
            g2d.drawImage(img, at, null);
            // g.drawImage(asset, (int) loc.x, (int) loc.y, Main.mainWindow);
        }
    }

    protected boolean isColision(Cube otherCube) {
        if (((loc.x <= otherCube.loc.x && loc.x + Settings.cellSize >= otherCube.loc.x) ||
                (otherCube.loc.x <= loc.x && otherCube.loc.x + Settings.cellSize >= loc.x)) &&
                ((loc.y <= otherCube.loc.y) && (loc.y + Settings.cellSize >= otherCube.loc.y) ||
                        (otherCube.loc.y <= loc.y && otherCube.loc.y + Settings.cellSize >= loc.y))) {

            return true;
        } else {
            return false;
        }
    }

    @Override
    public void update() {
    };

    /* Beta testing */
    public void setColor(Color newColor) {
        col = newColor;
    }

    public boolean isLoc(PVector otherLoc) {
        return loc.x == otherLoc.x && loc.y == otherLoc.y;
    }
}

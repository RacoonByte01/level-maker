package gameObjects;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.File;

import javax.imageio.ImageIO;

import raccoon.PVector;
import settings.Settings;

public class Cube extends GameObjet {
    public Color col;
    private BufferedImage asset;
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
        // super(loc);
        try {
            if (asset != null) {
                this.asset = ImageIO.read(new File(asset));
            } else {
                this.asset = ImageIO.read(new File("assets/a.png"));
            }
        } catch (Exception ex) {
            System.out.println("error - load image");
            // handle exception...
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
            at = AffineTransform.getTranslateInstance(loc.x, loc.y);
            at.rotate((Math.PI * angle / 2), Settings.cellSize / 2, Settings.cellSize / 2);
            g2d.drawImage(asset, at, null);
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

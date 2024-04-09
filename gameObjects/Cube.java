package gameObjects;

import java.awt.Color;
import java.awt.Graphics;

import raccoon.PVector;
import settings.Settings;

public class Cube extends GameObjet {
    public Color col;

    public Cube(PVector loc, Color col) {
        super(new PVector(loc.x * Settings.cellSize, loc.y * Settings.cellSize));
        // super(loc);
        this.col = col;
    }

    @Override
    public void draw(Graphics g) {
        g.setColor(col);
        g.fillRect((int) loc.x, (int) loc.y, Settings.cellSize, Settings.cellSize);
        g.setColor(col.darker());
        g.drawRect((int) loc.x, (int) loc.y, Settings.cellSize, Settings.cellSize);
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
}

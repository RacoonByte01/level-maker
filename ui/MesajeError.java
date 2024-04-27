package ui;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

import inputs.Mouse;
import raccoon.PVector;
import settings.Constants;

public class MesajeError {
    private PVector loc, tam;
    private int timeVisible;
    private String text;

    public MesajeError(float x, float y, float tamx, float tamy) {
        this.loc = new PVector(x, y);
        this.tam = new PVector(tamx, tamy);
        this.text = "";
    }

    public void darw(Graphics g) {
        if (timeVisible != 0) {
            timeVisible--;
            g.setColor(Constants.cols[23]);
            g.fillRect((int) (loc.x - tam.x / 2), (int) (loc.y - tam.y / 2), (int) tam.x, (int) tam.y);
            g.setFont(new Font("Dialog", Font.PLAIN, 20));
            g.setColor(new Color(255, 255, 255));
            Text.drawText(g, text, loc.x, loc.y - 5, true,
                    new Font("Dialog", Font.PLAIN, 20));
        }
    }

    public void update() {
        if (Mouse.x >= loc.x - tam.x / 2 && Mouse.x <= loc.x + tam.x / 2 &&
                Mouse.y >= loc.y - tam.y / 2 && Mouse.y <= loc.y + tam.y / 2) {
            if (Mouse.left) {
                timeVisible = 0;
            }
        }
    }

    public void setText(String text) {
        this.text = text;
    }

    public void setVisibleTime(int time) {
        this.timeVisible = time;
    }
}

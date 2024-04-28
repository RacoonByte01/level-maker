package ui;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import inputs.Mouse;
import settings.Settings;

public class ImgButton extends Button {
    protected AffineTransform at;
    BufferedImage img;

    public ImgButton(int x, int y, String asset, Acttion acttion) {
        super(x, y, asset, acttion);
        try {
            img = ImageIO.read(new File(text));
        } catch (IOException e) {
            try {
                img = ImageIO.read(new File("assets/error/miss-assets.png"));
            } catch (IOException c) {
                System.out.println("Error to load img:\n" + c);
                img = null;
                System.out.println("Erro to load img:\n" + c);
            }
        }
    }

    @Override
    public void update() {
        if (Mouse.x >= loc.x - img.getWidth() / 2
                && Mouse.x <= loc.x - img.getWidth() / 2 + img.getWidth() &&
                Mouse.y >= loc.y - img.getHeight() / 2
                && Mouse.y <= loc.y - img.getHeight() / 2 + img.getHeight()) {
            col = new Color(255, 255, 255, 50);
            if (Mouse.left && Mouse.mousePressed) {
                buttonPressed = true;
            } else {
                buttonPressed = false;
            }
        } else {
            col = null;
        }

        if (buttonPressed) {
            acttion.accionARealizar();
            Mouse.mousePressed = false;
            buttonPressed = false;
        }
    }

    @Override
    public void draw(Graphics g) {
        if (text != null) {
            Graphics2D g2d = (Graphics2D) g;
            at = AffineTransform.getTranslateInstance(loc.x - img.getWidth() / 2, loc.y - img.getHeight() / 2);
            at.rotate((Math.PI * 0 / 2), Settings.cellSize / 2, Settings.cellSize / 2);
            g2d.drawImage(img, at, null);
        }
        if (col != null) {
            g.setColor(col);
            g.fillRect((int) loc.x - img.getWidth() / 2, (int) loc.y - img.getHeight() / 2, img.getWidth(),
                    img.getHeight());
        } else {
            g.setColor(col);
        }
    }
}

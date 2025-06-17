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

/**
 * class ImgButton
 * Clase que probee un boton que permite cargar una imagen
 * 
 * @author Javi.LeL
 * @version 1.0
 */
public class ImgButton extends Button {
    protected AffineTransform at;
    BufferedImage img;

    /**
     * Constructor basico para la creacion del boton
     * 
     * @param x
     * @param y
     * @param asset
     * @param acttion
     */
    public ImgButton(int x, int y, String asset, Acttion acttion) {
        super(x, y, asset, acttion);
        try {
            // Si puede cargar la imagen
            img = ImageIO.read(new File(text));
        } catch (IOException e) {
            // Si no puede cargarla
            try {
                // Cargara la imagen de error
                img = ImageIO.read(new File("assets/error/miss-assets.png"));
            } catch (IOException c) {
                // Si no dara un error
                System.out.println("Error to load img:\n" + c);
                // Y la imagen sera nula
                img = null;
            }
        }
    }

    /**
     * Metodo que realizara todas las operaciones relacionadas con las matematicas
     */
    @Override
    public void update() {
        // Si el cursor esta dentro del boton
        if (Mouse.x >= loc.x - img.getWidth() / 2
                && Mouse.x <= loc.x - img.getWidth() / 2 + img.getWidth() &&
                Mouse.y >= loc.y - img.getHeight() / 2
                && Mouse.y <= loc.y - img.getHeight() / 2 + img.getHeight()) {
            // Pondra un color mas vivo
            col = new Color(255, 255, 255, 50);
            if (Mouse.left && Mouse.mousePressed) { // Y es presionado
                // La varible buttonPressed cambiara a true
                buttonPressed = true;
            } else {
                // Si no sera false
                buttonPressed = false;
            }
        } else { // Si el cursor no esta dentro de la imagen
            // Su color sera nulo
            col = null;
        }
        if (buttonPressed) { // Si se presiono el boton
            // Se realiza la acion
            acttion.accionARealizar();
            // Y se reinican los valores del raton
            Mouse.mousePressed = false;
            // Y los del boton
            buttonPressed = false;
        }
    }

    /**
     * Este metodo se encargara de realizar todas las operaciones graficas
     * 
     * @param isEnable
     */
    @Override
    public void draw(Graphics g) {
        if (text != null) { // Si el texro no es nulo
            // Representara la imagen sin ningun angulo de inclinacion
            Graphics2D g2d = (Graphics2D) g;
            at = AffineTransform.getTranslateInstance(loc.x - img.getWidth() / 2, loc.y - img.getHeight() / 2);
            at.rotate((Math.PI * 0 / 2), Settings.cellSize / 2, Settings.cellSize / 2);
            g2d.drawImage(img, at, null);
        }
        if (col != null) { // Si su color no es null
            // Dibujara un cuadrado opaco sobre la imagen
            g.setColor(col);
            g.fillRect((int) loc.x - img.getWidth() / 2, (int) loc.y - img.getHeight() / 2, img.getWidth(),
                    img.getHeight());
        } else {
            // Si no se pondra el color pordefecto
            g.setColor(col);
        }
    }
}

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
import settings.Constants;
import settings.Settings;
import states.LeverCreatorState;

/**
 * class BlockCard
 * Classe que probee un sistema de visualizacion de los bloques del estado
 * LeverCreatorState
 * 
 * @author Javi.LeL
 * @version 1.0
 */
public class BlockCard {
    protected AffineTransform at;
    private PVector loc, tam;
    private boolean isSelected;
    private Color colBox, colSelect, colNotSelect;
    private static String assetSeleceted;
    private String asset;

    /**
     * Constructor que dependiendo de cuando se crea esta carta esta estara mas
     * arriba o abajo
     * 
     * @param y
     * @param asset
     */
    public BlockCard(float y, String asset) {
        this.tam = new PVector(Settings.cellSize * 5 / 3, Settings.cellSize * 5 / 3);
        this.loc = new PVector(this.tam.x / 2 + this.tam.x / 10, y * (this.tam.y + 5) + (this.tam.y * 2 / 3 + 5));
        this.colNotSelect = Constants.cols[2];
        this.colSelect = Constants.cols[4];
        this.colBox = colNotSelect;
        this.isSelected = false;
        this.asset = asset;
        assetSeleceted = null;
    }

    /**
     * Metodo que realizara todas las operaciones relacionadas con las matematicas
     * 
     * @param scroly
     */
    public void update(int scroly) {
        // Si no esta seleccionado
        if (!(isSelected && (assetSeleceted.equals(asset)))) {
            // Se cambiara el color a no seleccionado
            colBox = colNotSelect;
        }
        // Si el raton esta sobre la carta
        if (Mouse.x >= loc.x - tam.x / 2 && Mouse.x <= loc.x + tam.x / 2 &&
                Mouse.y >= loc.y + scroly - tam.y / 2 && Mouse.y <= loc.y + scroly + tam.y / 2) {
            // Se cabia el color por el color seleciondo
            colBox = colSelect;
            if (Mouse.left) { // Si se presiona sobre ella
                // Se reiniciaran unos valores
                Keyboard.key = null;
                // Se pondra como seleccionda
                isSelected = true;
                // El assete que manejara sera el sullo
                assetSeleceted = asset;
                // Se reiniciara el angulo
                LeverCreatorState.angle = 0;
            }
        }
    }

    /**
     * Este metodo se encargara de realizar todas las operaciones graficas
     * 
     * @param g
     * @param scroly
     * @param angle
     */
    public void draw(Graphics g, int scroly, int angle) {
        // Se pintara un cuadrado donde se pintara la imagen
        g.setColor(colBox);
        g.fillRect((int) (loc.x - tam.x / 2), (int) (loc.y + scroly - tam.y / 2), (int) tam.x, (int) tam.y);
        if (asset != null) { // Si el asset elegido no es nulo
            // Se creara un Graphics2D para poder cargar imagenes con BufferedImage
            Graphics2D g2d = (Graphics2D) g;
            BufferedImage img;
            try {
                // Si se puede cargar la imagen
                img = ImageIO.read(new File(asset));
            } catch (IOException e) {
                // Si no se puede cargar la imagen
                try {
                    // Se pondra la imagen de fallo de carga
                    img = ImageIO.read(new File("assets/error/miss-assets.png"));
                } catch (IOException c) {
                    // Si esta tambien da un error
                    System.out.println("Error to load img:\n" + c);
                    // La imagen sera null
                    img = null;
                    System.out.println("Error to load img:\n" + c);
                }
            }
            // Se representa la imagen
            at = AffineTransform.getTranslateInstance(loc.x - tam.x * 2 / 6, loc.y + scroly - tam.y * 2 / 6);
            if (!(isSelected && (assetSeleceted.equals(asset)))) { // Si no es la seleccionada
                // No se le aplicara el angulo
                at.rotate((Math.PI * 0 / 2), Settings.cellSize / 2, Settings.cellSize / 2);
            } else { // Si es la seleccionada
                // Se le aplicara el angulo
                at.rotate((Math.PI * angle / 2), Settings.cellSize / 2, Settings.cellSize / 2);
            }
            // Se dibujara el resultado final
            g2d.drawImage(img, at, null);
        }
    }

    /**
     * Devolvera el asset seleccionado
     * 
     * @return
     */
    public static String getAssetSeleceted() {
        return assetSeleceted;
    }
}

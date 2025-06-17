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

/**
 * class Cube
 * Extiende de GameObjet esta clase se encarga de gestionar los cubos del juego
 */
public class Cube extends GameObjet {
    public Color col;

    private String asset;
    protected AffineTransform at;
    protected int angle;

    /**
     * Constructor un cubo con un color solido
     * 
     * @param loc
     * @param col
     */
    public Cube(PVector loc, Color col) {
        super(new PVector(loc.x * Settings.cellSize, loc.y * Settings.cellSize));
        this.col = col;
    }

    /**
     * Constructor para crear un cubo con una imagen
     * 
     * @param loc
     * @param asset
     */
    public Cube(PVector loc, String asset) {
        this(loc, asset, 0);
    }

    /**
     * Constructor para crear un cubo con una imagen con angulo
     * 
     * @param loc
     * @param asset
     */
    public Cube(PVector loc, String asset, int angle) {
        super(new PVector(loc.x * Settings.cellSize, loc.y * Settings.cellSize));
        File image = new File(asset);
        if (asset != null && image.isFile()) { // Si la imagen existe
            // Se usara el asset
            this.asset = asset;
        } else {
            // Si no existe el asset sera el no encotrado
            this.asset = "assets/blocks/error/miss-assets.png";
        }
        this.angle = angle;
    }

    /**
     * Este metodo se encarga de dibujar un cuadrado
     * 
     * @param g
     */
    @Override
    public void draw(Graphics g) {
        if (col != null) { // Si el color no es nulo
            // Este creara un cubo en esa posicion con su color correspondiente
            // Para ello se pone el color en el aparatado de graficos
            g.setColor(col);
            // Se crea un cuadrado en la posicion escogida y con su espacio correspondiente
            g.fillRect((int) loc.x, (int) loc.y, Settings.cellSize, Settings.cellSize);
            // Se pone el color en el aparatado de graficos pero mas oscuro
            g.setColor(col.darker());
            // Y se crea un cuadrado con las mismas dimensiones pero solo con el perimetro
            g.drawRect((int) loc.x, (int) loc.y, Settings.cellSize, Settings.cellSize);
        } else if (asset != null) { // Si el asset no es nulo
            // Se creara un objeto grafico de tipo 2D el cual podra cargar buffers de
            // imagenes
            Graphics2D g2d = (Graphics2D) g;
            // Y un objeto de tipo imagen buffer para poder cargar la imagen
            BufferedImage img;
            try {
                // Si se puede leer el archivo se cargara
                img = ImageIO.read(new File(asset));
            } catch (IOException e) {
                // Si no dara un error al cargar una imagen
                System.out.println("Error to load img:\n" + e);
                // Pondra la imagen en nulo para evitar errores en las poseteriores cargas de
                // datos
                img = null;
                System.out.println("Erro to load img:\n" + e);
                // Y se auto pondra un color por defecto en este caso es el blanco
                this.col = new Color(255, 255, 255);
            }
            // Si todo ha ido bien
            // Se cargar la imagen en la posicion especificada
            at = AffineTransform.getTranslateInstance(loc.x, loc.y);
            /*
             * Y se rotar 90 grados por su angulo es decir:
             * 
             * angulo=0 poseera 0 grados
             * angulo=1 poseera 90 grados
             * angulo=2 poseera 128 grados
             * angulo=3 poseera 270 grados
             */
            at.rotate((Math.PI * angle / 2), Settings.cellSize / 2, Settings.cellSize / 2);
            // Y por ultimo se cargara la imagen
            g2d.drawImage(img, at, null);
        }
    }

    /**
     * Este metodo calculara si hay algun tipo de colision entre un cubo y otro
     * 
     * @param otherCube
     * @return true si hay colision false si no la hay
     */
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

    /**
     * Metodo encargado de realizar todas las cuentas matematicas
     */
    @Override
    public void update() {
        // El cubo estandar no hara nada al actualizarse
    }

    /**
     * Calcula si un vector equvale al vector de localizacion del propio cubo
     * 
     * @param otherLoc
     * @return
     */
    public boolean isLoc(PVector otherLoc) {
        return loc.x == otherLoc.x && loc.y == otherLoc.y;
    }
}

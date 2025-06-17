package gameObjects;

import java.awt.Graphics;
import java.io.Serializable;

import raccoon.PVector;

/**
 * class GameObjet
 * De esta clase extenderan todos los elementos de tipo juego
 * 
 * @author Javi.LeL
 * @version 1.0
 */
public abstract class GameObjet implements Serializable {
    protected PVector loc;

    /**
     * Constructor con la localizacion inicial
     * 
     * @param loc
     */
    public GameObjet(PVector loc) {
        this.loc = loc;
    }

    /**
     * Este metodo se usara para hacer todos los calculos relacionados con los
     * graficos
     * 
     * @param g
     */
    public abstract void draw(Graphics g);

    /**
     * Este metodo realizara todos los calculos relacionados con las matematicas
     */
    public abstract void update();

    public PVector getLoc() {
        return loc;
    }
}

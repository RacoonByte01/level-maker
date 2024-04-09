package gameObjects;

import java.awt.Graphics;

import raccoon.PVector;

/**
 * Class GameObjet
 * This class will be the parent of all game objects this will be use to update
 * and draw all objets
 * 
 * @author Javi.LeL
 * @version 1.0
 */
public abstract class GameObjet {
    /* This is the position of the objet will be start */
    protected PVector loc;

    /**
     * This constructor provides a initial localitation
     * 
     * @param loc
     */
    public GameObjet(PVector loc) {
        this.loc = loc;
    }

    /**
     * This method will be use to calculate all related of Graphics
     * 
     * @param g
     */
    public abstract void draw(Graphics g);

    /**
     * This method will be use to calculate all related of maths
     */
    public abstract void update();
}

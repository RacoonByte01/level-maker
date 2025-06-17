package gameObjects;

import java.awt.Color;

import inputs.Keyboard;
import raccoon.PVector;

/**
 * class Player
 * Esta clase proveera de un jugador al juego
 * 
 * @author JaviLeL
 * @version 1.0
 */
public class Player extends DinamicCube {
    /**
     * Constructo para crear un jugador con un color especifico
     * 
     * @param loc
     * @param col
     */
    public Player(PVector loc, Color col) {
        super(loc, col, 20);
        // Donde la gravedad sera y
        this.acc = new PVector(0, 4f);

    }

    /**
     * Constructor para crea un jugador con una imagen especifica
     * 
     * @param loc
     * @param asset
     */
    public Player(PVector loc, String asset) {
        super(loc, asset);
        // Donde la gravedad sera y
        this.acc = new PVector(0, 4f);

    }

    /**
     * Este metodo movera al jugador
     */
    public void controls() {
        // Se reinicia la velocidad
        vel.x = 0;
        if (Keyboard.left) {
            vel.x = -15;
        }
        if (Keyboard.right) {
            vel.x = 15;
        }
        if (Keyboard.jump && canJump && !(vel.y > 0)) {
            vel.y = -31;
            canJump = false;
        }
    }

    /**
     * Este metodo devolvera la posicion del jugador para usarlo como camara
     * 
     * @return
     */
    public PVector getLoc() {
        return loc;
    }
}

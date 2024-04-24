package gameObjects;

import java.awt.Color;

import inputs.Keyboard;
import raccoon.PVector;

/**
 * Class Player
 * 
 * @author JaviLeL
 * @version 1.0
 */
public class Player extends DinamicCube {

    public Player(PVector loc, Color col) {
        super(loc, col, 30);
        // Where g = y
        this.acc = new PVector(0, 4f);

    }

    public Player(PVector loc, String asset) {
        super(loc, asset);
        // Where g = y
        this.acc = new PVector(0, 4f);

    }

    /**
     * This function can move the player if the user press any of the keys
     */
    public void controls() {
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
     * This function return the loc off Player to use it to move the "cam"
     * 
     * @return
     */
    public PVector getLoc() {
        return loc;
    }
}

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
        this.acc = new PVector(0, .5f);

    }

    /**
     * This function can move the player if the user press any of the keys
     */
    public void controls() {
        vel.x = 0;

        if (Keyboard.left) {
            vel.x = -10;
        }
        if (Keyboard.right) {
            vel.x = 10;
        }
        if (Keyboard.jump && canJump) {
            vel.y = -10;
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

package gameObjects;

import java.awt.Color;

import raccoon.PVector;
import states.GameState;

public class DinamicCube extends Cube {
    public PVector vel, acc;
    private Integer velLimit;

    public DinamicCube(PVector loc, Color col, Integer velLimit) {
        super(loc, col);
        vel = new PVector(0, 0);
        acc = new PVector(0, 0);
        this.velLimit = velLimit;
    }

    public DinamicCube(PVector loc, Color col) {
        this(loc, col, null);
    }

    @Override
    public void update() {
        vel.add(acc);
        if (velLimit != null) {
            vel.limit(velLimit);
        }
        loc.add(vel);
        boolean colision = false;
        for (Cube cube : GameState.grid) {
            if (this != cube) {
                if (this.isColision(cube)) {
                    colision = true;
                    cube.setColor(new Color(0, 255, 0));
                } else {
                    cube.setColor(new Color(255, 0, 0));
                }
            }
        }
        if (colision) {
            setColor(new Color(255, 255, 0));
            System.out.println("Si Colision :D");
        } else {
            setColor(new Color(255, 255, 255));
            System.out.println("No Colision D:");
        }
    }

    /* beta test */
    public void setLoc(PVector newLoc) {
        loc = newLoc;
    }
}

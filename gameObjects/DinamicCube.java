package gameObjects;

import java.awt.Color;

import raccoon.PVector;
import settings.Settings;
import states.GameState;

/**
 * Class DinamicCube
 * 
 * @author JaviLeL
 * @version 1.0
 */
public class DinamicCube extends Cube {
    public PVector vel, acc;
    private Integer velLimit;
    protected boolean canJump;

    public DinamicCube(PVector loc, Color col, Integer velLimit) {
        super(loc, col);
        vel = new PVector(0, 0);
        acc = new PVector(0, 0);
        this.velLimit = velLimit;
        canJump = false;
    }

    public DinamicCube(PVector loc, Color col) {
        this(loc, col, null);
    }

    @Override
    public void update() {
        vel.add(acc);
        if (velLimit != null) {
            vel.limit(velLimit);
            if (vel.y >= velLimit) {
                vel.y = velLimit;
            }
        }
        loc.add(vel);
        fisicsColision();
    }

    /**
     * This function return UP DOWN RIGTH LEFT
     * If the colision is produce by any of this directions
     * 
     * @param otherCube
     * @return result = UP DOWN RIGTH LEFT
     */
    private String typeColision(Cube otherCube) {
        String result = null;
        if (vel.x > 0) {
            // El 14 es la velocidad -1
            if ((this.loc.y <= otherCube.loc.y) && (this.vel.y > 0)
                    && !((int) this.loc.x + Settings.cellSize - 14 == (int) otherCube.loc.x)) {
                result = "DOWN";
            } else if ((this.loc.y > otherCube.loc.y) && (this.vel.y < 0) && !(this.loc.x < otherCube.loc.x)) {
                result = "UP";
            } else if ((this.loc.x + Settings.cellSize >= otherCube.loc.x)
                    && !(this.loc.y + Settings.cellSize - 10 < otherCube.loc.y)) {
                result = "RIGHT";
            }
        } else if (vel.x < 0) {
            if ((this.loc.y <= otherCube.loc.y) && (this.vel.y > 0)

                    && !((int) this.loc.x == (int) otherCube.loc.x + Settings.cellSize - 14)) {
                result = "DOWN";
            } else if ((this.loc.y > otherCube.loc.y) && (this.vel.y < 0) && !(this.loc.x > otherCube.loc.x)) {
                result = "UP";
            } else if ((this.loc.x <= otherCube.loc.x + Settings.cellSize)
                    && !(this.loc.y + Settings.cellSize - 10 < otherCube.loc.y)) {
                result = "LEFT";
            }
        } else {
            if ((this.loc.y >= otherCube.loc.y) && (this.vel.y < 0)) {
                result = "UP";
            } else if (((this.loc.y <= otherCube.loc.y) && (this.vel.y > 0))) {
                result = "DOWN";
            }
        }
        return result;
    }

    protected void fisicsColision() {
        for (Cube cube : GameState.grid) {
            if (this != cube) {
                if (this.isColision(cube)) {
                    if (typeColision(cube) != null) {
                        switch (typeColision(cube)) {
                            case "DOWN":
                                vel.y = 0;
                                loc.y = cube.loc.y - Settings.cellSize - 1;
                                canJump = true;
                                break;
                            case "RIGHT":
                                vel.x = 0;
                                acc.x = 0;
                                loc.x = cube.loc.x - Settings.cellSize - 1;
                                break;
                            case "LEFT":
                                vel.x = 0;
                                acc.x = 0;
                                loc.x = cube.loc.x + Settings.cellSize + 1;
                                break;
                            case "UP":
                                vel.y = 0;
                                loc.y = cube.loc.y + Settings.cellSize + 1;
                                break;
                        }
                    }
                }
            }
        }
    }

}

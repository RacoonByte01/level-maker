package gameObjects;

import java.awt.Color;

import raccoon.PVector;
import settings.Settings;
import states.GameState;

/**
 * class DinamicCube
 * Esta clase sera la encargada de crear cubos con la capacidad de movimento
 * 
 * @author Javi.LeL
 * @version 1.0
 */
public class DinamicCube extends Cube {
    public PVector vel, acc;
    private Integer velLimit;
    protected boolean canJump;

    /**
     * Constructor para crear un cubo con un color y con un limite de velocidad
     * 
     * @param loc
     * @param col
     * @param velLimit
     */
    public DinamicCube(PVector loc, Color col, Integer velLimit) {
        super(loc, col);
        vel = new PVector(0, 0);
        acc = new PVector(0, 0);
        this.velLimit = velLimit;
        canJump = false;
    }

    /**
     * Constructor para crear un cubo con un color y sin un limite de velocidad
     * 
     * @param loc
     * @param col
     */
    public DinamicCube(PVector loc, Color col) {
        this(loc, col, null);
    }

    /**
     * Constructor para crear un cubo con una imagen y sin un limite de velocidad
     * 
     * @param loc
     * @param asset
     */
    public DinamicCube(PVector loc, String asset) {
        this(loc, asset, null);
    }

    /**
     * Constructor para crear un cubo con una imagen y con un limite de velocidad
     * 
     * @param loc
     * @param asset
     * @param velLimit
     */
    public DinamicCube(PVector loc, String asset, Integer velLimit) {
        super(loc, asset);
        vel = new PVector(0, 0);
        acc = new PVector(0, 0);
        this.velLimit = velLimit;
        canJump = false;
    }

    /**
     * Este metodo se encarga de realizar todas las operaciones matematicas
     * En este caso realiza los calculos para aplicar velocidades y aceleraciones
     */
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
     * Esta funcion devuelve UP DOWN RIGTH LEFT
     * dependiendo del tipo de colision detectada
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

    /**
     * Este metodo se encarga de evitar que el cubo atraviese un bloque estatico
     */
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

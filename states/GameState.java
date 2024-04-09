package states;

import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.List;

import gameObjects.Cube;
import gameObjects.DinamicCube;
import inputs.Mouse;
import raccoon.PVector;
import settings.Settings;

/**
 * Class GameState
 * This class will manager all the fisics of the diferents elements of the game
 * 
 * @author Javi.LeL
 * @version 1.0
 */
public class GameState extends State {
    /* This hashmap will save all the cels with respect level */
    public static List<Cube> grid;

    /**
     * Principal constructor of the game State
     */
    public GameState(List<Cube> grid) {
        /*
         * When you create a level you dont have information so for this not crash
         * we put the information necesary to it work
         */
        if (grid != null) {
            GameState.grid = grid;
        } else {
            GameState.grid = new ArrayList<>();
        }
        /* Work in progress */
        GameState.grid.add(new DinamicCube(new PVector(0, 0), new Color(255, 255, 255)));
        for (int i = 0; i < 10; i++) {
            GameState.grid.add(new Cube(new PVector(1 + i * 2, 5), new Color(255, 0, 0)));
        }
    }

    @Override
    public void update() {
        for (Cube cube : grid) {
            cube.update();
        }
    }

    @Override
    public void draw(Graphics g) {
        g.setColor(new Color(0));
        g.fillRect(0, 0, Settings.width, Settings.height);
        for (Cube cube : grid) {
            cube.draw(g);
        }
        DinamicCube player = (DinamicCube) grid.get(0);
        player.setLoc(new PVector(Mouse.x, Mouse.y));
    }

}

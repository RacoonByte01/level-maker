package states;

import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.List;

import gameObjects.Cube;
import gameObjects.Player;
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
        GameState.grid.add(
                new Player(
                        new PVector(Settings.width / 2 / Settings.cellSize,
                                Settings.height / 2 / Settings.cellSize - 1),
                        new Color(255, 255, 255)));
        for (int i = 0; i < Settings.width / Settings.cellSize; i++) {
            GameState.grid.add(new Cube(new PVector(i, Settings.height / 2 / Settings.cellSize), new Color(255, 0, 0)));
        }
        // for (int i = 0; i < Settings.width / Settings.cellSize; i++) {
        // GameState.grid
        // .add(new Cube(new PVector(i, Settings.height / 2 / Settings.cellSize - 3),
        // new Color(255, 0, 0)));
        // }
        GameState.grid.add(new Cube(
                new PVector(Settings.width / 2 / Settings.cellSize + 5, Settings.height / 2 / Settings.cellSize - 1),
                new Color(255, 0, 0)));
        GameState.grid.add(new Cube(
                new PVector(Settings.width / 2 / Settings.cellSize + 5, Settings.height / 2 / Settings.cellSize - 2),
                new Color(255, 0, 0)));
        GameState.grid.add(new Cube(
                new PVector(Settings.width / 2 / Settings.cellSize + 5, Settings.height / 2 / Settings.cellSize - 3),
                new Color(255, 0, 0)));
        GameState.grid.add(new Cube(
                new PVector(Settings.width / 2 / Settings.cellSize + 5, Settings.height / 2 / Settings.cellSize - 4),
                new Color(255, 0, 0)));

        GameState.grid.add(new Cube(
                new PVector(Settings.width / 2 / Settings.cellSize - 5, Settings.height / 2 / Settings.cellSize - 1),
                new Color(255, 0, 0)));
        GameState.grid.add(new Cube(
                new PVector(Settings.width / 2 / Settings.cellSize - 5, Settings.height / 2 / Settings.cellSize - 2),
                new Color(255, 0, 0)));
        GameState.grid.add(new Cube(
                new PVector(Settings.width / 2 / Settings.cellSize - 5, Settings.height / 2 / Settings.cellSize - 3),
                new Color(255, 0, 0)));
        GameState.grid.add(new Cube(
                new PVector(Settings.width / 2 / Settings.cellSize - 5, Settings.height / 2 / Settings.cellSize - 4),
                new Color(255, 0, 0)));
        // for (int x = 1; x < 10; x++) {
        // for (int y = 1; y < x; y++) {
        // GameState.grid.add(
        // new Cube(new PVector(x + Settings.width / 2 / Settings.cellSize,
        // Settings.height / 2 / Settings.cellSize - y), new Color(255, 0, 0)));
        // }
        // }

    }

    @Override
    public void update() {
        Player player = (Player) grid.get(0);
        player.controls();
        for (Cube cube : grid) {
            cube.update();
        }
    }

    @Override
    public void draw(Graphics g) {
        g.setColor(new Color(0));
        g.fillRect(0, 0, Settings.width, Settings.height);
        Player player = (Player) grid.get(0);
        g.translate((int) -player.getLoc().x + Settings.width / 2, (int) -player.getLoc().y + Settings.height / 2);
        for (Cube cube : grid) {
            cube.draw(g);
        }
    }

}

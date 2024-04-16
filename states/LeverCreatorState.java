package states;

import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.List;

import inputs.Keyboard;
import inputs.Mouse;
import raccoon.PVector;
import settings.Settings;
import gameObjects.Cube;
import gameObjects.Player;

/**
 * class LeverCreatorState
 * 
 * @author JaviLeL
 * @version 1.0
 */
public class LeverCreatorState extends State {
    private PVector loc, lastClick, locCam;
    private boolean isPressed;
    public List<Cube> grid;
    public int angel;

    public LeverCreatorState(List<Cube> grid) {
        this.loc = new PVector(Settings.width / 2, Settings.height / 2 - Settings.cellSize * 2);
        this.locCam = new PVector(0, 0);
        this.isPressed = false;
        if (grid != null) {
            this.grid = grid;
        } else {
            this.grid = new ArrayList<>();
            // Spawn of player
            this.grid.add(new Player(new PVector(0, 0), new Color(255, 255, 255)));
        }
        Keyboard.key = null;
        this.angel = 0;
    }

    @Override
    public void update() {
        camera();
        if (Mouse.mousePressed) {
            PVector press = new PVector((int) (Mouse.x / Settings.cellSize) - (int) (locCam.x / Settings.cellSize),
                    (int) (Mouse.y / Settings.cellSize) - (int) (locCam.y / Settings.cellSize));
            if (Mouse.left) {
                boolean wasLoc = false;
                for (Cube cube : grid) {
                    if (cube.isLoc(new PVector(press.x * Settings.cellSize, press.y * Settings.cellSize))) {
                        wasLoc = true;
                    }
                }
                if (!wasLoc) {
                    // grid.add(new Cube(press, new Color(255, 0, 0)));
                    grid.add(new Cube(press, "assets/grass/grass-corner.png", angel));
                }
            } else if (Mouse.right && !(press.x == 0 && press.y == 0)) {
                for (int i = 0; i < grid.size(); i++) {
                    if (grid.get(i).isLoc(new PVector(press.x * Settings.cellSize, press.y * Settings.cellSize))) {
                        grid.remove(i);
                    }
                }
            }
        }
        /* Work in progress */
        if (Keyboard.key != null && Keyboard.key == ' ') {
            Keyboard.key = null;
            State.setActualState(new GameState(grid));
        } else if (Keyboard.key != null && Character.toLowerCase(Keyboard.key) == 'r') {
            Keyboard.key = null;
            angel = (angel + 1) % 4;
        }
    }

    @Override
    public void draw(Graphics g) {
        g.setColor(new Color(51, 51, 65));
        g.fillRect(0, 0, Settings.width, Settings.height);
        g.translate((int) locCam.x, (int) locCam.y);
        for (Cube cube : grid) {
            cube.draw(g);
        }
        // g.setColor(new Color(255, 255, 255));
        // g.fillRect(0, 0, 32, 32);
    }

    protected void camera() {
        if (Mouse.mousePressed && Mouse.center) {
            if (!isPressed) {
                lastClick = new PVector(Mouse.x, Mouse.y);
                isPressed = true;
            }
        } else {
            lastClick = null;
            isPressed = false;
        }
        if (lastClick != null) {
            loc.add(new PVector(Mouse.x - lastClick.x, Mouse.y - lastClick.y));
            lastClick = new PVector(Mouse.x, Mouse.y);
        }
        locCam = new PVector((int) (loc.x / Settings.cellSize) * Settings.cellSize,
                (int) (loc.y / Settings.cellSize) * Settings.cellSize);
    }
}

package states;

import java.awt.Graphics;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import db.crud.LevelCRUD;
import db.dto.DTOUtils;
import db.dto.LevelDTO;
import inputs.Keyboard;
import inputs.Mouse;
import raccoon.PVector;
import settings.Constants;
import settings.Settings;
import ui.BlockCard;
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
    public LevelDTO level;
    private boolean isPressed;
    public List<Cube> grid;
    public static int angle;
    private BlockCard[] blockCards;
    private int scroll;

    @SuppressWarnings("unchecked")
    public LeverCreatorState(LevelDTO level) {
        this.loc = new PVector(Settings.width / 2, Settings.height / 2 - Settings.cellSize * 2);
        this.locCam = new PVector(0, 0);
        this.isPressed = false;
        this.level = level;
        if (level.getData() != null) {
            try {
                this.grid = (List<Cube>) DTOUtils.stringToObj(level.getData());
            } catch (ClassNotFoundException | IOException e) {
                System.out.println("Error al cargar la info:\n" + e);
            }
        } else {
            this.grid = new ArrayList<>();
            // Spawn of player
            // this.grid.add(new Player(new PVector(0, 0), new Color(255, 255, 255)));
            this.grid.add(new Player(new PVector(0, 0), "assets/player/player.png"));
        }
        Keyboard.key = null;
        LeverCreatorState.angle = 0;
        this.blockCards = new BlockCard[6];
        // for (int i = 0; i < blockCards.length; i++) {
        // blockCards[i] = new BlockCard(i, i + "");
        // }
        blockCards[0] = new BlockCard(0, "assets/dirt/dirt.png");
        blockCards[1] = new BlockCard(1, "assets/grass/grass-all.png");
        blockCards[2] = new BlockCard(2, "assets/grass/grass-up.png");
        blockCards[3] = new BlockCard(3, "assets/grass/grass-corner.png");
        blockCards[4] = new BlockCard(4, "assets/grass/grass-up-down.png");
        blockCards[5] = new BlockCard(5, "assets/grass/grass-up-down-side.png");
    }

    @Override
    public void update() {
        camera();
        for (BlockCard blockCard : blockCards) {
            blockCard.update(scroll);
        }
        if (Mouse.x >= 0 && Mouse.x <= Settings.cellSize * 2) {
            if (Mouse.mouseWheelDown) {
                if (scroll > -(blockCards.length - 11) * 60) {
                    scroll -= 72;
                }
            } else if (Mouse.mouseWheelUp) {
                if (scroll < 0) {
                    scroll += 72;
                }
            }
            Mouse.mouseWheelDown = false;
            Mouse.mouseWheelUp = false;
        } else {
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
                    if (!wasLoc && BlockCard.getAssetSeleceted() != null) {
                        // grid.add(new Cube(press, new Color(255, 0, 0)));
                        grid.add(new Cube(press, BlockCard.getAssetSeleceted(), angle));
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
                angle = (angle + 1) % 4;
            } else if (Keyboard.key != null && Keyboard.key == 27) {
                saveData();
                @SuppressWarnings("unchecked")
                List<LevelDTO> levels = (List<LevelDTO>) new LevelCRUD().select(SelectLevel.user.getCorreo());
                State.setActualState(new SelectLevel(levels, SelectLevel.user));
            }
        }
        // System.out.println("scroll: " + scroll + "\n" +
        // BlockCard.getAssetSeleceted());
    }

    private void saveData() {
        if (level.getId() == null) {
            try {
                level.setData(DTOUtils.objToString((ArrayList<Cube>) grid));
            } catch (IOException e) {
                System.out.println("Error to save (insert):\n" + e);
            }
            new LevelCRUD().insert(level);
        } else {
            try {
                level.setData(DTOUtils.objToString((ArrayList<Cube>) grid));
            } catch (IOException e) {
                System.out.println("Error to save (update):\n" + e);
            }
            new LevelCRUD().update(level);
        }
    }

    @Override
    public void draw(Graphics g) {
        g.setColor(Constants.cols[0]);
        g.fillRect(0, 0, Settings.width, Settings.height);
        g.translate((int) locCam.x, (int) locCam.y);
        for (Cube cube : grid) {
            cube.draw(g);
        }
        g.translate((int) -locCam.x, (int) -locCam.y);
        g.setColor(Constants.cols[5]);
        g.fillRect(0, 0, Settings.cellSize * 2, Settings.height);
        for (BlockCard blockCard : blockCards) {
            blockCard.draw(g, scroll, angle);
        }
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

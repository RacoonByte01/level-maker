package states;

import java.awt.Graphics;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import db.crud.LevelCRUD;
import db.dto.DTOUtils;
import db.dto.LevelDTO;
import gameObjects.Cube;
import gameObjects.Player;
import inputs.Keyboard;
import raccoon.PVector;
import settings.Constants;
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
        }

        @SuppressWarnings("unchecked")
        public GameState(LevelDTO level) {
                Keyboard.key = '´';
                /*
                 * When you create a level you dont have information so for this not crash
                 * we put the information necesary to it work
                 */
                if (level.getData() != null) {
                        try {
                                GameState.grid = (List<Cube>) DTOUtils.stringToObj(level.getData());
                        } catch (ClassNotFoundException | IOException e) {
                                System.out.println("Error al cargar la info:\n" + e);
                        }
                } else {
                        GameState.grid = new ArrayList<>();
                        // Spawn of player
                        GameState.grid.add(new Player(new PVector(0, 0), "assets/player/player.png"));
                        // GameState.grid.add(new Player(new PVector(0, 0), new Color(255, 255, 255)));
                }
        }

        @Override
        public void update() {
                Player player = (Player) grid.get(0);
                player.controls();
                for (Cube cube : grid) {
                        cube.update();
                }
                if (Keyboard.key != null && Keyboard.key == 27) {
                        try {
                                @SuppressWarnings("unchecked")
                                List<LevelDTO> levels = (List<LevelDTO>) new LevelCRUD()
                                                .select(SelectLevel.user.getCorreo());
                                State.setActualState(new SelectLevel(levels, SelectLevel.user));
                        } catch (Exception e) {
                                State.setActualState(new LoggingState());
                        }
                }
        }

        @Override
        public void draw(Graphics g) {
                g.setColor(Constants.cols[0]);
                g.fillRect(0, 0, Settings.width, Settings.height);
                Player player = (Player) grid.get(0);
                g.translate((int) -player.getLoc().x + Settings.width / 2,
                                (int) -player.getLoc().y + Settings.height / 2);
                for (Cube cube : grid) {
                        if (cube.getLoc().x > player.getLoc().x - Settings.cellSize - Settings.width / 2 &&
                                        cube.getLoc().x < player.getLoc().x + Settings.cellSize + Settings.width / 2 &&
                                        cube.getLoc().y > player.getLoc().y - Settings.cellSize - Settings.height / 2 &&
                                        cube.getLoc().y < player.getLoc().y + Settings.cellSize + Settings.height / 2) {
                                cube.draw(g);
                        }
                }
        }

}

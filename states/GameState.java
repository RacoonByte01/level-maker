package states;

import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.List;

import gameObjects.Cube;
import gameObjects.Player;
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
                g.translate((int) -player.getLoc().x + Settings.width / 2,
                                (int) -player.getLoc().y + Settings.height / 2);
                for (Cube cube : grid) {
                        cube.draw(g);
                }
        }

}

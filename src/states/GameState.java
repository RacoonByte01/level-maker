package states;

import java.awt.Graphics;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import db.dto.DTOUtils;
import db.dto.LevelDTO;
import gameObjects.Cube;
import gameObjects.Player;
import inputs.Keyboard;
import raccoon.PVector;
import settings.Constants;
import settings.Settings;

/**
 * class GameState
 * Esta clase permitira que el jugador se mueva por un nivel creado
 * 
 * @author Javi.LeL
 * @version 1.0
 */
public class GameState extends State {
        /* This hashmap will save all the cels with respect level */
        public static List<Cube> grid;

        /**
         * Obtiene la informacion de un nivel
         * 
         * @param level
         */
        @SuppressWarnings("unchecked")
        public GameState(LevelDTO level) {
                Keyboard.key = '´';
                /*
                 * Si pusiesemos un nivel sin informacion el programa craseari por la falta de
                 * informacion para esto le daremos informacion minima
                 */
                if (level.getData() != null) {
                        try {
                                // Pasara la informacion de String a su correspondiente objeto
                                GameState.grid = (List<Cube>) DTOUtils.stringToObj(level.getData());
                        } catch (ClassNotFoundException | IOException e) {
                                System.out.println("Error al cargar la info:\n" + e);
                        }
                } else {
                        GameState.grid = new ArrayList<>();
                        // Spawneara al jugador
                        GameState.grid.add(new Player(new PVector(0, 0), "assets/player/player.png"));
                }
        }

        /**
         * Metodo encargado de actualizar los objetos de la instancia de la pantalla
         */
        @Override
        public void update() {
                // El jugador siempre se encontrara en la posicion 0
                Player player = (Player) grid.get(0);
                // Le diremos que el jugadro tendra controles y leera los imputs del teclado
                player.controls();
                // Actualizaremos todos los cubos
                for (Cube cube : grid) {
                        cube.update();
                }
                /*
                 * Leeremos los impts de teclado
                 * Si este es 27 == escape este volvera al selector de niveles
                 */
                if (Keyboard.key != null && Keyboard.key == 27) {
                        try {
                                LoggingState.getLevelsAndReGenState(SelectLevel.user.getCorreo());
                        } catch (Exception e) {
                                State.setActualState(new LoggingState());
                        }
                }
        }

        /**
         * Metodo que realizara los calculos de dibujado
         * 
         * @param g
         */
        @Override
        public void draw(Graphics g) {
                // Pondra un fondo
                g.setColor(Constants.cols[0]);
                g.fillRect(0, 0, Settings.width, Settings.height);
                // Se obtendra el jugador y se casteara
                Player player = (Player) grid.get(0);
                // Para poder usar su posicion como camara
                g.translate((int) -player.getLoc().x + Settings.width / 2,
                                (int) -player.getLoc().y + Settings.height / 2);
                // Se dibujaran todos los cubos
                for (Cube cube : grid) {
                        // Siempre y cuando estos esten dentro de la camara del jugador
                        if (cube.getLoc().x > player.getLoc().x - Settings.cellSize - Settings.width / 2 &&
                                        cube.getLoc().x < player.getLoc().x + Settings.cellSize + Settings.width / 2 &&
                                        cube.getLoc().y > player.getLoc().y - Settings.cellSize - Settings.height / 2 &&
                                        cube.getLoc().y < player.getLoc().y + Settings.cellSize + Settings.height / 2) {
                                cube.draw(g);
                        }
                }
        }

}

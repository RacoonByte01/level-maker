package states;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

import db.crud.LevelCRUD;
import db.dto.LevelDTO;
import settings.Constants;
import settings.Settings;
import ui.Acttion;
import ui.Button;
import ui.Text;

/**
 * class DeleteState
 * Clase que borrara un nivel con interfaz visual
 * 
 * @author Javi.LeL
 * @version 1.0
 */
public class DeleteState extends State {
    private Button[] buttons = new Button[2];
    boolean revers;
    LevelDTO level;

    /**
     * Constructor que pondra la reversa como falso por defecto
     * 
     * @param level
     */
    DeleteState(LevelDTO level) {
        this(level, false);
    }

    /**
     * Constructor que permitira invertir los botones
     * 
     * @param level
     * @param revers
     */
    DeleteState(LevelDTO level, boolean revers) {
        // Si esta en reversa pondra los botone en posiciones contrarias
        if (revers) {
            // Se crean los botones y se meten en un array para poder manejarlos de forma
            // mucho mas sencilla
            buttons[0] = new Button(Settings.width * 3 / 5, Settings.height * 5 / 6, "Calcelar", new Acttion() {
                @Override
                public void accionARealizar() {
                    // Al cancelar se mandara al selector de niveles
                    LoggingState.getLevelsAndReGenState(SelectLevel.user.getCorreo());
                }
            });
            buttons[1] = new Button(Settings.width * 2 / 5, Settings.height * 5 / 6, "Aceptar", new Acttion() {
                @Override
                public void accionARealizar() {
                    // Ya que la accion de borrado es lenta en la base de datos se creara un nuevo
                    // State que actuara como ventana de carga
                    State.setActualState(new LoaddingState("Borrando", new Acttion() {
                        @Override
                        public void accionARealizar() {
                            // Se borrara el nivel correspondiente a la id del nivel
                            new LevelCRUD().delete(level.getId());
                        }
                    }, new Acttion() {
                        @Override
                        public void accionARealizar() {
                            // Se mandara al selector de niveles
                            LoggingState.getLevelsAndReGenState(SelectLevel.user.getCorreo());
                        }
                    }));
                }
            });
        } else {
            // Se crean los botones y se meten en un array para poder manejarlos de forma
            // mucho mas sencilla
            buttons[0] = new Button(Settings.width * 2 / 5, Settings.height * 5 / 6, "Calcelar", new Acttion() {
                @Override
                public void accionARealizar() {
                    // Al cancelar se mandara al selector de niveles
                    LoggingState.getLevelsAndReGenState(SelectLevel.user.getCorreo());
                }
            });
            buttons[1] = new Button(Settings.width * 3 / 5, Settings.height * 5 / 6, "Aceptar", new Acttion() {
                @Override
                public void accionARealizar() {
                    // Si se acepta se invertiran los botones
                    setActualState(new DeleteState(level, true));
                }
            });
        }
        this.revers = revers;
        this.level = level;
    }

    /**
     * Este metodo se encargara de realizar las operaciones matematicas
     */
    @Override
    public void update() {
        // Cargara todas las actualizaciones de los botones
        for (Button button : buttons) {
            button.update();
        }
    }

    /**
     * Este metodo se encargara de realizar todas las operaciones graficas
     * 
     * @param g
     */
    @Override
    public void draw(Graphics g) {
        // Se creara el fondo
        g.setColor(Constants.cols[0]);
        g.fillRect(0, 0, Settings.width, Settings.height);
        // Se pondra el color de las letras del fondo
        g.setColor(new Color(255, 255, 255));
        // Se dibujaran los botones
        for (Button button : buttons) {
            button.draw(g);
        }
        if (revers) { // Si esta en reversa
            // Creara estos campos de texto
            Text.drawText(g, "Estas seguro de eliminar", Settings.width / 2, Settings.height * 2 / 6, true,
                    new Font("Dialog", Font.PLAIN, 50));
            Text.drawText(g, level.getNombre(), Settings.width / 2, (int) (Settings.height * 2.5 / 6), true,
                    new Font("Dialog", Font.PLAIN, 50));
        } else { // Si no esta en reversa
            // Creara estos otros campos de texto
            Text.drawText(g, "Desea eliminar el nivel", Settings.width / 2, Settings.height * 2 / 6, true,
                    new Font("Dialog", Font.PLAIN, 50));
            Text.drawText(g, level.getNombre(), Settings.width / 2, (int) (Settings.height * 2.5 / 6), true,
                    new Font("Dialog", Font.PLAIN, 50));
        }
    }

}

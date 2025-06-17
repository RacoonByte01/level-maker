package states;

import java.awt.Graphics;
import java.util.List;

import db.crud.LevelCRUD;
import db.dto.LevelDTO;
import db.dto.UserDTO;
import inputs.Mouse;
import settings.Constants;
import settings.Settings;
import ui.Acttion;
import ui.Button;
import ui.LevelCard;

/**
 * class SelectLevel
 * Clase que probee de una interfaz visual para seleccionar el niveles
 * 
 * @author Javi.LeL
 * @version 1.0
 */
public class SelectLevel extends State {
    private int scroll;
    public static UserDTO user;
    List<LevelDTO> levels;
    private LevelCard[] levelCards;
    private Button[] buttons = new Button[4];;

    /**
     * Constructor para representar todos los niveles y guardar el usuario
     * 
     * @param levels
     * @param user
     */
    public SelectLevel(List<LevelDTO> levels, UserDTO user) {
        SelectLevel.user = user;
        if (levels != null) { // Si los niveles no son nulos
            this.levels = levels;
            // Cres un array con todos las catas de los niveles
            levelCards = new LevelCard[this.levels.size()];
        }
        // Leera y escribira las cartas
        for (int i = 0; i < levelCards.length; i++) {
            levelCards[i] = new LevelCard(i, levels.get(i));
        }
        // Se crearan los botones y se almacenara en un array para facilitar su uso
        buttons[0] = new Button(Settings.width * 2 / 6, Settings.height * 5 / 6, "Crear Nivel", new Acttion() {
            @Override
            public void accionARealizar() {
                /*
                 * Si se presiona el boton crear nivel, se ejecutara la accion de crear un nivel
                 * con la instancia ModificatorLevelFileState
                 */
                State.setActualState(new ModificatorLevelFileState());
            }
        });
        buttons[1] = new Button(Settings.width * 2 / 6, Settings.height * 5 / 6 + Constants.tamButomY + 5,
                "Jugar Nivel", new Acttion() {
                    @Override
                    public void accionARealizar() {
                        /*
                         * Si se presiona el boton Jugar nivel Creara una instancia de GameState con el
                         * nivel correspondiente
                         */
                        if (LevelCard.getIdSelected() != null) {
                            // Se leen los datos necesarios del nivel
                            @SuppressWarnings("unchecked")
                            LevelDTO levels = ((List<LevelDTO>) new LevelCRUD().select(LevelCard.getIdSelected()))
                                    .get(0);
                            // Y se le pasa al GameState para que pueda generar las colisiones del nivel
                            State.setActualState(new GameState(levels));
                        }
                    }
                });
        buttons[2] = new Button(Settings.width * 4 / 6, Settings.height * 5 / 6, "Borrar Nivel", new Acttion() {
            @Override
            public void accionARealizar() {
                // Al presionar el boton de borrar nivel
                if (LevelCard.getIdSelected() != null) {
                    // Se leen los datos necesarios del nivel para obtener la id
                    @SuppressWarnings("unchecked")
                    LevelDTO levels = ((List<LevelDTO>) new LevelCRUD().select(LevelCard.getIdSelected()))
                            .get(0);
                    // Se crea una instancia para borrar el nivel seleccionado
                    State.setActualState(new DeleteState(levels));
                }
            }
        });
        buttons[3] = new Button(Settings.width * 4 / 6, Settings.height * 5 / 6 + Constants.tamButomY + 5,
                "Editar Nivel", new Acttion() {
                    @Override
                    public void accionARealizar() {
                        // Al presionar el boton Editar Nivel
                        if (LevelCard.getIdSelected() != null) {
                            // Se recuperan todos los datos del nivel seleccionado
                            @SuppressWarnings("unchecked")
                            LevelDTO levels = ((List<LevelDTO>) new LevelCRUD().select(LevelCard.getIdSelected()))
                                    .get(0);
                            // Se crea una instancia LeverCreatorState con el nivel seleccionado para su
                            // posible modificacion del ususario
                            State.setActualState(new LeverCreatorState(levels));
                        }
                    }
                });
        this.scroll = 0;
    }

    /**
     * Metodo que realizara todas las operaciones relacionadas con las matematicas
     */
    @Override
    public void update() {
        // Se realizan las actualizaciones de los botones
        for (Button button : buttons) {
            button.update();
        }
        if (Mouse.y < Settings.height * 4 / 5) { // Si el raton es superior al cuadro de los botones
            // Se cargaran las actualizaciones de los botones
            for (LevelCard levelCard : levelCards) {
                levelCard.update(scroll);
            }
            if (Mouse.mouseWheelDown) { // Si se hace scroll hacia abajo
                if (scroll > -(levelCards.length - 5) * 120) { // Y las cartas no son mas de 5
                    // Hara un scroll de 60px en las cartas
                    scroll -= 60;
                }
            } else if (Mouse.mouseWheelUp) { // Si se hace scroll hacia arriba
                if (scroll < 0) { // Y el scroll es inferior a 0
                    // Hara un scroll de 60px en las cartas
                    scroll += 60;
                }
            }
        } else {
            // Cargaran las actualizaciones de las cartas
            for (LevelCard levelCard : levelCards) {
                levelCard.notMouse();
            }
        }
        // Se reinicia el scroll
        Mouse.mouseWheelDown = false;
        Mouse.mouseWheelUp = false;
        if (LevelCard.getIdSelected() == null) { // Si no se ha seleccionado ningun nivel
            // Desactivara todos los botones de accion
            for (int i = 1; i < buttons.length; i++) {
                buttons[i].setEnable(false);
            }
        } else { // Si uno esta seleccionado
            // Se activaran los botones de accion
            for (int i = 1; i < buttons.length; i++) {
                buttons[i].setEnable(true);
            }
        }
    }

    /**
     * Este metodo se encargara de realizar todas las operaciones graficas
     * 
     * @param g
     */
    @Override
    public void draw(Graphics g) {
        // Pintara un fondo
        g.setColor(Constants.cols[0]);
        g.fillRect(0, 0, Settings.width, Settings.height);
        // Dibujara las cartas
        for (LevelCard levelCard : levelCards) {
            levelCard.draw(g, scroll);
        }
        // Dibujar el fondo de los botones de accion
        g.setColor(Constants.cols[0]);
        g.fillRect(0, Settings.height * 4 / 5, Settings.width, Settings.height * 1 / 5);
        // Dibujara encima los botones
        for (Button button : buttons) {
            button.draw(g);
        }
    }

}
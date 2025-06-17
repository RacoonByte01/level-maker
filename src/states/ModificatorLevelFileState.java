package states;

import java.awt.Graphics;
import java.util.ArrayList;
import java.util.List;

import db.crud.LevelCRUD;
import db.dto.LevelDTO;
import settings.Constants;
import settings.Settings;
import ui.Acttion;
import ui.Button;
import ui.MessageError;
import ui.TextBox;

/**
 * class ModificatorLevelFileState
 * Clase que probee de un sistema visual para combiar datos del nivel tambien
 * sera la encargada de manejar el nombre del nivel al crearlo
 * 
 * @author Javi.LeL
 * @version 1.0
 */
public class ModificatorLevelFileState extends State {
    LevelDTO level;
    private TextBox textBoxName;
    private List<Button> buttonSends = new ArrayList<>();
    MessageError messageError;

    /**
     * Constructor basico
     */
    public ModificatorLevelFileState() {
        this(null);
    }

    /**
     * Constructor a partir de un nivel ya creado
     * 
     * @param level
     */
    public ModificatorLevelFileState(LevelDTO level) {
        // Se genera el cuadro de texto
        textBoxName = new TextBox(Settings.width / 2, Settings.height * 2 / 6, Settings.width * 2 / 3, 50,
                "Nombre del nivel *", 20);
        // Se genera el mensaje de error
        messageError = new MessageError(Settings.width / 2, Settings.height * 3 / 6, 500, 50);
        if (level != null) { // Si el nivel no es nulo
            this.level = level;
            // Se obtendra el nombre del nivel
            textBoxName.setText(level.getNombre());
            // Se generaran los botones y se guardaran en una lista para facilitar su manejo
            buttonSends.add(new Button(Settings.width * 3 / 5, Settings.height * 5 / 6, "Guardar y Seguir >",
                    new Acttion() {
                        @Override
                        public void accionARealizar() {
                            // Pondra el nuevo nombre en el nivel
                            level.setNombre(textBoxName.getText());
                            // Ya que la accion de actualizar el nivel puede durar unos segundos se lleva a
                            // una ventana de carga
                            State.setActualState(new LoaddingState("Guardando", new Acttion() {
                                @Override
                                public void accionARealizar() {
                                    // La cual en segundo plano realizara la actualizacion del nivel
                                    new LevelCRUD().update(level);
                                }
                            }, new Acttion() {
                                @Override
                                public void accionARealizar() {
                                    // Y volvera a la aedicion del nivel
                                    State.setActualState(new LeverCreatorState(level));
                                }
                            }));
                        }
                    }));
            buttonSends.add(new Button(Settings.width * 2 / 5, Settings.height * 5 / 6, "< Cancelar", new Acttion() {
                @Override
                public void accionARealizar() {
                    // Al cancelar se devovera al editor de niveles
                    State.setActualState(new LeverCreatorState(level));
                }
            }));
            buttonSends.add(new Button(Settings.width / 2, Settings.height * 10 / 11, "< GUARDAR Y SALIR >",
                    new Acttion() {
                        @Override
                        public void accionARealizar() {
                            // Actualizara el nombre en la base de datos
                            level.setNombre(textBoxName.getText());
                            // Esto al tardar lanzara una ventana de carga
                            State.setActualState(new LoaddingState("Guardando", new Acttion() {
                                @Override
                                public void accionARealizar() {
                                    // Actualizara el nivel con los datos que ha modificado el usario
                                    new LevelCRUD().update(level);
                                }
                            }, new Acttion() {
                                @Override
                                public void accionARealizar() {
                                    // Despues de esto ira al selector de niveles
                                    LoggingState.getLevelsAndReGenState(SelectLevel.user.getCorreo());
                                }
                            }));
                        }
                    }));
        } else { // Si el nivel es nulo
            // se generaran los botones y se guardaran en una lista para facilitar su manejo
            buttonSends.add(new Button(Settings.width * 3 / 5, Settings.height * 5 / 6, "Crear >", new Acttion() {
                @Override
                public void accionARealizar() {
                    // Ya que la accion de crear puede tardar varios minutos se lanzara una ventana
                    // de carga
                    State.setActualState(new LoaddingState(State.getActualState(), "Creando", new Acttion() {
                        @Override
                        public void accionARealizar() {
                            // En Segundo plano se lanzara la inserccion del nuevo registro en la base de
                            // datos
                            new LevelCRUD().insert(
                                    new LevelDTO(textBoxName.getText(), null, SelectLevel.user.getCorreo()));
                            // Despues se ira al apartado de seleccion de niveles
                            LoggingState.getLevelsAndReGenState(SelectLevel.user.getCorreo());
                        }
                    }));

                }
            }));
            buttonSends.add(new Button(Settings.width * 2 / 5, Settings.height * 5 / 6, "< Cancelar", new Acttion() {
                @Override
                public void accionARealizar() {
                    // Si se cancela se ira al aparatado de selecion de niveles
                    LoggingState.getLevelsAndReGenState(SelectLevel.user.getCorreo());
                }
            }));
        }
    }

    /**
     * Metodo que realizara todas las operaciones relacionadas con las matematicas
     */
    @Override
    public void update() {
        // Se realizaran las actualizaciones de la caja de texto
        textBoxName.update();
        // Se realizaran las actualizaciones de los botones
        for (Button button : buttonSends) {
            button.update();
        }
        if (textBoxName.getText().trim().length() == 0) { // Si el texto es inferior a 0
            if (buttonSends.size() == 2) { // Si el tamano de la lista de los botones es 2
                // Desactivara el boton numero 0 (Crear)
                buttonSends.get(0).setEnable(false);
            } else if (buttonSends.size() == 3) { // Si el tamano de la lista de los botones es 3
                // Desactivara el boton numero 0 (Guardar y seguir)
                buttonSends.get(0).setEnable(false);
                // Desactivara el boton numero 2 (Guardar y salir)
                buttonSends.get(2).setEnable(false);
            }
        } else { // Si no es asi
            if (buttonSends.size() == 2) { // Si el tamano de la lista de los botones es 2
                // Activara el boton numero 0 (Crear)
                buttonSends.get(0).setEnable(true);
            } else if (buttonSends.size() == 3) { // Si el tamano de la lista de los botones es 3
                // Activara el boton numero 0 (Guardar y seguir)
                buttonSends.get(0).setEnable(true);
                // Activara el boton numero 2 (Guardar y salir)
                buttonSends.get(2).setEnable(true);
            }
        }
        // Hara las actualizaciones de los mensajes de error
        messageError.update();
    }

    /**
     * Este metodo se encargara de realizar todas las operaciones graficas
     * 
     * @param g
     */
    @Override
    public void draw(Graphics g) {
        // Se generara el fondo
        g.setColor(Constants.cols[0]);
        g.fillRect(0, 0, Settings.width, Settings.height);
        // Dibujara la caja de texto
        textBoxName.draw(g);
        // Dibujara los bootnes
        for (Button button : buttonSends) {
            button.draw(g);
        }
        // Dibujara el mensaje de error
        messageError.darw(g);
    }
}

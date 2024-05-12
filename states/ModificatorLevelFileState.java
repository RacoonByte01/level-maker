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
 * ModificatorLevelFileState
 * 
 * @author JaviLeL
 * @version 1.0
 */
public class ModificatorLevelFileState extends State {
    LevelDTO level;
    private TextBox textBoxName;
    private List<Button> buttonSends = new ArrayList<>();
    MessageError messageError;

    public ModificatorLevelFileState() {
        this(null);
    }

    public ModificatorLevelFileState(LevelDTO level) {
        textBoxName = new TextBox(Settings.width / 2, Settings.height * 2 / 6, Settings.width * 2 / 3, 50,
                "Nombre del nivel *", 20);
        messageError = new MessageError(Settings.width / 2, Settings.height * 3 / 6, 500, 50);
        if (level != null) {
            this.level = level;
            textBoxName.setText(level.getNombre());
            buttonSends.add(new Button(Settings.width * 3 / 5, Settings.height * 5 / 6, "Guardar y Seguir >",
                    new Acttion() {
                        @Override
                        public void accionARealizar() {
                            if (textBoxName.getText().length() == 0
                                    && !textBoxName.getText().equals(level.getNombre())) {
                                messageError.setText("Porfavor renombre su nivel");
                                messageError.setVisibleTime(250);
                            } else {
                                // Update name in RAM obj and after update this in BD
                                level.setNombre(textBoxName.getText());
                                State.setActualState(new LoaddingState("Guardando", new Acttion() {
                                    @Override
                                    public void accionARealizar() {
                                        new LevelCRUD().update(level);
                                    }
                                }, new Acttion() {
                                    @Override
                                    public void accionARealizar() {
                                        State.setActualState(new LeverCreatorState(level));
                                    }
                                }));
                            }
                        }
                    }));
            buttonSends.add(new Button(Settings.width * 2 / 5, Settings.height * 5 / 6, "< Cancelar", new Acttion() {
                @Override
                public void accionARealizar() {
                    State.setActualState(new LeverCreatorState(level));
                }
            }));
            buttonSends.add(new Button(Settings.width / 2, Settings.height * 10 / 11, "< GUARDAR Y SALIR >",
                    new Acttion() {
                        @Override
                        public void accionARealizar() {
                            // Update name in RAM obj and after update this in BD
                            level.setNombre(textBoxName.getText());
                            State.setActualState(new LoaddingState("Guardando", new Acttion() {
                                @Override
                                public void accionARealizar() {
                                    new LevelCRUD().update(level);
                                }
                            }, new Acttion() {
                                @Override
                                public void accionARealizar() {
                                    LoggingState.getLevelsAndReGenState(SelectLevel.user.getCorreo());
                                }
                            }));
                        }
                    }));
        } else {
            buttonSends.add(new Button(Settings.width * 3 / 5, Settings.height * 5 / 6, "Crear >", new Acttion() {
                @Override
                public void accionARealizar() {
                    if (textBoxName.getText().trim().length() == 0) {
                        messageError.setText("Porfavor nombre su nivel");
                        messageError.setVisibleTime(250);
                    } else {
                        State.setActualState(new LoaddingState(State.getActualState(), "Creando", new Acttion() {
                            @Override
                            public void accionARealizar() {
                                new LevelCRUD().insert(
                                        new LevelDTO(textBoxName.getText(), null, SelectLevel.user.getCorreo()));
                                LoggingState.getLevelsAndReGenState(SelectLevel.user.getCorreo());
                            }
                        }));
                    }
                }
            }));
            buttonSends.add(new Button(Settings.width * 2 / 5, Settings.height * 5 / 6, "< Cancelar", new Acttion() {
                @Override
                public void accionARealizar() {
                    LoggingState.getLevelsAndReGenState(SelectLevel.user.getCorreo());
                }
            }));
        }
    }

    @Override
    public void update() {
        textBoxName.update();
        for (Button button : buttonSends) {
            button.update();
        }
        if (textBoxName.getText().trim().length() == 0) {
            if (buttonSends.size() == 2) {
                buttonSends.get(0).setEnable(false);
            } else if (buttonSends.size() == 3) {
                buttonSends.get(0).setEnable(false);
                buttonSends.get(2).setEnable(false);
            }
        } else {
            if (buttonSends.size() == 2) {
                buttonSends.get(0).setEnable(true);
            } else if (buttonSends.size() == 3) {
                buttonSends.get(0).setEnable(true);
                buttonSends.get(2).setEnable(true);
            }
        }
        messageError.update();
    }

    @Override
    public void draw(Graphics g) {
        g.setColor(Constants.cols[0]);
        g.fillRect(0, 0, Settings.width, Settings.height);
        textBoxName.draw(g);
        for (Button button : buttonSends) {
            button.draw(g);
        }
        messageError.darw(g);
    }
}

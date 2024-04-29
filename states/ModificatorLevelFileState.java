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
            buttonSends.add(new Button(Settings.width * 3 / 5, Settings.height * 5 / 6, "Guardar Ajustes >",
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
                                new LevelCRUD().update(level);
                                State.setActualState(new LeverCreatorState(level));
                            }
                        }
                    }));
            buttonSends.add(new Button(Settings.width * 2 / 5, Settings.height * 5 / 6, "< Cancelar", new Acttion() {
                @Override
                public void accionARealizar() {
                    State.setActualState(new LeverCreatorState(level));
                }
            }));
            buttonSends.add(new Button(Settings.width / 2, Settings.height * 10 / 11, "< SALIR >",
                    new Acttion() {
                        @Override
                        public void accionARealizar() {
                            LoggingState.getLevelsAndReGenState(SelectLevel.user.getCorreo());
                        }
                    }));
        } else {
            buttonSends.add(new Button(Settings.width * 3 / 5, Settings.height * 5 / 6, "Crear >", new Acttion() {
                @Override
                public void accionARealizar() {
                    if (textBoxName.getText().length() == 0) {
                        messageError.setText("Porfavor nombre su nivel");
                        messageError.setVisibleTime(250);
                    } else {
                        new LevelCRUD().insert(new LevelDTO(textBoxName.getText(), null, SelectLevel.user.getCorreo()));
                        @SuppressWarnings("unchecked")
                        List<LevelDTO> levels = (List<LevelDTO>) new LevelCRUD().select(SelectLevel.user.getCorreo());
                        State.setActualState(new SelectLevel(levels, SelectLevel.user));
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

package states;

import java.awt.Graphics;
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
    private Button buttonSend;
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
        } else {
            buttonSend = new Button(Settings.width / 2, Settings.height * 5 / 6, "Crear", new Acttion() {
                @Override
                public void accionARealizar() {
                    if (textBoxName.getText().length() == 0) {
                        messageError.setText("Porfavor nombra al nivel");
                        messageError.setVisibleTime(250);
                    } else {
                        new LevelCRUD().insert(new LevelDTO(textBoxName.getText(), null, SelectLevel.user.getCorreo()));
                        @SuppressWarnings("unchecked")
                        List<LevelDTO> levels = (List<LevelDTO>) new LevelCRUD().select(SelectLevel.user.getCorreo());
                        State.setActualState(new SelectLevel(levels, SelectLevel.user));
                    }
                }
            });
        }
    }

    @Override
    public void update() {
        textBoxName.update();
        buttonSend.update();
        messageError.update();
    }

    @Override
    public void draw(Graphics g) {
        g.setColor(Constants.cols[0]);
        g.fillRect(0, 0, Settings.width, Settings.height);
        textBoxName.draw(g);
        buttonSend.draw(g);
        messageError.darw(g);
    }
}

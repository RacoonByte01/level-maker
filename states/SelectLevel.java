package states;

import java.awt.Color;
import java.awt.Graphics;
import java.util.List;

import db.crud.LevelCRUD;
import db.dto.LevelDTO;
import db.dto.UserDTO;
import settings.Constants;
import settings.Settings;
import ui.Acttion;
import ui.Button;
import ui.LevelCard;

/**
 * SelectLevel
 * 
 * @author JaviLeL
 * @version 1.0
 */
public class SelectLevel extends State {
    public static UserDTO user;
    List<LevelDTO> levels;
    private LevelCard[] levelCards;
    private Button[] buttons = new Button[4];;

    public SelectLevel(List<LevelDTO> levels, UserDTO user) {
        SelectLevel.user = user;
        if (levels != null) {
            this.levels = levels;
            levelCards = new LevelCard[this.levels.size()];
        }
        for (int i = 0; i < levelCards.length; i++) {
            levelCards[i] = new LevelCard(i, levels.get(i));
        }
        buttons[0] = new Button(Settings.width * 2 / 6, Settings.height * 5 / 6, "Crear Nivel", new Acttion() {
            @Override
            public void accionARealizar() {
                State.setActualState(new ModificatorLevelFileState());
            }
        });
        buttons[1] = new Button(Settings.width * 2 / 6, Settings.height * 5 / 6 + Constants.tamButomY + 5,
                "Jugar Nivel", null);
        buttons[2] = new Button(Settings.width * 4 / 6, Settings.height * 5 / 6, "Borrar Nivel", null);
        buttons[3] = new Button(Settings.width * 4 / 6, Settings.height * 5 / 6 + Constants.tamButomY + 5,
                "Editar Nivel", new Acttion() {
                    @Override
                    public void accionARealizar() {
                        if (LevelCard.getIdSelected() != null) {
                            @SuppressWarnings("unchecked")
                            LevelDTO levels = ((List<LevelDTO>) new LevelCRUD().select(LevelCard.getIdSelected()))
                                    .get(0);
                            State.setActualState(new LeverCreatorState(levels));
                        }
                    }
                });
    }

    @Override
    public void update() {
        for (Button button : buttons) {
            button.update();
        }
        for (LevelCard levelCard : levelCards) {
            levelCard.update();
        }
    }

    @Override
    public void draw(Graphics g) {
        g.setColor(new Color(0));
        g.fillRect(0, 0, Settings.width, Settings.height);
        for (LevelCard levelCard : levelCards) {
            levelCard.draw(g);
        }
        g.setColor(new Color(0));
        g.fillRect(0, Settings.height * 4 / 5, Settings.width, Settings.height * 1 / 5);
        for (Button button : buttons) {
            button.draw(g);
        }
    }

}
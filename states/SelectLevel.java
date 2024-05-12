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
 * SelectLevel
 * 
 * @author JaviLeL
 * @version 1.0
 */
public class SelectLevel extends State {
    private int scroll;
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
                "Jugar Nivel", new Acttion() {
                    @Override
                    public void accionARealizar() {
                        if (LevelCard.getIdSelected() != null) {
                            @SuppressWarnings("unchecked")
                            LevelDTO levels = ((List<LevelDTO>) new LevelCRUD().select(LevelCard.getIdSelected()))
                                    .get(0);
                            State.setActualState(new GameState(levels));
                        }
                    }
                });
        buttons[2] = new Button(Settings.width * 4 / 6, Settings.height * 5 / 6, "Borrar Nivel", new Acttion() {
            @Override
            public void accionARealizar() {
                if (LevelCard.getIdSelected() != null) {
                    @SuppressWarnings("unchecked")
                    LevelDTO levels = ((List<LevelDTO>) new LevelCRUD().select(LevelCard.getIdSelected()))
                            .get(0);
                    State.setActualState(new DeleteState(levels));
                }
            }
        });
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
        this.scroll = 0;
    }

    @Override
    public void update() {
        for (Button button : buttons) {
            button.update();
        }
        if (Mouse.y < Settings.height * 4 / 5) {
            for (LevelCard levelCard : levelCards) {
                levelCard.update(scroll);
            }
            if (Mouse.mouseWheelDown) {
                if (scroll > -(levelCards.length - 5) * 120) {
                    scroll -= 60;
                }
            } else if (Mouse.mouseWheelUp) {
                if (scroll < 0) {
                    scroll += 60;
                }
            }
        } else {
            for (LevelCard levelCard : levelCards) {
                levelCard.notMouse();
            }
        }
        /* Reset the scroll */
        Mouse.mouseWheelDown = false;
        Mouse.mouseWheelUp = false;
        if (LevelCard.getIdSelected() == null) {
            for (int i = 1; i < buttons.length; i++) {
                buttons[i].setEnable(false);
            }
        } else {
            for (int i = 1; i < buttons.length; i++) {
                buttons[i].setEnable(true);
            }
        }
    }

    @Override
    public void draw(Graphics g) {
        g.setColor(Constants.cols[0]);
        g.fillRect(0, 0, Settings.width, Settings.height);
        for (LevelCard levelCard : levelCards) {
            levelCard.draw(g, scroll);
        }
        g.setColor(Constants.cols[0]);
        g.fillRect(0, Settings.height * 4 / 5, Settings.width, Settings.height * 1 / 5);
        for (Button button : buttons) {
            button.draw(g);
        }
    }

}
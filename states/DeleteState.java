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

public class DeleteState extends State {
    private Button[] buttons = new Button[2];
    boolean revers;
    LevelDTO level;

    DeleteState(LevelDTO level) {
        this(level, false);
    }

    DeleteState(LevelDTO level, boolean revers) {
        if (revers) {
            buttons[0] = new Button(Settings.width * 3 / 5, Settings.height * 5 / 6, "Calcelar", new Acttion() {
                @Override
                public void accionARealizar() {
                    LoggingState.getLevelsAndReGenState(SelectLevel.user.getCorreo());
                }
            });
            buttons[1] = new Button(Settings.width * 2 / 5, Settings.height * 5 / 6, "Aceptar", new Acttion() {
                @Override
                public void accionARealizar() {
                    State.setActualState(new LoaddingState("Borrando", new Acttion() {
                        @Override
                        public void accionARealizar() {
                            new LevelCRUD().delete(level.getId());
                        }
                    }, new Acttion() {
                        @Override
                        public void accionARealizar() {
                            LoggingState.getLevelsAndReGenState(SelectLevel.user.getCorreo());
                        }
                    }));
                }
            });
        } else {
            buttons[0] = new Button(Settings.width * 2 / 5, Settings.height * 5 / 6, "Calcelar", new Acttion() {
                @Override
                public void accionARealizar() {
                    LoggingState.getLevelsAndReGenState(SelectLevel.user.getCorreo());
                }
            });
            buttons[1] = new Button(Settings.width * 3 / 5, Settings.height * 5 / 6, "Aceptar", new Acttion() {
                @Override
                public void accionARealizar() {
                    setActualState(new DeleteState(level, true));
                }
            });
        }
        this.revers = revers;
        this.level = level;
    }

    @Override
    public void update() {
        for (Button button : buttons) {
            button.update();
        }
    }

    @Override
    public void draw(Graphics g) {
        g.setColor(Constants.cols[0]);
        g.fillRect(0, 0, Settings.width, Settings.height);
        g.setColor(new Color(255, 255, 255));
        for (Button button : buttons) {
            button.draw(g);
        }
        if (revers) {
            Text.drawText(g, "Estas seguro de eliminar", Settings.width / 2, Settings.height * 2 / 6, true,
                    new Font("Dialog", Font.PLAIN, 50));
            Text.drawText(g, level.getNombre(), Settings.width / 2, (int) (Settings.height * 2.5 / 6), true,
                    new Font("Dialog", Font.PLAIN, 50));
        } else {
            Text.drawText(g, "Desea eliminar el nivel", Settings.width / 2, Settings.height * 2 / 6, true,
                    new Font("Dialog", Font.PLAIN, 50));
            Text.drawText(g, level.getNombre(), Settings.width / 2, (int) (Settings.height * 2.5 / 6), true,
                    new Font("Dialog", Font.PLAIN, 50));
        }
    }

}

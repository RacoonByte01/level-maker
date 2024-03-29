package states;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.util.ArrayList;

import settings.Constants;
import settings.Settings;
import ui.Acttion;
import ui.Button;
import ui.CheckBox;
import ui.Text;
import windows.MainWindow;

public class MenuState extends State {
    ArrayList<Button> buttons;
    CheckBox checkBox;

    public MenuState() {
        this.buttons = new ArrayList<>();
        buttons.add(new Button(Settings.width / 2, Settings.height / 2, "Single Player", new Acttion() {
            @Override
            public void accionARealizar() {
                // GameState.setActualState(new GameState());
            }
        }));

        buttons.add(new Button(Settings.width / 2, Settings.height / 2 + Constants.tamButomY + 10, "Multy Player",
                new Acttion() {
                    @Override
                    public void accionARealizar() {
                        // GameState.setActualState(new SelectMultijugador());
                    }
                }));

        buttons.add(new Button(Settings.width / 2, Settings.height / 2 + Constants.tamButomY * 2 + 20, "Exit",
                new Acttion() {
                    @Override
                    public void accionARealizar() {
                        MainWindow.funcionar = false;
                    }
                }));
    }

    @Override
    public void update() {
        for (Button button : buttons) {
            button.update();
        }
    }

    @Override
    public void draw(Graphics g) {
        // checkBox = new CheckBox(10, 110, "Prueba", false, 20);
        // fondo
        g.setColor(new Color(0, 0, 0));
        g.fillRect(0, 0, Settings.width, Settings.height);
        for (Button button : buttons) {
            button.draw(g);
        }
        checkBox.draw(g);
        g.setColor(new Color(255, 255, 255));
        Text.drawText(g, "Level - Maker", Settings.width / 2, 100, true, new Font("Dialog", Font.PLAIN, 50));
    }
}
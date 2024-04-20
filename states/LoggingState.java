package states;

import ui.Acttion;
import ui.Button;
import ui.Text;
import ui.TextBox;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.util.List;

import db.crud.LevelCRUD;
import db.crud.UserCRUD;
import db.dto.DTOUtils;
import db.dto.LevelDTO;
import db.dto.UserDTO;
import settings.Settings;

/**
 * LoggingState
 * This class provides ha windows with loggin for the user
 * 
 * @author JaviLeL
 * @version 1.0
 */
public class LoggingState extends State {
    Button[] buttons = new Button[2];
    TextBox[] textBoxs = new TextBox[2];
    // CheckBox checkBox;

    public LoggingState() {
        textBoxs[0] = new TextBox(Settings.width / 2, Settings.height * 2 / 6, 500, 50, "Correo", 20);
        textBoxs[1] = new TextBox(Settings.width / 2, Settings.height * 3 / 6, 500, 50, "Password", 20, true);
        // checkBox = new CheckBox(Settings.width / 2 - "Recuerdame".length() * 13 / 2,
        // Settings.height * 4 / 6,
        // "Recuerdame", false, 20);
        buttons[0] = new Button(Settings.width * 3 / 5, Settings.height * 5 / 6, "Iniciar sesion >", new Acttion() {
            @Override
            public void accionARealizar() {
                UserDTO user = (UserDTO) new UserCRUD().select(textBoxs[0].getText());
                if (user != null && user.getPass().equals(DTOUtils.getMD5(textBoxs[1].getText()))) {
                    // TODO code read and represent all levels
                    @SuppressWarnings("unchecked")
                    List<LevelDTO> levels = (List<LevelDTO>) new LevelCRUD().select(user.getCorreo());
                    State.setActualState(new SelectLevel(levels, user));
                } else {
                    // TODO code error
                    System.out.println("MAL");
                }
            }
        });
        buttons[1] = new Button(Settings.width * 2 / 5, Settings.height * 5 / 6, "Registrar", new Acttion() {
            @Override
            public void accionARealizar() {
                State.setActualState(new RegisterState());
            }
        });
    }

    @Override
    public void update() {
        for (Button button : buttons) {
            button.update();
        }
        for (TextBox textBox : textBoxs) {
            textBox.update();
        }
    }

    @Override
    public void draw(Graphics g) {
        g.setColor(new Color(0));
        g.fillRect(0, 0, Settings.width, Settings.height);
        for (Button button : buttons) {
            button.draw(g);
        }
        for (TextBox textBox : textBoxs) {
            textBox.draw(g);
        }

        g.setColor(new Color(255, 255, 255));
        Text.drawText(g, "Inicio de Sesión", Settings.width / 2, (int) (Settings.height * .7 / 6), true,
                new Font("Dialog", Font.PLAIN, 50));
        Text.drawText(g, "No estas registrado aun?", Settings.width / 2, (int) (Settings.height * 4 / 6), true,
                new Font("Dialog", Font.PLAIN, 16));
        Text.drawText(g, "pulse el boton \"Registrar\"", Settings.width / 2, (int) (Settings.height * 4 / 6) + 18, true,
                new Font("Dialog", Font.PLAIN, 16));
        // checkBox.draw(g);
    }

}

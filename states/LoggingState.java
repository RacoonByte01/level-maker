package states;

import ui.Acttion;
import ui.Button;
import ui.MessageError;
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
import settings.Constants;
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
    private static MessageError messageError;
    // CheckBox checkBox;

    public LoggingState() {
        textBoxs[0] = new TextBox(Settings.width / 2, Settings.height * 2 / 6, 500, 50, "Correo *", 20);
        textBoxs[1] = new TextBox(Settings.width / 2, Settings.height * 3 / 6, 500, 50, "Contraseña *", 20, true);
        messageError = new MessageError(Settings.width / 2, Settings.height * 4 / 6, 500, 50);
        // checkBox = new CheckBox(Settings.width / 2 - "Recuerdame".length() * 13 / 2,
        // Settings.height * 4 / 6,
        // "Recuerdame", false, 20);
        buttons[0] = new Button(Settings.width * 3 / 5, Settings.height * 5 / 6, "Iniciar sesion >", new Acttion() {
            @Override
            public void accionARealizar() {
                try {
                    if (textBoxs[0].getText().length() != 0 && textBoxs[1].getText().length() != 0) {
                        loggin(textBoxs[0].getText(), textBoxs[1].getText());
                    } else {
                        messageError.setText("¡Porfavor rellene todos los campos!");
                        messageError.setVisibleTime(250);
                    }
                } catch (Exception e) {
                    messageError.setText("¡Error de conexión revise su conexion a internet!");
                    messageError.setVisibleTime(250);
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
        messageError.update();
    }

    @Override
    public void draw(Graphics g) {
        g.setColor(Constants.cols[0]);
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
        // checkBox.draw(g);
        messageError.darw(g);
    }

    static void loggin(String email, String pass) {
        UserDTO user = (UserDTO) new UserCRUD().select(email);
        if (user != null && user.getPass().equals(DTOUtils.getMD5(pass))) {
            getLevelsAndReGenState(email);
        } else {
            messageError.setText("¡Error al iniciar sesion! Puede que no este registrado");
            messageError.setVisibleTime(250);
        }
    }

    public static void getLevelsAndReGenState(String email) {
        UserDTO user = (UserDTO) new UserCRUD().select(email);
        @SuppressWarnings("unchecked")
        List<LevelDTO> levels = (List<LevelDTO>) new LevelCRUD().select(user.getCorreo());
        State.setActualState(new SelectLevel(levels, user));
    }
}

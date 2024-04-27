package states;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

import db.crud.UserCRUD;
import db.dto.UserDTO;
import settings.Constants;
import settings.Settings;
import ui.Acttion;
import ui.Text;
import ui.Button;
import ui.TextBox;

public class RegisterState extends State {
    Button[] buttons = new Button[2];
    TextBox[] textBoxs = new TextBox[4];

    public RegisterState() {
        textBoxs[0] = new TextBox(Settings.width / 2, Settings.height * 2 / 7, 500, 50, "Correo", 20);
        textBoxs[1] = new TextBox(Settings.width / 2, Settings.height * 3 / 7, 500, 50, "Password", 20, true);
        textBoxs[2] = new TextBox(Settings.width / 2, Settings.height * 4 / 7, 500, 50, "Nombre", 20);
        textBoxs[3] = new TextBox(Settings.width / 2, Settings.height * 5 / 7, 500, 50, "Telefono", 20);

        buttons[0] = new Button(Settings.width * 3 / 5, Settings.height * 5 / 6, "Registrarse >", new Acttion() {
            @Override
            public void accionARealizar() {
                UserDTO user = new UserDTO(textBoxs[0].getText(), textBoxs[1].getText(), textBoxs[2].getText(),
                        textBoxs[3].getText());
                // System.out.println(user.toString());
                boolean isInserted = new UserCRUD().insert(user);
                if (isInserted) {
                    System.out.println("Se registro sin problemas");
                    State.setActualState(new LoggingState());
                    // TODO code mesage good
                    System.out.println("BIEN");
                } else {
                    // TODO code error
                    System.out.println("MAL");
                }
            }
        });
        buttons[1] = new Button(Settings.width * 2 / 5, Settings.height * 5 / 6, "< Volver", new Acttion() {
            @Override
            public void accionARealizar() {
                State.setActualState(new LoggingState());
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
        g.setColor(Constants.cols[0]);
        g.fillRect(0, 0, Settings.width, Settings.height);
        for (Button button : buttons) {
            button.draw(g);
        }
        for (TextBox textBox : textBoxs) {
            textBox.draw(g);
        }

        g.setColor(new Color(255, 255, 255));
        Text.drawText(g, "Registrarse", Settings.width / 2, (int) (Settings.height * .7 / 6), true,
                new Font("Dialog", Font.PLAIN, 50));
    }
}

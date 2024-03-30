package states;

import ui.Acttion;
import ui.Button;
import ui.CheckBox;
import ui.Text;
import ui.TextBox;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

import settings.Settings;

/**
 * LoggingState
 * This class provides ha windows with loggin for the user
 * 
 * @author JaviLeL
 * @version 1.0
 */
public class LoggingState extends State {
    Button button;
    TextBox[] textBoxs = new TextBox[2];
    CheckBox checkBox;

    public LoggingState() {
        textBoxs[0] = new TextBox(Settings.width / 2, Settings.height * 2 / 6, 500, 50, "Correo", 20);
        textBoxs[1] = new TextBox(Settings.width / 2, Settings.height * 3 / 6, 500, 50, "Password", 20, true);
        checkBox = new CheckBox(Settings.width / 2 - "Recuerdame".length() * 13 / 2, Settings.height * 4 / 6,
                "Recuerdame", false, 20);
        button = new Button(Settings.width / 2, Settings.height * 5 / 6, "Enviar", new Acttion() {
            @Override
            public void accionARealizar() {
                // textBoxs[1].togglePassword();
                // System.out.println("Se enviaran los datos :D");
                State.setActualState(new CameraState());
            }
        });
    }

    @Override
    public void update() {
        button.update();
        for (TextBox textBox : textBoxs) {
            textBox.update();
        }
    }

    @Override
    public void draw(Graphics g) {
        g.setColor(new Color(0));
        g.fillRect(0, 0, Settings.width, Settings.height);
        button.draw(g);
        for (TextBox textBox : textBoxs) {
            textBox.draw(g);
        }

        g.setColor(new Color(255, 255, 255));
        Text.drawText(g, "Inicio de Sesión", Settings.width / 2, (int) (Settings.height * .7 / 6), true,
                new Font("Dialog", Font.PLAIN, 50));
        checkBox.draw(g);
    }

}

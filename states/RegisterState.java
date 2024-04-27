package states;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.util.regex.Pattern;

import db.crud.UserCRUD;
import db.dto.UserDTO;
import settings.Constants;
import settings.Settings;
import ui.Acttion;
import ui.Text;
import ui.Button;
import ui.MesajeError;
import ui.TextBox;

public class RegisterState extends State {
    Button[] buttons = new Button[2];
    TextBox[] textBoxs = new TextBox[4];
    MesajeError mesajeError;

    public RegisterState() {
        textBoxs[0] = new TextBox(Settings.width / 2, Settings.height * 2 / 8, 500, 50, "Correo *", 20);
        textBoxs[1] = new TextBox(Settings.width / 2, Settings.height * 3 / 8, 500, 50, "Contraseña *", 20, true);
        textBoxs[2] = new TextBox(Settings.width / 2, Settings.height * 4 / 8, 500, 50, "Nombre *", 20);
        textBoxs[3] = new TextBox(Settings.width / 2, Settings.height * 5 / 8, 500, 50, "Telefono", 20);
        mesajeError = new MesajeError(Settings.width / 2, Settings.height * 6 / 8, 500, 50);
        buttons[0] = new Button(Settings.width * 3 / 5, Settings.height * 5 / 6, "Registrarse >", new Acttion() {
            @Override
            public void accionARealizar() {
                if (!isEmail(textBoxs[0].getText().trim().toLowerCase())) {
                    mesajeError.setText("El correo no es valido");
                    mesajeError.setVisibleTime(250);
                } else if (!isPasswd(textBoxs[1].getText())) {
                    mesajeError.setText("La contraseña debe de tener minimo 8 caracteres");
                    mesajeError.setVisibleTime(250);
                } else if (!isName(textBoxs[2].getText().trim())) {
                    mesajeError.setText("Su nombre debe tener minimo 4 carcteres");
                    mesajeError.setVisibleTime(250);
                } else if (!(textBoxs[3].getText().equals("") || isTel(textBoxs[3].getText()))) {
                    mesajeError.setText("Su numero de telefono no es correcto");
                    mesajeError.setVisibleTime(250);
                } else {
                    try {
                        UserDTO user = new UserDTO(
                                textBoxs[0].getText().trim().toLowerCase(),
                                textBoxs[1].getText(),
                                textBoxs[2].getText().trim(),
                                textBoxs[3].getText().trim().toLowerCase());
                        boolean isInserted = new UserCRUD().insert(user);
                        if (isInserted) {
                            System.out.println("Se registro sin problemas");
                            State.setActualState(new LoggingState());
                            mesajeError.setText("Registrado Correctamente");
                            mesajeError.setVisibleTime(250);
                        } else {
                            mesajeError.setText("Este correo ya esta registrado");
                            mesajeError.setVisibleTime(250);
                        }
                    } catch (Exception e) {
                        mesajeError.setText("Error de conexión revise su conexion a internet");
                        mesajeError.setVisibleTime(250);
                    }
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
        mesajeError.update();
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
        mesajeError.darw(g);
    }

    /**
     * Check if the email is correct
     * 
     * @param email
     * @return
     */
    private boolean isEmail(String email) {
        return Pattern.compile("^(?=.{1,64}@)[A-Za-z0-9_-]+(\\.[A-Za-z0-9_-]+)*@"
                + "[^-][A-Za-z0-9-]+(\\.[A-Za-z0-9-]+)*(\\.[A-Za-z]{2,})$")
                .matcher(email)
                .matches();
    }

    /**
     * Check if the pass has minimal 8 characters
     * 
     * @param pass
     * @return
     */
    private boolean isPasswd(String pass) {
        return pass.trim().length() >= 8;
    }

    /**
     * Check if the name has minimal 4 characters
     * 
     * @param name
     * @return
     */
    private boolean isName(String name) {
        return name.trim().length() >= 4;
    }

    private boolean isTel(String tel) {
        return Pattern.compile("^\\d{9}$")
                .matcher(tel)
                .matches();
    }
}

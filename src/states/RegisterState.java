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
import ui.MessageError;
import ui.TextBox;

/**
 * class RegisterState
 * Clase que probee de una interfaz visual para que el usuario pueda registrarse
 * en la base de datos
 * 
 * @author Javi.LeL
 * @version 1.0
 */
public class RegisterState extends State {
    Button[] buttons = new Button[2];
    TextBox[] textBoxs = new TextBox[4];
    MessageError messageError;

    /**
     * Constructor basico para crear un objeto de tipo RegisterState
     */
    public RegisterState() {
        // Se generaran las cajas de texto y se almcenaran en un array
        textBoxs[0] = new TextBox(Settings.width / 2, Settings.height * 2 / 8, 500, 50, "Correo *", 20);
        textBoxs[1] = new TextBox(Settings.width / 2, Settings.height * 3 / 8, 500, 50, "Contraseña *", 20, true);
        textBoxs[2] = new TextBox(Settings.width / 2, Settings.height * 4 / 8, 500, 50, "Nombre *", 20);
        textBoxs[3] = new TextBox(Settings.width / 2, Settings.height * 5 / 8, 500, 50, "Telefono", 20);
        messageError = new MessageError(Settings.width / 2, Settings.height * 6 / 8, 500, 50);
        // Se generaran los botones
        buttons[0] = new Button(Settings.width * 3 / 5, Settings.height * 5 / 6, "Registrarse >", new Acttion() {
            @Override
            public void accionARealizar() {
                if (!isEmail(textBoxs[0].getText().trim().toLowerCase())) { // Si el correo no es valido
                    // Se lanzara un mensaje de error
                    messageError.setText("El correo no es valido");
                    messageError.setVisibleTime(250);
                } else if (!isPasswd(textBoxs[1].getText())) { // Si la contrasena no es valida
                    // Se lanzara un mensaje de error
                    messageError.setText("La contraseña debe de tener minimo 8 caracteres");
                    messageError.setVisibleTime(250);
                } else if (!isName(textBoxs[2].getText().trim())) { // Si el nombre no es valido
                    // Se lanzara un mensaje de error
                    messageError.setText("Su nombre debe tener minimo 4 carcteres");
                    messageError.setVisibleTime(250);
                } else if (!(textBoxs[3].getText().equals("") || // Si el telefono no es correcto
                        isTel(textBoxs[3].getText()))) {
                    // Se lanzara un mensaje de error
                    messageError.setText("Su numero de telefono no es correcto");
                    messageError.setVisibleTime(250);
                } else {
                    // En cualquier otro caso el registro debe de ser correcto
                    try {
                        // Se creara un UserDTO con los datos recividos
                        UserDTO user = new UserDTO(
                                textBoxs[0].getText().trim().toLowerCase(),
                                textBoxs[1].getText(),
                                textBoxs[2].getText().trim(),
                                textBoxs[3].getText().trim().toLowerCase());
                        // Se trata de insertar el usuario y el resultado se guarda en una varible
                        boolean isInserted = new UserCRUD().insert(user);
                        if (isInserted) { // Si se inserto
                            // Se hace un loggin con los datos
                            LoggingState.loggin(user.getCorreo(), textBoxs[1].getText());
                        } else { // Si no se inserto
                            // Se lanzara un mensaje de error
                            messageError.setText("¡Este correo ya esta registrado!");
                            messageError.setVisibleTime(250);
                        }
                    } catch (Exception e) {
                        // Si no se puede esatblecer una conexion con la base de datos se laza un error
                        messageError.setText("!Error de conexión revise su conexion a internet!");
                        messageError.setVisibleTime(250);
                    }
                }
            }
        });
        buttons[1] = new Button(Settings.width * 2 / 5, Settings.height * 5 / 6, "< Volver", new Acttion() {
            @Override
            public void accionARealizar() {
                // Volvera al apartado de iniciar sesion
                State.setActualState(new LoggingState());
            }
        });
    }

    /**
     * Metodo que realizara todas las operaciones relacionadas con las matematicas
     */
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

    /**
     * Este metodo se encargara de realizar todas las operaciones graficas
     * 
     * @param g
     */
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
        messageError.darw(g);
    }

    /**
     * Revisa si el email es correcto
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
     * Revisa si la contrasena pose 8 caracters
     * 
     * @param pass
     * @return
     */
    private boolean isPasswd(String pass) {
        return pass.trim().length() >= 8;
    }

    /**
     * Revisa si el nombre pose 4 caracters
     * 
     * @param name
     * @return
     */
    private boolean isName(String name) {
        return name.trim().length() >= 4;
    }

    /**
     * Revisa si el telefono pose 9 caracters de tipo numerico 0-9
     * 
     * @param tel
     * @return
     */
    private boolean isTel(String tel) {
        return Pattern.compile("^\\d{9}$")
                .matcher(tel)
                .matches();
    }
}

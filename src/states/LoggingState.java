package states;

import ui.Acttion;
import ui.Button;
import ui.ImgButton;
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
 * class LoggingState
 * Esta clase probee de un loggin visual
 * 
 * @author Javi.LeL
 * @version 1.0
 */
public class LoggingState extends State {
    Button[] buttons = new Button[2];
    TextBox[] textBoxs = new TextBox[2];
    private static MessageError messageError;
    private ImgButton imgButton;

    /**
     * Contrucotor para crear un loggin
     */
    public LoggingState() {
        // Se crean los textbox y se almacenan en un array para la facilidad de su
        // manejo
        textBoxs[0] = new TextBox(Settings.width / 2, Settings.height * 2 / 6, 500, 50, "Correo *", 20);
        textBoxs[1] = new TextBox(Settings.width / 2, Settings.height * 3 / 6, 500, 50, "Contraseña *", 20, true);
        // Se crea un mensaje de error
        messageError = new MessageError(Settings.width / 2, Settings.height * 4 / 6, 500, 50);
        // Se crean los botones y se almacenan en un array para la facilidad de su
        // manejo
        buttons[0] = new Button(Settings.width * 3 / 5, Settings.height * 5 / 6, "Iniciar sesion >", new Acttion() {
            @Override
            public void accionARealizar() {
                try {
                    // Si los campos no son nulos
                    if (textBoxs[0].getText().length() != 0 && textBoxs[1].getText().length() != 0) {
                        // Revisara que los datos sean correctos
                        loggin(textBoxs[0].getText(), textBoxs[1].getText());
                    } else { // Si uno de los campose es nulo
                        // Devolvera un error por pantalla
                        messageError.setText("¡Porfavor rellene todos los campos!");
                        // Durante un tiempo
                        messageError.setVisibleTime(250);
                    }
                } catch (Exception e) { // Si hay algun tipo de problema
                    // Devolvera un error por pantalla
                    messageError.setText("¡Error de conexión revise su conexion a internet!");
                    // Durante un tiempo
                    messageError.setVisibleTime(250);
                }
            }
        });
        buttons[1] = new Button(Settings.width * 2 / 5, Settings.height * 5 / 6, "Registrar", new Acttion() {
            @Override
            public void accionARealizar() {
                // Al presoinar le boton de registrar mandara al usuario al apartado de registro
                State.setActualState(new RegisterState());
            }
        });
        // Se crea un boton con la imagen de la tuerca
        imgButton = new ImgButton(Settings.width - 70, 50, "assets/buttons/cogcustom.png", new Acttion() {

            @Override
            public void accionARealizar() {
                // Al presionar el boton se llevara al usuario al estado de DBState
                State.setActualState(new DBState());
            }

        });
    }

    /**
     * Metodo que realizara todas las operaciones relacionadas con las matematicas
     */
    @Override
    public void update() {
        // Realizara las actualizaciones de los botones
        for (Button button : buttons) {
            button.update();
        }
        // Realizara las actualizaciones de las cajas de texto
        for (TextBox textBox : textBoxs) {
            textBox.update();
        }
        // Realizara las actualizaciones de el mensaje de error
        messageError.update();
        // Realizar las actualizaciones del boton con imagen
        imgButton.update();
    }

    /**
     * Este metodo se encargara de realizar todas las operaciones graficas
     * 
     * @param g
     */
    @Override
    public void draw(Graphics g) {
        // Pondra un fondo
        g.setColor(Constants.cols[0]);
        g.fillRect(0, 0, Settings.width, Settings.height);
        // Pintara los botones
        for (Button button : buttons) {
            button.draw(g);
        }
        // Pintara las cajas de texto
        for (TextBox textBox : textBoxs) {
            textBox.draw(g);
        }
        // Pondra el titulo
        g.setColor(new Color(255, 255, 255));
        Text.drawText(g, "Inicio de Sesión", Settings.width / 2, (int) (Settings.height * .7 / 6), true,
                new Font("Dialog", Font.PLAIN, 50));
        // Pintara el mensaje de error
        messageError.darw(g);
        // Pinta el boton con imagen
        imgButton.draw(g);
    }

    /**
     * Metodo que comprueba que el correo y contrasena del registro son correctos
     * 
     * @param email
     * @param pass
     */
    static void loggin(String email, String pass) {
        UserDTO user = (UserDTO) new UserCRUD().select(email);
        if (user != null && user.getPass().equals(DTOUtils.getMD5(pass))) {
            getLevelsAndReGenState(email);
        } else {
            messageError.setText("¡Error al iniciar sesion! Puede que no este registrado");
            messageError.setVisibleTime(250);
        }
    }

    /**
     * Accede al selector de niveles creados por el usuario que inicio la sesion
     * 
     * @param email
     */
    public static void getLevelsAndReGenState(String email) {
        UserDTO user = (UserDTO) new UserCRUD().select(email);
        // Creara una lista con todos los niveles encontrados con el mismo correo
        @SuppressWarnings("unchecked")
        List<LevelDTO> levels = (List<LevelDTO>) new LevelCRUD().select(user.getCorreo());
        // Se ira a la instancia de Seleccionar niveles
        State.setActualState(new SelectLevel(levels, user));
    }
}

package states;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import db.crud.CRUD;
import settings.Constants;
import settings.Settings;
import ui.Acttion;
import ui.Button;
import ui.Text;
import ui.TextBox;

/**
 * class DBState
 * Clase que probee de una interfaz visual para poder modificar la informacion
 * de conexsion a la base de datos
 * 
 * @author Javi.LeL
 * @version 1.0
 */
public class DBState extends State {
    TextBox[] textBoxs = new TextBox[5];
    Button button;
    final File file = new File("db/db.cfg");

    /**
     * Constructor basico para crear un objeto de tipo DBState
     */
    public DBState() {
        textBoxs[0] = new TextBox(Settings.width / 2, Settings.height * 2 / 8, 500, 50, "IP de la base de datos", 20);
        textBoxs[1] = new TextBox(Settings.width / 2, Settings.height * 3 / 8, 500, 50, "Puerto", 20);
        textBoxs[2] = new TextBox(Settings.width / 2, Settings.height * 4 / 8, 500, 50, "Nombre de la base de datos",
                20);
        textBoxs[3] = new TextBox(Settings.width / 2, Settings.height * 5 / 8, 500, 50, "Usuario de la base de datos",
                20);
        textBoxs[4] = new TextBox(Settings.width / 2, Settings.height * 6 / 8, 500, 50,
                "Contraseña de la base de datos", 20);
        // Obtenemos los datos guardados
        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            textBoxs[0].setText(CRUD.ignoreMetaData(br.readLine()));
            textBoxs[1].setText(CRUD.ignoreMetaData(br.readLine()));
            textBoxs[2].setText(CRUD.ignoreMetaData(br.readLine()));
            textBoxs[3].setText(CRUD.ignoreMetaData(br.readLine()));
            textBoxs[4].setText(CRUD.ignoreMetaData(br.readLine()));
        } catch (Exception e) {
            System.out.println("Error to read the file/n" + e);
        }
        button = new Button(Settings.width / 2, Settings.height * 7 / 8, "Guardar", new Acttion() {
            @Override
            public void accionARealizar() {
                // Al presionar el boton
                // Se guardara la informacion
                saveData(textBoxs[0].getText(), textBoxs[1].getText(), textBoxs[2].getText(), textBoxs[3].getText(),
                        textBoxs[4].getText());
                // Se volvera a cargar el archivo del CRUD
                try {
                    CRUD.readData();
                } catch (IOException e) {
                    System.out.println("Error to read the data\n" + e);
                }
                // Se enviara al usuario al apartado de estado de inicio de sesion
                State.setActualState(new LoggingState());
            }
        });
    }

    /**
     * Metodo que realizara todas las operaciones relacionadas con las matematicas
     */
    @Override
    public void update() {
        // Realizara las actualizaciones de las cajas de texto
        for (TextBox textBox : textBoxs) {
            textBox.update();
        }
        // Realizara las actualizaciones de los botones
        button.update();
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
        // Pintara las cajas de texto
        for (TextBox textBox : textBoxs) {
            textBox.draw(g);
        }
        // Pintara los botones
        button.draw(g);
        // Pinta un titulo
        g.setColor(new Color(255, 255, 255));
        Text.drawText(g, "Configure la base de datos", Settings.width / 2, (int) (Settings.height * .5 / 6), true,
                new Font("Dialog", Font.PLAIN, 50));
    }

    /**
     * Metodo encargado de guardar la informacion en un fichero de texto
     * 
     * @param ip
     * @param port
     * @param dbname
     * @param dbuser
     * @param dbpass
     */
    void saveData(String ip, String port, String dbname, String dbuser, String dbpass) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(file))) {
            bw.write(CRUD.generateMetadata("IP", ip));
            bw.newLine();
            bw.write(CRUD.generateMetadata("Port", port));
            bw.newLine();
            bw.write(CRUD.generateMetadata("DBName", dbname));
            bw.newLine();
            bw.write(CRUD.generateMetadata("User", dbuser));
            bw.newLine();
            bw.write(CRUD.generateMetadata("Pass", dbpass));
            bw.newLine();
        } catch (Exception e) {
            System.out.println("Error for save the data in file/n" + e);
        }
    }
}

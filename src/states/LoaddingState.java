package states;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

import settings.Constants;
import settings.Settings;
import ui.Acttion;
import ui.MessageError;
import ui.Text;
import windows.MainWindow;

/**
 * class LoaddingState
 * Clase que probee de una ventana de carga
 * 
 * @author Javi.LeL
 * @version 1.0
 */
public class LoaddingState extends State {
    private Thread actionBack;
    private Acttion endAction;
    private String textIn, textDraw;
    private MessageError messageError;

    /**
     * Pondra un texto como el titulo realizara una accion en segundo plano y tras
     * acabar nos llevara a un state concreto
     * Construcotor para acciones que no se llevan al siguiente State
     * 
     * @param text
     * @param backAct
     * @param endAction
     */
    public LoaddingState(String text, Acttion backAct, Acttion endAction) {
        // Por si hay cualquier error se mandara un mensaje de erro
        messageError = new MessageError(Settings.width / 2, Settings.height * 4 / 6, 500, 50);
        // Se creara el hilo que actuara por detras
        actionBack = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    // Tardara un segundo en iniciar (Para que se ve un poco la pantalla de carga)
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    // Si hay algun tipo de error al dormir el hilo se saltara esta parte sin avisar
                }
                // Tras esto se lanzara la accion a realizar en segundo plano
                backAct.accionARealizar();
            }
        });
        // Se lanzara el hilo
        actionBack.start();
        this.endAction = endAction;
        this.textIn = text;
        this.textDraw = text;
    }

    /**
     * Construcotor para acciones que se llevan al siguiente State
     * 
     * @param goBack
     * @param text
     * @param backAct
     */
    public LoaddingState(State goBack, String text, Acttion backAct) {
        // Se crea el hilo que se lanzara por detras
        actionBack = new Thread(new Runnable() {
            @Override
            public void run() {
                // Revisara que la accion se haya realizado sin problemas
                boolean action = false;
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                }
                try {
                    // Se realiza la accion
                    backAct.accionARealizar();
                    // Como no ha avido problemas se pone en true
                    action = true;
                } catch (Exception e) {
                    // Con cualquier problema este ira al estado actual
                    State.setActualState(goBack);
                }
                if (!action) { // Si no se realizo la accion
                    // El hilo se dormira un segundo
                    try {
                        Thread.sleep(1000);
                    } catch (Exception e) {
                        // Si hay algun tipo de error al dormir el hilo se saltara esta parte sin avisar
                    }
                    // Se pondra un error de conexion
                    messageError.setText("Error de conexion");
                    // Se pondra visble durante un tiempo especifico
                    messageError.setVisibleTime(1000);
                }
            }
        });
        // Se lanzara el hilo
        actionBack.start();
        this.textIn = text;
        this.textDraw = text;
    }

    /**
     * Metodo que realizara todas las operaciones relacionadas con las matematicas
     */
    @Override
    public void update() {
        // Si la accion no es nula y el hilo no esta vivo
        if (!actionBack.isAlive() && endAction != null) {
            // Hara la accino que llevara al siguiente State
            endAction.accionARealizar();
        }
        // Cada 30 frames
        if (MainWindow.frameCount % 30 == 0) {
            // Se pondra un punto
            textDraw += ".";
            // Tras poner tres puntos
            if (textIn.length() + 4 == textDraw.length()) {
                // Este se reiniciara
                textDraw = textIn;
            }
        }
    }

    /**
     * Este metodo se encargara de realizar todas las operaciones graficas
     * 
     * @param g
     */
    @Override
    public void draw(Graphics g) {
        // Dibujara un fondo
        g.setColor(Constants.cols[0]);
        g.fillRect(0, 0, Settings.width, Settings.height);
        // Con el texto con la animacion de los puntos
        g.setColor(new Color(255, 255, 255));
        Text.drawText(g, textDraw, Settings.width / 2, Settings.height / 2, true, new Font("Dialog", Font.PLAIN, 50));
    }
}

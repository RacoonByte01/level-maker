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
 * LoaddingState
 * 
 * @author JaviLeL
 * @version 1.0
 */
public class LoaddingState extends State {
    private Thread actionBack;
    private Acttion endAction;
    private String textIn, textDraw;
    private MessageError messageError;

    public LoaddingState(String text, Acttion backAct, Acttion endAction) {
        messageError = new MessageError(Settings.width / 2, Settings.height * 4 / 6, 500, 50);

        actionBack = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                }
                // System.out.println("Soy el hilo: " + Thread.currentThread().getName());
                backAct.accionARealizar();
            }
        });
        actionBack.start();
        this.endAction = endAction;
        this.textIn = text;
        this.textDraw = text;
    }

    public LoaddingState(State goBack, String text, Acttion backAct) {
        actionBack = new Thread(new Runnable() {
            @Override
            public void run() {
                boolean action = false;
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                }
                try {
                    backAct.accionARealizar();
                    action = true;
                } catch (Exception e) {
                    State.setActualState(goBack);
                }
                if (!action) {
                    try {
                        Thread.sleep(1000);
                    } catch (Exception e) {
                    }
                    messageError.setText("Error de conexion");
                    messageError.setVisibleTime(1000);
                }
            }
        });
        actionBack.start();
        this.textIn = text;
        this.textDraw = text;
    }

    @Override
    public void update() {
        if (!actionBack.isAlive() && endAction != null) {
            endAction.accionARealizar();
        }
        if (MainWindow.frameCount % 30 == 0) {
            textDraw += ".";
            if (textIn.length() + 4 == textDraw.length()) {
                textDraw = textIn;
            }
        }
    }

    @Override
    public void draw(Graphics g) {
        g.setColor(Constants.cols[0]);
        g.fillRect(0, 0, Settings.width, Settings.height);
        g.setColor(new Color(255, 255, 255));
        Text.drawText(g, textDraw, Settings.width / 2, Settings.height / 2, true, new Font("Dialog", Font.PLAIN, 50));
    }
}

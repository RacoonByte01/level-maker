package windows;

import java.awt.Canvas;
import java.awt.Dimension;
import java.awt.Frame;
import java.awt.Graphics;
import java.awt.Toolkit;
import java.awt.image.BufferStrategy;

import javax.swing.JFrame;

import inputs.Keyboard;
import inputs.Mouse;
import settings.Settings;
import states.*;

/**
 * class MainWindow
 * Clase que probee una ventana extiende de JFrame para ello
 */
public class MainWindow extends JFrame implements Runnable {

    Dimension screenSize;

    private Canvas canvas;
    private Thread thread;
    public static boolean funcionar;
    public static long frameCount;
    BufferStrategy bs;
    Graphics g;

    // inputs
    Keyboard keyboard;
    Mouse mouse;

    // Estado
    public State state;
    // Calcular fps
    private final int FPS;
    private double targetTime;
    private double delta;
    int averageFPS;

    /*
     * Iniciador de la ventana
     */
    public MainWindow() {
        // Ventana al iniciar
        this.setVisible(true);
        this.setTitle("Level Maker");
        this.setSize(Settings.width, Settings.height);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setResizable(false);
        this.setLocationRelativeTo(null);

        if (Settings.fullScreen) {
            this.setExtendedState(Frame.MAXIMIZED_BOTH);
            Settings.width = (int) Toolkit.getDefaultToolkit().getScreenSize().getWidth();
            Settings.height = (int) Toolkit.getDefaultToolkit().getScreenSize().getHeight();
        }

        // Inicializamos los inputs
        keyboard = new Keyboard();
        mouse = new Mouse();

        // Iniciamos el camvas y trabajamos con el
        this.canvas = new Canvas();
        this.canvas.setPreferredSize(new Dimension(Settings.width, Settings.height));
        this.canvas.setMaximumSize(new Dimension(Settings.width, Settings.height));
        this.canvas.setMinimumSize(new Dimension(Settings.width, Settings.height));
        this.canvas.setFocusable(true);
        this.add(canvas);

        this.canvas.addKeyListener(keyboard);
        this.canvas.addMouseListener(mouse);
        this.canvas.addMouseMotionListener(mouse);
        this.canvas.addMouseWheelListener(mouse);

        // Creamos el limitador de FPS
        this.FPS = 60;
        this.targetTime = 1000000000 / FPS;
        this.delta = 0;
        this.averageFPS = FPS;

        // Creo el hilo
        thread = new Thread(this);
    }

    @Override
    public void run() {
        long now = 0;
        long lastTime = System.nanoTime();
        int frames = 0;
        long time = 0;

        do {

            now = System.nanoTime();
            delta += (now - lastTime) / targetTime;
            time = now - lastTime;

            lastTime = System.nanoTime();
            if (delta >= 1) {
                state = State.getActualState();
                // System.out.println(averageFPS);
                update();
                draw();
                delta--;
                frames++;
            }
            if (time >= 1000000000) {
                averageFPS = frames;
                frames = 0;
                time = 0;
            }
        } while (funcionar);
        stop();
    }

    /**
     * Realiza los calculos de la pantalla
     */
    void update() {
        frameCount++;
        state.update();
    }

    /**
     * Craga los graficos generales de la pantalla
     */
    void draw() {
        bs = canvas.getBufferStrategy();
        if (bs == null) {
            canvas.createBufferStrategy(3);
            return;
        }
        g = bs.getDrawGraphics();
        state.draw(g);
        g.dispose();
        bs.show();
    }

    /**
     * Inicia el ciclo de la pantalla y el thread
     */
    public void start() {
        funcionar = true;
        State.setActualState(new LoggingState());
        state = State.getActualState();
        thread.run();
    }

    /**
     * Detiene el thread de la pantalla
     */
    void stop() {
        try {
            System.exit(0);
            thread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
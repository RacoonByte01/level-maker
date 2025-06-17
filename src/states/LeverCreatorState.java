package states;

import java.awt.Graphics;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import db.crud.LevelCRUD;
import db.dto.DTOUtils;
import db.dto.LevelDTO;
import inputs.Keyboard;
import inputs.Mouse;
import raccoon.PVector;
import settings.Constants;
import settings.Settings;
import ui.Acttion;
import ui.BlockCard;
import ui.ImgButton;
import gameObjects.Cube;
import gameObjects.Player;

/**
 * class LeverCreatorState
 * Esrta clase probee de un editor de niveles visual
 * 
 * @author Javi.LeL
 * @version 1.0
 */
public class LeverCreatorState extends State {
    private PVector loc, lastClick, locCam;
    public LevelDTO level;
    private boolean isPressed;
    public List<Cube> grid;
    public static int angle;
    private List<BlockCard> blockCards;
    private int scroll;
    ImgButton imgButton;

    /**
     * Constructor de la instancia del creador de niveles
     * 
     * @param level
     */
    @SuppressWarnings("unchecked")
    public LeverCreatorState(LevelDTO level) {
        // El punto de inicio de visualizacion se en el medio de la pantalla
        this.loc = new PVector(Settings.width / 2, Settings.height / 2 - Settings.cellSize * 2);
        // La camara se localiza en 0, 0
        this.locCam = new PVector(0, 0);
        this.isPressed = false;
        this.level = level;
        if (level.getData() != null) {
            try {
                this.grid = (List<Cube>) DTOUtils.stringToObj(level.getData());
            } catch (ClassNotFoundException | IOException e) {
                System.out.println("Error al cargar la info:\n" + e);
            }
        } else {
            this.grid = new ArrayList<>();
            // Spawnea el jugador
            this.grid.add(new Player(new PVector(0, 0), "assets/blocks/player/player.png"));
        }
        // La tecla pulsada por el teclado se reinicia
        Keyboard.key = null;
        // El angulo es 0
        LeverCreatorState.angle = 0;
        // Se crea un array list con todas las cartas de los bloques a representar
        blockCards = new ArrayList<>();
        // Se obtienen todos las imagenes de los bloques deseados
        blockCards.add(new BlockCard(0, "assets/blocks/dirt/dirt.png"));
        blockCards.add(new BlockCard(1, "assets/blocks/grass/grass-all.png"));
        blockCards.add(new BlockCard(2, "assets/blocks/grass/grass-up.png"));
        blockCards.add(new BlockCard(3, "assets/blocks/grass/grass-corner.png"));
        blockCards.add(new BlockCard(4, "assets/blocks/grass/grass-up-down.png"));
        blockCards.add(new BlockCard(5, "assets/blocks/grass/grass-up-down-side.png"));
        // Se crea un boton de imagen con el simbolo de engranaje
        imgButton = new ImgButton(Settings.width - 32 * 2, 32, "assets/buttons/cogcustom.png",
                new Acttion() {
                    @Override
                    public void accionARealizar() {
                        // Ya que la accion del guardado puede tardar unos segundos se crea una
                        // instancia LoaddingState para que actue como una ventana de carga
                        State.setActualState(new LoaddingState("Guardando", new Acttion() {
                            @Override
                            public void accionARealizar() {
                                // Guardara la infomacion
                                saveData();
                            }
                        }, new Acttion() {
                            @Override
                            public void accionARealizar() {
                                // Tras esto se ira al apartado ModificatorLevelFileState
                                State.setActualState(new ModificatorLevelFileState(level));
                            }
                        }));
                    }
                });
    }

    /**
     * Metodo que realizara todas las operaciones relacionadas con las matematicas
     */
    @Override
    public void update() {
        // Se realizaran las operaciones de la camara
        camera();
        // Se haran las acrualizaciones del boton image
        imgButton.update();
        // Se haran las actualizaciones las cartas de bloque
        for (BlockCard blockCard : blockCards) {
            blockCard.update(scroll);
        }
        // Si el raton esta dentro del apartado de seleccion de bloques
        if (Mouse.x >= 0 && Mouse.x <= Settings.cellSize * 2) {
            if (Mouse.mouseWheelDown) { // Y hace scroll abajo
                if (scroll > -(blockCards.size() - 11) * 60) { // Siempre y cuando haya cartas suficientes estas bajaran
                    scroll -= 72;
                }
            } else if (Mouse.mouseWheelUp) { // Y si se hace scroll arriba estas subiran
                if (scroll < 0) {
                    scroll += 72;
                }
            }
            // Reiniciamos la ruleta del raton (Se especifica en su clase)
            Mouse.mouseWheelDown = false;
            Mouse.mouseWheelUp = false;
        } else {
            if (Mouse.mousePressed) { // Si el raton es pulsado en la pantalla
                /*
                 * Se calcula deonde crear el bloque
                 * Para ello se suma la posicion del raton y la posicion de la camara
                 */
                PVector press = new PVector((int) (Mouse.x / Settings.cellSize) - (int) (locCam.x / Settings.cellSize),
                        (int) (Mouse.y / Settings.cellSize) - (int) (locCam.y / Settings.cellSize));
                if (Mouse.left) { // Si se presiona con click izquierdo
                    boolean wasLoc = false;
                    // Se revisa que no hay ningun cubo localizado en esta posicion
                    for (Cube cube : grid) {
                        // Si alguno de los cubos esta en esta posiocion wasLoc se pondra a true
                        if (cube.isLoc(new PVector(press.x * Settings.cellSize, press.y * Settings.cellSize))) {
                            wasLoc = true;
                        }
                    }
                    // Tras verificar que ningun cubo este en esta posicion
                    if (!wasLoc && BlockCard.getAssetSeleceted() != null) {
                        // Se crea un cubo en la posicion seleccionada con el asset y el angulo elegido
                        // por el jugador
                        grid.add(new Cube(press, BlockCard.getAssetSeleceted(), angle));
                    }
                    // Si se presiona el raton izquierdo siempre y cuando no sea 0, 0
                } else if (Mouse.right && !(press.x == 0 && press.y == 0)) {
                    // Se eliminara el cubo de la posicion seleccionada
                    for (int i = 0; i < grid.size(); i++) {
                        if (grid.get(i).isLoc(new PVector(press.x * Settings.cellSize, press.y * Settings.cellSize))) {
                            grid.remove(i);
                        }
                    }
                }
            }
            // Si se presiona el boton r del teclado
            if (Keyboard.key != null && Character.toLowerCase(Keyboard.key) == 'r') {
                // Se reiniciara la tecla
                Keyboard.key = null;
                // Y su angulo aumentara en 1 los valores que pueden tomar son 0, 1, 2, 3
                angle = (angle + 1) % 4;
            } else if (Keyboard.key != null && Keyboard.key == 27) { // Si se presiona escape
                // Se guardara la informacion del nivel creado
                saveData();
                // Se mandara al apartado de seleccionar nivel
                LoggingState.getLevelsAndReGenState(SelectLevel.user.getCorreo());
            }
        }
    }

    /**
     * Metodo que se encarga de guardar el nivel
     */
    private void saveData() {
        if (level.getId() == null) { // Si su id es nula (no esta en la base de datos)
            // Se creara un nuevo registro en la base de datos
            try {
                // Se actualizara la informacion del nivel
                level.setData(DTOUtils.objToString((ArrayList<Cube>) grid));
            } catch (IOException e) {
                // Si se produce cualquier problema devolvera un error por consola
                System.out.println("Error to save (insert):\n" + e);
            }
            // Y se insertara el nivel en la base de datos
            new LevelCRUD().insert(level);
        } else { // Si no posee id (esta en la base de datos)
            // Se actualizara el registro ya existente en la base de datos
            try {
                // Se actualizara la informacion del nivel
                level.setData(DTOUtils.objToString((ArrayList<Cube>) grid));
            } catch (IOException e) {
                // Si algo va mal lanzara un error por consola
                System.out.println("Error to save (update):\n" + e);
            }
            // Se actualizara el nivel correspondiente con lo cambios realizados por el
            // usuario
            new LevelCRUD().update(level);
        }
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
        // Movera la camara a su posicion correspondiente
        g.translate((int) locCam.x, (int) locCam.y);
        // Se cargaran todos los cubos y se representan
        for (Cube cube : grid) {
            cube.draw(g);
        }
        // La camara se pondra en 0, 0
        g.translate((int) -locCam.x, (int) -locCam.y);
        // Creara un cuadro donde iran las cartas de los bloques
        g.setColor(Constants.cols[5]);
        g.fillRect(0, 0, Settings.cellSize * 2, Settings.height);
        // Representara visualmente todas las cartas
        for (BlockCard blockCard : blockCards) {
            blockCard.draw(g, scroll, angle);
        }
        // Y representara el boton
        imgButton.draw(g);
    }

    /**
     * Metodo que hara los calculos de la acmara
     */
    protected void camera() {
        // Si el raton se presiona y es el boton del centro
        if (Mouse.mousePressed && Mouse.center) {
            if (!isPressed) { // Si no estaba presionado de antes
                // Y calculara donde presiono el usuario
                lastClick = new PVector(Mouse.x, Mouse.y);
                // Se pondra compo que estaba presionado de antes
                isPressed = true;
            }
        } else { // Si no
            // El ultimo click sera nulo
            lastClick = null;
            // Y si estaba presionado se pondra a false
            isPressed = false;
        }
        if (lastClick != null) { // Si el ultimo click no es nulo
            // Se sumara a la localizacion el mouse mas el ultimo lugar donde este se
            // presiono
            loc.add(new PVector(Mouse.x - lastClick.x, Mouse.y - lastClick.y));
            // Y su ultima pulsacion sera la posicion actual del raton
            lastClick = new PVector(Mouse.x, Mouse.y);
        }
        // Se calcula la localizacion de la camara
        locCam = new PVector((int) (loc.x / Settings.cellSize) * Settings.cellSize,
                (int) (loc.y / Settings.cellSize) * Settings.cellSize);
    }
}

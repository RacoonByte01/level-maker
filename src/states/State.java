package states;

import java.awt.Graphics;

/**
 * class State
 * Clase que actuara como base para los poseteriores States
 * 
 * @author Javi.LeL
 * @version 1.3
 */
public abstract class State {
    private static State actualState = null;

    /**
     * Este metodo devuleve el estado actual
     * 
     * @return the state actual
     */
    public static State getActualState() {
        return actualState;
    }

    /**
     * Este metodo establece el estado
     * 
     * @param state The new state how state actual will be
     */
    public static void setActualState(State state) {
        State.actualState = state;
    }

    /**
     * Metodo que realizara todas las operaciones relacionadas con las matematicas
     */
    public abstract void update();

    /**
     * Este metodo se encargara de realizar todas las operaciones graficas
     * 
     * @param g
     */
    public abstract void draw(Graphics g);
}

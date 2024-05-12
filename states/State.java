package states;

import java.awt.Graphics;

/**
 * Class how use like base of the rest of states
 * 
 * @author JaviLeL
 * @version 1.3
 */
public abstract class State {
    /* This State save the state actual */
    private static State actualState = null;

    /**
     * This function returns the state of a window
     * 
     * @return the state actual
     */
    public static State getActualState() {
        return actualState;
    }

    /**
     * This function set the actualState to the new state
     * 
     * @param state The new state how state actual will be
     */
    public static void setActualState(State state) {
        State.actualState = state;
    }

    /**
     * Here we put all the math operaions
     */
    public abstract void update();

    /**
     * Here we put all the operation relative to grapics
     * 
     * @param g Grapics of window
     */
    public abstract void draw(Graphics g);
}

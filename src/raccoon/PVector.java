package raccoon;

import java.io.Serializable;

/**
 * This class make more easy work with vectors
 * 
 * @author JaviLeL
 * @version 1.6
 */
public class PVector implements Serializable {
    public float x, y, z;

    /**
     * This constructor make a bidimensional Vector
     * 
     * @param x Set the parameter x of PVector
     * @param y Set the parameter y of PVector
     */
    public PVector(float x, float y) {
        this.x = x;
        this.y = y;
    }

    /**
     * This constructor make a tridimensional Vector
     * 
     * @param x Set the parameter x of PVector
     * @param y Set the parameter y of PVector
     * @param z Set the parameter z of PVector
     */
    public PVector(float x, float y, float z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    /**
     * This function can add other PVector to this PVector
     * 
     * @param add PVector how add to this PVector
     */
    public void add(PVector add) {
        x += add.x;
        y += add.y;
        /*
         * If you try to add a tridimensional PVector with bidimensonal PVector,
         * or vice versa you gona get a excption so you should be carefully
         */
        z += add.z;
    }

    /**
     * This function can substract another PVector
     * 
     * @param sum
     */
    public void sub(PVector sub) {
        x += sub.x;
        y += sub.y;
        /*
         * If you try to sub a tridimensional PVector with bidimensonal PVector,
         * or vice versa you gona get a excption so you should be carefully
         */
        z += sub.z;
    }

    /**
     * This function can set the limit of this Vector
     * TODO Make it to work with tridimensional vector
     * 
     * @param e Integer of max module can equals (or limit) of the vector
     */
    public void limit(int e) {
        int f = module() / e;
        if (f != 0) {
            x /= f;
            y /= f;
        }
    }

    /**
     * This funcion can make the module of Vector
     * TODO Make it to work with tridimensional vector
     * 
     * @return the integer of module of vector
     */
    public int module() {
        /*
         * To calculate the module of Vector we must add the x and y squared
         */
        return (int) Math.sqrt(Math.pow(x, 2) + Math.pow(y, 2));
    }
}
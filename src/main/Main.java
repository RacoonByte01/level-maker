package main;

import java.io.IOException;

import db.crud.CRUD;
import windows.MainWindow;

/**
 * class Main
 * Esta clase es la encargada de lanzar todo el programa
 * 
 * @author Javi.LeL
 * @version 1.1
 */
public class Main {
    public static MainWindow mainWindow;

    /**
     * Metodo que lanza el programa
     * 
     * @param args
     */
    public static void main(String[] args) {
        // Obtiene los datos del archivo de configuracionde la base de datos
        try {
            CRUD.readData();
        } catch (IOException e) {
            e.printStackTrace();
        }
        // Crea la ventana principal
        mainWindow = new MainWindow();
        // Lanza la ventana principal
        mainWindow.start();
    }
}
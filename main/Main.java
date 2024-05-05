package main;

import java.io.IOException;

import db.crud.CRUD;
import windows.MainWindow;

/**
 * Class how purpose is launch the program
 * 
 * @author JaviLeL
 * @version 1.1
 */
public class Main {
    public static MainWindow mainWindow;

    public static void main(String[] args) {
        try {
            CRUD.readData();
        } catch (IOException e) {
            e.printStackTrace();
        }
        mainWindow = new MainWindow();
        mainWindow.start();
    }
}
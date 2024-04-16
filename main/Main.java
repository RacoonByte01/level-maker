package main;

import windows.MainWindow;

/**
 * Class how purpose is launch the program
 * 
 * @author JaviLeL
 * @version 1.0
 */
public class Main {
    public static MainWindow mainWindow;

    public static void main(String[] args) {
        mainWindow = new MainWindow();
        mainWindow.start();
    }
}
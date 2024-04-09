package main;

import windows.MainWindow;

/**
 * Class how purpose is launch the program
 * 
 * @author JaviLeL
 * @version 1.0
 */
public class Main {
    public static void main(String[] args) {
        MainWindow mainWindow = new MainWindow();
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            System.out.println(e);
        }
        mainWindow.start();
    }
}
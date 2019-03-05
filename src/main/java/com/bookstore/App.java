package com.bookstore;

/**
 * Application for managing and keeping records of books, magazines,
 * papers etc for book stores.
 *
 * @author Christoffer A Træen
 * @version 1
 */
public class App {

    /**
     * Application entry
     *
     * @param args Application arguments
     */
    public static void main(String[] args) {

        // CREATE USER INTERFACE
        ApplicationUI ui = new ApplicationUI(new MagazineRegister());
        ui.start();

    }

}

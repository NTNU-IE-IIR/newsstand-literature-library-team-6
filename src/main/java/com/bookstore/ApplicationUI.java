package com.bookstore;

import com.bookstore.readables.Magazine;

import java.util.*;

/**
 * Handles the user interface for the application.
 * Prints a menu in the terminal for the user to interact with the application.
 *
 * @author Christoffer A Træen
 * @version 1
 */
public class ApplicationUI {

    /**
     * Application menu choices which will be display to the user
     */
    private final String[] menuItems = {
        "Add new magazine",
        "List all magazines",
        "Search magazines by title",
        "Search magazines publisher",
        "Delete a magazine by ID",
        "Quit application"
    };

    private HashMap<Integer, Magazine> lastFoundMagazines;

    /**
     * The Magazine register to get and insert magazines.
     */
    private final MagazineRegister magazineRegister;

    /**
     * Set the magazine register to field for use.
     *
     * @param magazineRegister The magazine register which holds on magazines.
     */
    public ApplicationUI(MagazineRegister magazineRegister) {
        this.lastFoundMagazines = new HashMap<>();
        this.magazineRegister = magazineRegister;
    }

    /**
     * Starts the user interface
     */
    public void start() {

        boolean quitApplication = false;

        while (!quitApplication) {
            try {
                this.printMenu();
                int menuChoice = this.getMenuChoice();
                switch (menuChoice) {
                    case 1:
                        this.addNewMagazine();
                        break;
                    case 2:
                        this.listAllMagazines();
                        break;
                    case 3:
                        this.searchMagazinesByTitle();
                        break;
                    case 4:
                        this.searchMagazinesByPublisher();
                        break;
                    case 5:
                        this.removeMagazine();
                        break;
                    case 6:
                        this.println("\nClosing...\n" +
                                "Thank you for using Magazine Register.");
                        quitApplication = true;
                        break;
                    default:
                }
                pressKeyToContinue();
            } catch (InputMismatchException e) {
                this.println("Please choose a number between 1 and "
                        + menuItems.length);
            }
        }

    }

    /**
     * Get the user input choice. Returns a number between 1 and max menu
     * items,{@see #menuItems}.
     *
     * @return A number between 1 and max menu items {@see menuItems}
     * @throws InputMismatchException thrown if input is out of valid range
     */
    private int getMenuChoice() {

        int menuSelection = this.getIntInput();
        if (menuSelection < 1 || menuSelection > menuItems.length) {
            throw new InputMismatchException();
        }
        return menuSelection;
    }

    /**
     * Prints the application menu.
     */
    private void printMenu() {
        StringBuilder menuString = new StringBuilder();
        menuString.append("\n****** Magazine Register v1 ****** \n\n");

        int menuNumber = 1;
        for (String menuItem : menuItems) {
            menuString.append(menuNumber + ". " + menuItem + "\n");
            menuNumber++;
        }

        menuString.append("\nEnter a number between (1 - " +
                this.menuItems.length + ") to navigate:");
        this.println(menuString.toString());
    }

    /**
     * Adds a new magazine to the register by
     * information provided by the user.
     */
    private void addNewMagazine() {
        String magazineTitle;
        String magazinePublisher;
        int publicationsPerYear;
        Scanner scanner = new Scanner(System.in);

        this.println("\nAdd magazine by title\n");

        this.println("Title:");
        magazineTitle = scanner.nextLine();

        this.println("Publisher:");
        magazinePublisher = scanner.nextLine();

        this.println("Publications per year:");
        publicationsPerYear = getIntInput();

        try {
            Magazine magazine = new Magazine(
                    magazineTitle,
                    magazinePublisher,
                    publicationsPerYear);
            if (this.magazineRegister.addMagazine(magazine)) {
                this.println("\nAdded magazine to register.");
            } else {
                this.println("\nCould not add magazine... unknown error.");
            }
        } catch (DuplicateEntryException e) {
            this.println(e.toString());

        } catch (IllegalArgumentException e) {
            this.println(e.toString());
            if (this.tryAgain()) {
                this.addNewMagazine();
            }
        }

    }

    /**
     * Lists all magazines stored in the magazine register.
     */
    private void searchMagazinesByPublisher() {
        String magazinePublisher;
        Scanner scanner = new Scanner(System.in);

        this.println("\nSearch magazines by publisher\n");
        this.println("Publisher:");

        magazinePublisher = scanner.nextLine().trim();

        Iterator<Magazine> foundMagazines = this.magazineRegister
                .searchMagazinesByPublisher(magazinePublisher);
        this.addFoundMagazines(foundMagazines);
        this.printLastFoundMagazines();

    }

    /**
     * Adds magazines to map of found magazines with ID
     * for referencing. Clears the existing collection
     * before adding new entries.
     *
     * @param magazines The magazines to add
     */
    private void addFoundMagazines(Iterator<Magazine> magazines) {
        this.lastFoundMagazines.clear();
        int i = 0;
        while (magazines.hasNext()) {
            this.lastFoundMagazines.put(i, magazines.next());
            i++;
        }
    }

    /**
     * Get all magazine in register and add call
     * {@link #addFoundMagazines} so we can use them later
     * and then call {@link #printLastFoundMagazines} to print them out.
     * displays message if no magazine is found
     */
    private void listAllMagazines() {
        this.println("\nListing all magazines\n");
        Iterator<Magazine> magazines = this.magazineRegister.getAllMagazines();

        if (magazines.hasNext()) {
            this.addFoundMagazines(magazines);
            this.printLastFoundMagazines();
        } else {
            this.println("There are no magazines to display.");
        }
        this.println("");
    }

    /**
     * Gets input from the user and searches the register for that input
     * and call {@link #addFoundMagazines} so we can use them later
     * and then call {@link #printLastFoundMagazines} to print them out.
     *
     */
    private void searchMagazinesByTitle() {
        String searchString;
        Scanner scanner = new Scanner(System.in);

        this.println("\nSearch magazines by title\n");
        this.println("Title:");

        searchString = scanner.nextLine().trim();

        Iterator<Magazine> foundMagazines = this.magazineRegister
                .searchMagazinesTitles(searchString);
        this.addFoundMagazines(foundMagazines);
        this.printLastFoundMagazines();
    }

    /**
     * Prints all found magazines by Id, Title, Publisher and releases per year
     */
    private void printLastFoundMagazines() {

        StringBuilder magazinesString = new StringBuilder();
        if (this.lastFoundMagazines.isEmpty()) {
            magazinesString.append("\nCan not find any magazines");
        } else {
            magazinesString.append("ID | TITLE | PUBLISHER | " +
                    "PUBLICATIONS YEAR\n");

            this.lastFoundMagazines.forEach((id, magazine) -> {
                magazinesString.append(id);
                magazinesString.append(" | ");
                magazinesString.append(magazine.getTitle());
                magazinesString.append(" | ");
                magazinesString.append(magazine.getPublisher());
                magazinesString.append(" | ");
                magazinesString.append(magazine.getPublicationsPerYear());
                magazinesString.append("\n");
            });
        }
        this.println(magazinesString.toString());
    }

    /**
     * Removes a magazine from the register by the ID displayed
     * when getting magazines from the register.
     * Displays error message if it could`nt delete the magazine.
     * If wrong id is entered, prompt for retry
     */
    private void removeMagazine() {
        this.println("\nThe id of a found magazine: ");
        Integer id = this.getIntInput();
        try {
            Magazine magazine = this.lastFoundMagazines.get(id);
            if (magazine != null) {
                if (this.magazineRegister.deleteMagazine(magazine)) {
                    this.println("Removed magazine");
                    this.lastFoundMagazines.remove(id);
                } else {
                    this.println("Could not remove magazine");
                }
            }
        } catch (IndexOutOfBoundsException e) {
            this.println("Magazine id not valid.");
            if (this.tryAgain()) {
                this.removeMagazine();
            }
        }
    }

    /**
     * Reads the Scanner for number and returns it,
     * if no valid number is passed, prints an error message and
     * ask try to get input again.
     * <p>
     * Workaround for the scanner, because it goes into infinite loop if
     * a non int is used as input type when expecting int.
     *
     * @return returns the number entered by the user
     */
    private int getIntInput() {
        Scanner scanner = new Scanner(System.in);
        int number = 0;
        try {
            number = scanner.nextInt();
        } catch (InputMismatchException e) {
            this.println("Please enter a whole number.");
            number = getIntInput();
        }
        return number;
    }

    /**
     * Prints action confirmation as yes/no question
     * and returns a boolean for the action.
     * If not a valid choice is entered, it will ask until a valid choice
     * is entered.
     *
     * @return Returns true if answare is yes else false if answare is no
     */
    private boolean confirmAction() {
        boolean confirmed = false;
        Scanner scanner = new Scanner(System.in);
        String choice;
        this.print("Enter [Y / N]: ");
        choice = scanner.next();
        if (choice.equalsIgnoreCase("y") || choice.equalsIgnoreCase("n")) {
            if (choice.equalsIgnoreCase("y")) {
                confirmed = true;
            }
        } else {
            this.println("Not a valid choice... Try again");
            confirmed = this.confirmAction();
        }
        return confirmed;
    }

    /**
     * Prompt for a retry, and returns a boolean for
     * your choice
     *
     * @return Returns true of answares is yes, else false
     */
    private boolean tryAgain() {
        this.println("Would you try again?");
        return this.confirmAction();
    }

    /**
     * Prompts for a key press to continue program
     */
    private void pressKeyToContinue() {
        this.println("Press a key to continue...");
        Scanner scanner = new Scanner(System.in);
        scanner.nextLine();
    }

    /**
     * Prints to console on same line
     *
     * @param string The string to print
     */
    private void print(String string) {
        System.out.print(string);
    }

    /**
     * Prints to console on new line
     *
     * @param string The string to print
     */
    private void println(String string) {
        System.out.println(string);
    }

}

package com.bookstore;

import com.bookstore.readables.Magazine;

import java.util.HashMap;
import java.util.ArrayList;
import java.util.Iterator;

/**
 * Register for storing magazines
 * <ul>
 * <li>Add a magazine</li>
 * <li>Remove a magazine</li>
 * <li>Get a magazine by title</li>
 * <li>Get magazines by a publisher</li>
 * </ul>
 *
 * @author Christoffer A Træen
 * @version 1
 */
public class MagazineRegister {

    /**
     * Holds a collection of magazines
     */
    private ArrayList<Magazine> magazines;

    /**
     * Constructor
     * <p>
     * Instantiate magazine ArrayList
     */
    public MagazineRegister() {

        this.magazines = new ArrayList<>();
    }

    /**
     * Returns a magazine at given index
     *
     * @param index The index to get magazine from
     * @return Returns a Magazine if found else null
     */
    public Magazine getMagazineAtIndex(int index) {
        return this.magazines.get(index);
    }

    /**
     * Returns a list of magazines by a publisher
     * if no magazines are found return an empty list.
     * This is case insensitive
     *
     * @param publisher The publisher of a magazine
     * @return Returns a List of {@link Magazine} or an empty list
     * if no elements is found
     */
    public Iterator<Magazine> searchMagazinesByPublisher(String publisher) {

        if (publisher == null) {
            throw new IllegalArgumentException("Null passed as argument.");
        }

        ArrayList<Magazine> foundMagazines = new ArrayList<>();

        if (!publisher.isEmpty()) {
            for (Magazine magazine : this.magazines) {
                if (magazine.getPublisher().toLowerCase()
                        .contains(publisher.toLowerCase())) {
                    foundMagazines.add(magazine);
                }
            }
        }

        return foundMagazines.iterator();

    }

    /**
     * Add a magazine to the collection if the magazine does not already
     * exist. It checks the collection for same title and publisher
     *
     * @param magazine The magazine to add
     * @return Returns boolean for success / failure
     * @throws DuplicateEntryException  If there is a magazine with same title and publisher
     * @throws IllegalArgumentException If passed object is null
     */
    public boolean addMagazine(Magazine magazine) {

        if (magazine == null) {
            throw new IllegalArgumentException("Null passed as argument");
        }

        if (hasDuplicateOf(magazine)) {
            throw new DuplicateEntryException("A magazine with that " +
                    "title and publisher already exists.");
        }
        return this.magazines.add(magazine);
    }

    /**
     * Check if a magazine with title and publisher exists
     * to check for duplicate entry
     *
     * @param magazine The magazine to check for duplicate
     * @return Returns true if duplicate else false
     */
    public boolean hasDuplicateOf(Magazine magazine) {
        boolean foundDuplicate = false;
        Iterator<Magazine> foundMagazines = this.searchMagazinesTitles
                (magazine.getTitle());

        while (foundMagazines.hasNext() && !foundDuplicate) {
            if (foundMagazines.next().getPublisher()
                    .equalsIgnoreCase(magazine.getPublisher())) {
                foundDuplicate = true;
            }
        }

        return foundDuplicate;
    }

    /**
     * Search all magazines titles by a string
     * and returns a list of all found magazines matching the string
     * or empty list if none are found. This is case insensitive
     *
     * @param searchString The string to search for
     * @return Returns a list of <code>Magazine</code>s or empty list.
     * @throws IllegalArgumentException If argument is null
     */
    public Iterator<Magazine> searchMagazinesTitles(String searchString) {

        ArrayList<Magazine> foundMagazines = new ArrayList<>();

        if (searchString == null) {
            throw new IllegalArgumentException("Null passed as argument.");
        }

        if (!searchString.isEmpty()) {
            for (Magazine magazine : this.magazines) {
                if (magazine.getTitle().toLowerCase()
                        .contains(searchString.toLowerCase())) {
                    foundMagazines.add(magazine);
                }
            }
        }

        return foundMagazines.iterator();
    }

    /**
     * Returns all magazines stored in the collection
     *
     * @return Returns an ArrayList of all magazines stored
     */
    public Iterator<Magazine> getAllMagazines() {
        return this.magazines.iterator();
    }

    /**
     * Deletes the provided magazine, and
     * call the logger to output status of the deletion.
     *
     * @param magazineToDelete The magazine to delete
     * @return Returns boolean true if removed else false
     */
    public boolean deleteMagazine(Magazine magazineToDelete) {
        return this.magazines.remove(magazineToDelete);
    }

}

package com.bookstore.readables;

/**
 * Holds information about a magazine,
 * It contains of a title, publisher and publications per year
 *
 * @author Christoffer A Træen
 * @version 1
 */
public class Magazine {

    /**
     * The magazine title
     */
    private String title;

    /**
     * The publisher name
     */
    private String publisher;

    /**
     * Number of publications per year of this magazine
     */
    private int publicationsPerYear;

    /**
     * Assign the passed variables to local fields
     *
     * @param title The title of the magazine
     * @param publisher The publisher of the magazine
     * @param publicationsPerYear Number of publications per year
     */
    public Magazine(String title, String publisher, int publicationsPerYear) {
        this.setTitle(title);
        this.setPublisher(publisher);
        this.setPublicationsPerYear(publicationsPerYear);
    }

    private void setTitle(String title) {
        if (title == null) {
            title = "";
        }

        if (title.isEmpty()) {
            throw new IllegalArgumentException("Title can not be empty");
        }
        this.title = title.trim();
    }
    private void setPublisher(String publisher) {
        if (publisher == null) {
            publisher = "";
        }

        if (publisher.isEmpty()) {
            throw new IllegalArgumentException("Publisher can not be empty");
        }

        this.publisher = publisher.trim();
    }
    private void setPublicationsPerYear(int publicationsPerYear) {
        publicationsPerYear = Math.abs(publicationsPerYear);
        this.publicationsPerYear = publicationsPerYear;
    }

    /**
     * Returns the title of the magazine
     * @return returns the magazine title name
     */
    public String getTitle() {

        return this.title;
    }

    /**
     * Returns the publisher of the magazine
     * @return returns the magazine publisher
     */
    public String getPublisher() {

        return this.publisher;
    }


    /**
     * Returns the number of publications per year
     * @return Returns number of publications per year
     */
    public int getPublicationsPerYear() {

        return this.publicationsPerYear;
    }
}

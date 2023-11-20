package entity;

import java.util.ArrayList;

public class StoryBook {

    private String title;

    private ArrayList<Page> pages;

    /**
     * Constructs a new Storybook entity
     * Requires: title and pages.
     *
     * @param title
     * @param pages
     */
    public StoryBook(String title, ArrayList<Page> pages) {
        this.title = title;
        this.pages = pages;
    }

    /**
     * Returns Storybook's title
     */
    public String getTitle() { return title; }

    /**
     * Returns all pages in storybook
     */
    public ArrayList<Page> getPages() { return pages; }

    /**
     * Set a new title for the storybook
     */
    public void setTitle(String newTitle) { title = newTitle; }

    /**
     * Set a new set of pages for this storybook
     */
    public void setPages(ArrayList<Page> newPages) { pages = newPages; }

    /**
     * Add a new page to this storybook
     */
    public void addPage(Page newPage) { pages.add(newPage); }

}

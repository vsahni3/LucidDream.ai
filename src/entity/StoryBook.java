package entity;

import java.util.ArrayList;

/**
 * StoryBook is a class representing a storybook entity. It encapsulates information about
 * the title and pages associated with a specific storybook. Instances of this class are used
 * to model entire storybooks containing a collection of pages.
 *
 * <p>The class includes methods for accessing and modifying the title and pages of a storybook.
 * Additionally, it provides functionality to add new pages, set a new title, and set a new collection
 * of pages for a storybook.
 *
 * <p>StoryBook instances are constructed with a title and an initial collection of pages. The title
 * and pages are mutable, allowing for updates and modifications during the lifecycle of the storybook.
 *
 * <p>This class serves as a fundamental building block for representing and managing entire storybooks
 * within a larger narrative context.
 */

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

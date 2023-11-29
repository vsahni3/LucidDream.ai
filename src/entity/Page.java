package entity;

/**
 * Page is a class representing a page entity within a storybook. It encapsulates
 * information about the text content, page number, image, and page ID associated
 * with a specific page. Instances of this class are used to model individual pages
 * within a larger narrative context.
 *
 * <p>The class includes methods for accessing and modifying the text content, page
 * number, page ID, and image of a page. Additionally, it provides functionality to
 * set new text content, set a new page ID, and retrieve the byte array representing
 * the image associated with the page.
 *
 * <p>Page instances are constructed with text content, page number, image data, and
 * a unique page ID. The text content and page number are mutable, allowing for updates,
 * while the page ID and image are immutable once set during instantiation.
 *
 * <p>This class serves as a fundamental building block for representing and managing
 * individual pages within a storybook.
 */

public class Page {

    private String pageContents;

    private final Integer pageNumber;

    private final byte[] image;
    
    private Integer pageID;


    public Page(String textContents, Integer pageNumber, byte[] image, Integer pageID) {
    /**
     * Constructs a new Page entity
     *
     * @param textContents
     * @param pageNumber
     * @param pageID
     */
        this.pageContents = textContents;
        this.pageNumber = pageNumber;
        this.image = image;
        this.pageID = pageID;
    }

    /**
     * Returns page's text content
     */
    public String getTextContents() { return pageContents; }

    /**
     * Returns page's number in a storybook
     */
    public Integer getPageNumber() { return pageNumber; }

    /**
     * Returns page id number
     */
    public Integer getPageID() { return pageID; }

    /**
     * Sets a new page id number
     *
     * @param newPageID
     */
    public void setPageID(int newPageID) { pageID = newPageID; }
  
    public byte[] getImage() { return image; }

    /**
     * Set new text content for page
     *
     * @param newTextContents
     */
    public void setTextContents(String newTextContents) { pageContents = newTextContents; }

}

package entity;

public class Page {

    private String pageContents;

    private final Integer pageNumber;

    private final Integer pageID;

    /**
     * Constructs a new Page entity
     *
     * @param textContents
     * @param pageNumber
     * @param pageID
     */
    public Page(String textContents, Integer pageNumber, Integer pageID) {
        this.pageContents = textContents;
        this.pageNumber = pageNumber;
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
     * Set new text content for page
     */
    public void setTextContents(String newTextContents) { pageContents = newTextContents; }
}

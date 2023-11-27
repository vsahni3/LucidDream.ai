package entity;

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

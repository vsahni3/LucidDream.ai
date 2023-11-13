package entity;

public class Page {

    private final String pageContents;

    private final Integer pageNumber;

    private final Integer pageID;

    private final Image image;


    public Page(String textContents, Integer pageNumber, Integer pageID, Image image) {
        this.pageContents = textContents;
        this.pageNumber = pageNumber;
        this.pageID = pageID;
        this.image = image;
    }

    public String getTextContents() { return pageContents; }

    public Integer getPageNumber() { return pageNumber; }

    public Integer getPageID() { return pageID; }

    public Image getImage() { return image; }
}

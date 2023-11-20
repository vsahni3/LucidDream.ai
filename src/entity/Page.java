package entity;

public class Page {

    private final String pageContents;

    private final Integer pageNumber;

    private final byte[] image;
    
    private final Integer pageID;


    public Page(String textContents, Integer pageNumber, byte[] image, Integer pageID) {
        this.pageContents = textContents;
        this.pageNumber = pageNumber;
        this.image = image;
        this.pageID = pageID;
    }

    public String getTextContents() { return pageContents; }

    public Integer getPageNumber() { return pageNumber; }

    public Integer getPageID() { return pageID; }

    public byte[] getImage() { return image; }

}

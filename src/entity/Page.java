package entity;

public class Page {

    private String pageContents;

    private final Integer pageNumber;

    private final Integer pageID;



    public Page(String textContents, Integer pageNumber, Integer pageID) {
        this.pageContents = textContents;
        this.pageNumber = pageNumber;
        this.pageID = pageID;
    }

    public String getTextContents() { return pageContents; }

    public Integer getPageNumber() { return pageNumber; }

    public Integer getPageID() { return pageID; }

    public void setTextContents(String newTextContents) { pageContents = newTextContents; }
}

package entity;

public class Page {

    private final String pageContents;

    private final Integer pageNumber;

    private final Image image;


    public Page(String textContents, Integer pageNumber, Image image) {
        this.pageContents = textContents;
        this.pageNumber = pageNumber;
        this.image = image;
    }

    public String getTextContents() { return pageContents; }

    public Integer getpageNumber() { return pageNumber; }

    public Image getImage() { return image; }
}

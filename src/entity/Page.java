package entity;

public class Page {

    private final String pageContents;

    private final Integer pageNumber;

    private final byte[] image;


    public Page(String textContents, Integer pageNumber, byte[] image) {
        this.pageContents = textContents;
        this.pageNumber = pageNumber;
        this.image = image;
    }

    public String getTextContents() { return pageContents; }

    public Integer getpageNumber() { return pageNumber; }

    public byte[] getImage() { return image; }
}

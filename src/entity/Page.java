package entity;

import java.sql.Blob;

public class Page {

    private final String pageContents;

    private final Integer pageNumber;

    private final Blob image;


    public Page(String textContents, Integer pageNumber, Blob image) {
        this.pageContents = textContents;
        this.pageNumber = pageNumber;
        this.image = image;
    }

    public String getTextContents() { return pageContents; }

    public Integer getpageNumber() { return pageNumber; }

    public Blob getImage() { return image; }
}

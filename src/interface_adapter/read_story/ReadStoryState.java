package interface_adapter.read_story;

import javax.swing.*;

public class ReadStoryState {

    private String title;
    private ImageIcon[] pageImages;
    private String[] pageTexts;

    public ReadStoryState(ReadStoryState copy) {
        title = copy.title;
        pageImages = copy.pageImages;
        pageTexts = copy.pageTexts;
    }

    // Because of the previous copy constructor, the default constructor must be explicit.
    public ReadStoryState() {}

    /**
     * Return the story title
     */
    public String getTitle() {
        return title;
    }

    /**
     * Return the page image at a specific index number
     */
    public ImageIcon getPageImage(int i) {
        return pageImages[i];
    }

    /**
     * Return the page text at a specific index number
     */
    public String getPageText(int i) {
        return pageTexts[i];
    }

    public int getStoryLength() {
        return pageTexts.length;
    }

    /**
     * Set a new storybook title
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * Sets new story book images
     */
    public void setPageImages(ImageIcon[] pageImages) {
        this.pageImages = pageImages;
    }

    /**
     * Sets new story book texts
     */
    public void setPageTexts(String[] pageTexts) {
        this.pageTexts = pageTexts;
    }

}

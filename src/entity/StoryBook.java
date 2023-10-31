package entity;

public class StoryBook {

    private final String title;

    private final String storyContent;

    private final Page[] pages;


    public StoryBook(String title, String storyContent, Page[] pages) {
        this.title = title;
        this.storyContent = storyContent;
        this.pages = pages;
    }
}

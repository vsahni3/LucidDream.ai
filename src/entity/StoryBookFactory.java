package entity;

public class StoryBookFactory {

    public StoryBook create(String title, String storyContent, Page[] pages) {
        return new StoryBook(title, storyContent, pages);
    }
}

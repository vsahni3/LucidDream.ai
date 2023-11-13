package entity;

public class StoryBookFactory {

    public StoryBook create(String title, Page[] pages) {
        return new StoryBook(title, pages);
    }
}

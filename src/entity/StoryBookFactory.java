package entity;

public class StoryBookFactory {

    public StoryBook create(String prompt) {
        return new StoryBook(title, pages);
    }
}

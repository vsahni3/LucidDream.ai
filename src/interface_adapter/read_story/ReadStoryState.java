package interface_adapter.read_story;

import entity.StoryBook;

public class ReadStoryState {
    private StoryBook storyBook;

    public ReadStoryState(ReadStoryState copy) {
        storyBook = copy.storyBook;
    }

    // Because of the previous copy constructor, the default constructor must be explicit.
    public ReadStoryState() {}

    /**
     * Return the storybook
     */
    public StoryBook getStoryBook() {
        return storyBook;
    }

    /**
     * Set a new storybook value
     */
    public void setStoryBook(StoryBook storyBook) {
        this.storyBook = storyBook;
    }
}

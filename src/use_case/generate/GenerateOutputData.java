package use_case.generate;

import entity.StoryBook;

public class GenerateOutputData {
    private final StoryBook storyBook;

    public GenerateOutputData(StoryBook storyBook) {
        this.storyBook = storyBook;
    }

    public StoryBook getStoryBook() {
        return storyBook;
    }
}

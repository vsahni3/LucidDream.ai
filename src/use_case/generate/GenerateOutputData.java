package use_case.generate;

import entity.StoryBook;

/**
 * GenerateOutputData encapsulates the output data of the story generation process.
 * It primarily holds a reference to the generated StoryBook.
 */
public class GenerateOutputData {
    private final StoryBook storyBook;

    /**
     * Constructs a new GenerateOutputData object with a specific StoryBook.
     *
     * @param storyBook The StoryBook instance that represents the generated story.
     */
    public GenerateOutputData(StoryBook storyBook) {
        this.storyBook = storyBook;
    }

    /**
     * Retrieves the StoryBook generated as part of the story generation process.
     *
     * @return The generated StoryBook.
     */
    public StoryBook getStoryBook() {
        return storyBook;
    }
}

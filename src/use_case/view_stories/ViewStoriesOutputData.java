package use_case.view_stories;

import entity.StoryBook;

import java.util.ArrayList;

public class ViewStoriesOutputData {
    private final ArrayList<StoryBook> storyBooks;

    public ViewStoriesOutputData(ArrayList<StoryBook> storyBooks) {
        this.storyBooks = storyBooks;
    }

    public ArrayList<StoryBook> getStoryBooks() { return storyBooks; }
}

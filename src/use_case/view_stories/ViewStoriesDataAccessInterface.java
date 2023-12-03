package use_case.view_stories;

import entity.StoryBook;

import java.util.ArrayList;

public interface ViewStoriesDataAccessInterface {

    ArrayList<StoryBook> getStoryBooks(String username);
}

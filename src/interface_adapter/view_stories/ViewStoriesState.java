package interface_adapter.view_stories;

import entity.CommonUserFactory;
import entity.StoryBook;
import interface_adapter.logged_in.LoggedInState;

import java.util.ArrayList;


/**
 * Represents the state of the view stories module, including a list of story books and the currently selected story book.
 */
public class ViewStoriesState {

    private ArrayList<StoryBook> storyBooks = new ArrayList<>();

    private StoryBook selected;

    public ViewStoriesState(ViewStoriesState copy) {
        storyBooks = copy.storyBooks;
    }

    // Because of the previous copy constructor, the default constructor must be explicit.
    public ViewStoriesState() {}


    /**
     * Reset state values
     */
    public void clearState() {
        storyBooks = new ArrayList<>();
        selected = null;
    }

    /**
     * Return the ArrayList of Storybooks
     */
    public ArrayList<StoryBook> getStoryBooks() {
        return storyBooks;
    }

    /**
     * Set a new ArrayList of Storybooks
     */
    public void setStoryBooks(ArrayList<StoryBook> storyBooks) {
        this.storyBooks = storyBooks;
    }

    /**
     * Return the currently selected StoryBook
     */
    public StoryBook getSelected() { return selected; }

    /**
     * Set a new selected StoryBook
     */
    public void setSelected(StoryBook selected) {  this.selected = selected; }

}

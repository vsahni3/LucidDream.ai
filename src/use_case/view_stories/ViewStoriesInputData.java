package use_case.view_stories;

/**
 * ViewStoriesInputData encapsulates the input data required for displaying a user's
 * Storybook gallery.
 * Only the user's username is the core information needed for this process
 */
public class ViewStoriesInputData {

    final private String username;

    /**
     * Constructs a new ViewStoriesInputData object with the specified username.
     *
     * @param username The username of the user requesting the story generation.
     */
    public ViewStoriesInputData(String username) {
        this.username = username;
    }

    /**
     * Retrieves the username of the user whose Storybook gallery we wish to display
     *
     * @return The username of the user.
     */
    String getUsername() {
        return username;
    }

}

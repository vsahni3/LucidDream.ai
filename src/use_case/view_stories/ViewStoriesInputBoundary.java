package use_case.view_stories;

public interface ViewStoriesInputBoundary {

    /**
     * Executes the storybooks retrieval process based on the currently logged in user.
     * Retrieves the user, generates a storybook, updates user stories, and prepares the output view.
     *
     * @param viewStoriesInputData Data required to display a user's Storybook gallery
     */
    void execute(ViewStoriesInputData viewStoriesInputData);
}

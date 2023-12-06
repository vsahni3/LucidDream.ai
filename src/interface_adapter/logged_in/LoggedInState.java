package interface_adapter.logged_in;

/**
 * Represents the state of a user who is currently logged in.
 * This class stores information relevant to a logged-in user session, such as the username and a story prompt.
 * It is designed to keep track of user-specific data during an active session.
 */
public class LoggedInState {
    private String username = "";
    private String storyPrompt = "";

    /**
     * Copy constructor to create a new instance of LoggedInState based on an existing one.
     * This constructor is useful for duplicating the state when needed.
     *
     * @param copy The LoggedInState instance to copy data from.
     */
    public LoggedInState(LoggedInState copy) {
        username = copy.username;
    }

    // Because of the previous copy constructor, the default constructor must be explicit.
    public LoggedInState() {}

    /**
     * Retrieves the username of the logged-in user.
     *
     * @return The username as a String.
     */
    public String getUsername() {
        return username;
    }

    /**
     * Sets or updates the username of the logged-in user.
     *
     * @param username The new username to be set.
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * Retrieves the current story prompt associated with the user.
     *
     * @return The story prompt as a String.
     */
    public String getStoryPrompt() { return storyPrompt; }

    /**
     * Sets or updates the story prompt associated with the user.
     *
     * @param newStoryPrompt The new story prompt to be set.
     */
    public void setStoryPrompt(String newStoryPrompt) { this.storyPrompt = newStoryPrompt; }
}

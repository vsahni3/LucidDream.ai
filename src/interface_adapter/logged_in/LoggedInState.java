package interface_adapter.logged_in;

public class LoggedInState {
    private String username = "";
    private String storyPrompt = "";

    public LoggedInState(LoggedInState copy) {
        username = copy.username;
    }

    // Because of the previous copy constructor, the default constructor must be explicit.
    public LoggedInState() {}

    /**
     * Return the String username value
     */
    public String getUsername() {
        return username;
    }

    /**
     * Set a new String username value
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * Return the storybook prompt that the user has inputted
     */
    public String getStoryPrompt() { return storyPrompt; }

    /**
     * Set a new storybook prompt
     */
    public void setStoryPrompt(String newStoryPrompt) { this.storyPrompt = newStoryPrompt; }
}

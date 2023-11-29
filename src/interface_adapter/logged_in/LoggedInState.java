package interface_adapter.logged_in;

public class LoggedInState {
    private String username = "";

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
}

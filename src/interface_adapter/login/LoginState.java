package interface_adapter.login;

public class LoginState {
    private String username = "";
    private String usernameError = null;
    private String password = "";
    private String passwordError = null;

    public LoginState(LoginState copy) {
        username = copy.username;
        usernameError = copy.usernameError;
        password = copy.password;
        passwordError = copy.passwordError;
    }

    // Because of the previous copy constructor, the default constructor must be explicit.
    public LoginState() {}

    /**
     * Reset all state values
     */
    public void clearState() {
        username = "";
        usernameError = null;
        password = "";
        passwordError = null;
    }

    /**
     * Return the String username value
     */
    public String getUsername() {
        return username;
    }

    /**
     * Return the String username error value
     */
    public String getUsernameError() {
        return usernameError;
    }

    /**
     * Return the String password value
     */
    public String getPassword() {
        return password;
    }

    /**
     * Return the String password error value
     */
    public String getPasswordError() {
        return passwordError;
    }

    /**
     * Set a new username
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     *  Set a new username error value
     */
    public void setUsernameError(String usernameError) {
        this.usernameError = usernameError;
    }

    /**
     *  Set a new password value
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     *  Set a new password error value
     */
    public void setPasswordError(String passwordError) {
        this.passwordError = passwordError;
    }
}

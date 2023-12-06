package interface_adapter.login;

/**
 * Represents the state of the login form.
 * This class holds the current state of the login input fields, such as username and password,
 * as well as any error messages associated with them. It is used to manage and track the state of user inputs
 * and validations during the login process.
 */
public class LoginState {
    private String username = "";
    private String usernameError = null;
    private String password = "";
    private String passwordError = null;

    /**
     * Copy constructor for creating a new LoginState instance based on an existing one.
     * This is useful for duplicating the state, for instance, when transitioning between views.
     *
     * @param copy The LoginState instance to copy data from.
     */
    public LoginState(LoginState copy) {
        username = copy.username;
        usernameError = copy.usernameError;
        password = copy.password;
        passwordError = copy.passwordError;
    }

    /**
     * Default constructor for creating a new, empty LoginState instance.
     * Initializes a LoginState with default values.
     */
    public LoginState() {}

    /**
     * Resets all fields of the login state to their default values.
     * This includes clearing the username, password, and any associated error messages.
     */
    public void clearState() {
        username = "";
        usernameError = null;
        password = "";
        passwordError = null;
    }

    /**
     * Retrieves the current username.
     *
     * @return The username as a String.
     */
    public String getUsername() {
        return username;
    }

    /**
     * Retrieves the current username error message.
     *
     * @return The username error message as a String, or null if no error.
     */
    public String getUsernameError() {
        return usernameError;
    }

    /**
     * Retrieves the current password.
     *
     * @return The password as a String.
     */
    public String getPassword() {
        return password;
    }

    /**
     * Retrieves the current password error message.
     *
     * @return The password error message as a String, or null if no error.
     */
    public String getPasswordError() {
        return passwordError;
    }

    /**
     * Sets or updates the username.
     *
     * @param username The new username to be set.
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * Sets or updates the username error message.
     *
     * @param usernameError The new username error message to be set.
     */
    public void setUsernameError(String usernameError) {
        this.usernameError = usernameError;
    }

    /**
     * Sets or updates the password.
     *
     * @param password The new password to be set.
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * Sets or updates the password error message.
     *
     * @param passwordError The new password error message to be set.
     */
    public void setPasswordError(String passwordError) {
        this.passwordError = passwordError;
    }
}

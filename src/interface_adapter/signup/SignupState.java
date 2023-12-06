package interface_adapter.signup;

/**
 * Represents the state of the signup form.
 * This class holds the current state of the signup input fields, such as username and password,
 * as well as any error messages associated with them. It is used to manage and track the state of user inputs
 * and validations during the signup process.
 */
public class SignupState {
    private String username = "";
    private String usernameError = null;
    private String password = "";
    private String passwordError = null;
    private String repeatPassword = "";
    private String repeatPasswordError = null;

    private String deletedUsers = "";

    /**
     * Copy constructor for creating a new SignupState instance based on an existing one.
     * This is useful for duplicating the state, for instance, when transitioning between views.
     *
     * @param copy The SignupState instance to copy data from.
     */
    public SignupState(SignupState copy) {
        username = copy.username;
        usernameError = copy.usernameError;
        password = copy.password;
        passwordError = copy.passwordError;
        repeatPassword = copy.repeatPassword;
        repeatPasswordError = copy.repeatPasswordError;
    }

    /**
     * Default constructor for creating a new, empty SignupState instance.
     * Initializes a SignupState with default values.
     */
    public SignupState() {
    }

    /**
     * Resets all fields of the signup state to their default values.
     * This includes clearing the username, password, repeated password, and any associated error messages.
     */
    public void clearState() {
        username = "";
        usernameError = null;
        password = "";
        passwordError = null;
        repeatPassword = "";
        repeatPasswordError = null;
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
     * Retrieves the username error.
     *
     * @return The username error as a String.
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
     * Retrieves the password error.
     *
     * @return The password error as a String.
     */
    public String getPasswordError() {
        return passwordError;
    }

    /**
     * Retrieves the repeat password.
     *
     * @return The repeat password as a String.
     */
    public String getRepeatPassword() {
        return repeatPassword;
    }

    /**
     * Retrieves the repeat password error.
     *
     * @return The repeat password error as a String.
     */
    public String getRepeatPasswordError() {
        return repeatPasswordError;
    }

    /**
     * Sets a new username.
     * This method updates the username field of the signup state.
     *
     * @param username The new username to be set.
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * Sets a new username error message.
     * This method updates the username error field, typically used to store validation error messages.
     *
     * @param usernameError The new error message for the username field.
     */
    public void setUsernameError(String usernameError) {
        this.usernameError = usernameError;
    }

    /**
     * Sets a new password.
     * This method updates the password field of the signup state.
     *
     * @param password The new password to be set.
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * Sets a new password error message.
     * This method updates the password error field, typically used to store validation error messages.
     *
     * @param passwordError The new error message for the password field.
     */
    public void setPasswordError(String passwordError) {
        this.passwordError = passwordError;
    }

    /**
     * Sets a new repeated password.
     * This method updates the repeated password field of the signup state.
     * Typically, this field is used to confirm that the user has entered the intended password correctly.
     *
     * @param repeatPassword The new repeated password to be set.
     */
    public void setRepeatPassword(String repeatPassword) {
        this.repeatPassword = repeatPassword;
    }

    /**
     * Sets a new repeated password error message.
     * This method updates the repeated password error field, typically used to store validation error messages.
     *
     * @param repeatPasswordError The new error message for the repeated password field.
     */
    public void setRepeatPasswordError(String repeatPasswordError) {
        this.repeatPasswordError = repeatPasswordError;
    }

    /**
     * Provides a string representation of the current state, including username, password, and repeated password.
     *
     * @return A string representation of the SignupState.
     */
    @Override
    public String toString() {
        return "SignupState{" +
                "username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", repeatPassword='" + repeatPassword + '\'' +
                '}';
    }
}

package interface_adapter.signup;

public class SignupState {
    private String username = "";
    private String usernameError = null;
    private String password = "";
    private String passwordError = null;
    private String repeatPassword = "";
    private String repeatPasswordError = null;

    private String deletedUsers = "";

    public SignupState(SignupState copy) {
        username = copy.username;
        usernameError = copy.usernameError;
        password = copy.password;
        passwordError = copy.passwordError;
        repeatPassword = copy.repeatPassword;
        repeatPasswordError = copy.repeatPasswordError;
    }

    // Because of the previous copy constructor, the default constructor must be explicit.
    public SignupState() {
    }

    /**
     *  Reset state values
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
     *  Return username value
     */
    public String getUsername() {
        return username;
    }

    /**
     *  Return username error value
     */
    public String getUsernameError() {
        return usernameError;
    }

    /**
     *  Return password value
     */
    public String getPassword() {
        return password;
    }

    /**
     *  Return password error value
     */
    public String getPasswordError() {
        return passwordError;
    }

    /**
     *  Return repeated password
     */
    public String getRepeatPassword() {
        return repeatPassword;
    }

    /**
     *  Return repeated password error value
     */
    public String getRepeatPasswordError() {
        return repeatPasswordError;
    }

    /**
     *  Set a new username
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
     *  Set a new password
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

    /**
     *  Set a new repeated password
     */
    public void setRepeatPassword(String repeatPassword) {
        this.repeatPassword = repeatPassword;
    }

    /**
     *  Set a new repeated password error value
     */
    public void setRepeatPasswordError(String repeatPasswordError) {
        this.repeatPasswordError = repeatPasswordError;
    }

    /**
     *  Return string representation of sign up state values
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

package use_case.signup;

/**
 * Encapsulates the input data required for the signup process.
 * This class holds the necessary information for a user's registration, including username, password,
 * and a repeated password for verification. It is used to transfer this data to the components
 * responsible for handling the signup process.
 */
public class SignupInputData {

    final private String username;
    final private String password;
    final private String repeatPassword;

    /**
     * Constructs a new SignupInputData instance with the specified user credentials.
     *
     * @param username        The username of the user attempting to sign up.
     * @param password        The password chosen by the user.
     * @param repeatPassword  The repeated password for verification purposes.
     */
    public SignupInputData(String username, String password, String repeatPassword) {
        this.username = username;
        this.password = password;
        this.repeatPassword = repeatPassword;
    }

    /**
     * Retrieves the username.
     *
     * @return The username associated with this signup attempt.
     */
    String getUsername() {
        return username;
    }

    /**
     * Retrieves the password.
     *
     * @return The password associated with this signup attempt.
     */
    String getPassword() {
        return password;
    }

    /**
     * Retrieves the repeated password.
     *
     * @return The repeated password for verification associated with this signup attempt.
     */
    public String getRepeatPassword() {
        return repeatPassword;
    }
}

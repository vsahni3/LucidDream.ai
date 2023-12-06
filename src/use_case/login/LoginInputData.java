package use_case.login;

/**
 * A class that encapsulates login credentials for a given user.
 */
public class LoginInputData {

    final private String username;
    final private String password;

    /**
     * Constructs a new LoginInputData instance with the specified username and password.
     * This constructor initializes the login credentials that will be used in the login process.
     *
     * @param username The username of the user attempting to log in.
     * @param password The password of the user attempting to log in.
     */
    public LoginInputData(String username, String password) {
        this.username = username;
        this.password = password;
    }

    /**
     * Retrieves the username associated with this login data.
     *
     * @return A String representing the username.
     */
    String getUsername() {
        return username;
    }

    /**
     * Retrieves the password associated with this login data.
     *
     * @return A String representing the password.
     */
    String getPassword() {
        return password;
    }

}

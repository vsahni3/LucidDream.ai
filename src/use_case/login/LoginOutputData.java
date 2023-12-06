package use_case.login;

/**
 * Represents the data output from a login use case.
 * This class encapsulates the result of a login operation, including the username of the user
 * and a flag indicating whether the login use case was successful or failed.
 */
public class LoginOutputData {
    private final String username;
    private boolean useCaseFailed;

    /**
     * Constructs a new instance of LoginOutputData.
     *
     * @param username The username of the user involved in the login operation.
     * @param useCaseFailed A boolean flag indicating if the login use case failed or not.
     */
    public LoginOutputData(String username, boolean useCaseFailed) {
        this.username = username;
        this.useCaseFailed = useCaseFailed;
    }


    /**
     * Retrieves the username.
     *
     * @return A string representing the username of the user.
     */
    public String getUsername() {
        return username;
    }
}

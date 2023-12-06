package use_case.login;

/**
 * This interface defines the output boundary for the login use case.
 * It provides methods to prepare the view for successful and failed login attempts.
 * Implementations of this interface will handle how the application responds to
 * different outcomes of the login process.
 */
public interface LoginOutputBoundary {

    /**
     * Prepares and displays the view for a successful login attempt.
     * This method should be called when the user has successfully logged in.
     *
     * @param user The LoginOutputData object containing information about the logged-in user.
     */
    void prepareSuccessView(LoginOutputData user);

    /**
     * Prepares and displays the view for a failed login attempt.
     * This method should be called when the login attempt has failed.
     *
     * @param errorType A string representing the type of error that occurred during login.
     * @param error A string detailing the specific error message to be displayed.
     */
    void prepareFailView(String errorType, String error);
}

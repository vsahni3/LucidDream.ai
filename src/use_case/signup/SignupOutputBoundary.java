package use_case.signup;

/**
 * Interface defining the output boundary for the signup process.
 * It provides methods for conveying the results of the signup process, whether successful or failed,
 * to the appropriate presentation or response handling mechanisms.
 */
public interface SignupOutputBoundary {
    /**
     * Prepares the view or response for a successful signup.
     * This method should be invoked when the signup process completes successfully.
     * It can be used to present a success message, navigate to a different screen, or any other relevant action.
     *
     * @param user An instance of {@link SignupOutputData} containing information about the successfully signed-up user.
     */
    void prepareSuccessView(SignupOutputData user);

    /**
     * Prepares the view or response for a failed signup.
     * This method should be invoked when the signup process fails for any reason.
     * It can be used to display an error message, prompt the user to retry, or handle the error in an appropriate manner.
     *
     * @param errorType A string indicating the type of error that occurred during signup.
     * @param error A detailed error message explaining the cause of the failure.
     */
    void prepareFailView(String errorType, String error);
}
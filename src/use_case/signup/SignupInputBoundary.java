package use_case.signup;

/**
 * This interface defines the input boundary for the signup use case.
 * It serves as a contract for handling the signup process in the application.
 * Implementations of this interface will process the input data required for signing up a new user.
 */
public interface SignupInputBoundary {

    /**
     * Executes the signup process with the provided input data.
     * This method is responsible for initiating the signup procedure using the given {@link SignupInputData}.
     * It should handle the necessary logic for registering a new user based on the provided information.
     *
     * @param signupInputData An instance of {@link SignupInputData} containing the necessary information for user registration.
     */
    void execute(SignupInputData signupInputData);
}

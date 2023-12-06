package interface_adapter.signup;

import use_case.signup.SignupInputBoundary;
import use_case.signup.SignupInputData;

/**
 * Controller for handling user signup requests.
 * This class is responsible for receiving input data related to user registration, such as username and password,
 * and passing it to the signup use case interactor.
 */
public class SignupController {

    final SignupInputBoundary userSignupUseCaseInteractor;

    /**
     * Constructs a SignupController with a given SignupInputBoundary.
     * The SignupInputBoundary is typically an interactor that handles the signup logic.
     *
     * @param userSignupUseCaseInteractor The SignupInputBoundary instance responsible for executing the signup logic.
     */
    public SignupController(SignupInputBoundary userSignupUseCaseInteractor) {
        this.userSignupUseCaseInteractor = userSignupUseCaseInteractor;
    }

    /**
     * Executes the signup process with the provided user details.
     * This method creates a SignupInputData object from the provided username and passwords and passes it
     * to the user signup use case interactor for processing.
     *
     * @param username   The username provided by the user.
     * @param password1  The primary password provided by the user.
     * @param password2  The secondary password (for confirmation) provided by the user.
     */
    public void execute(String username, String password1, String password2) {
        SignupInputData signupInputData = new SignupInputData(
                username, password1, password2);

        userSignupUseCaseInteractor.execute(signupInputData);
    }
}

package use_case.signup;

import entity.User;
import entity.UserFactory;

import java.time.LocalDateTime;

/**
 * This class is responsible for handling the signup process.
 * It implements the {@link SignupInputBoundary} interface and coordinates the process of user registration.
 * This includes validating input data, creating new users, and communicating with the data access layer and the presenter.
 */
public class SignupInteractor implements SignupInputBoundary {
    final SignupUserDataAccessInterface userDataAccessObject;
    final SignupOutputBoundary userPresenter;
    final UserFactory userFactory;

    /**
     * Constructs a new SignupInteractor with specified data access, output boundary, and user factory.
     *
     * @param signupDataAccessInterface An instance of {@link SignupUserDataAccessInterface} for accessing user data.
     * @param signupOutputBoundary An instance of {@link SignupOutputBoundary} for presenting output to the user.
     * @param userFactory An instance of {@link UserFactory} for creating new user entities.
     */
    public SignupInteractor(SignupUserDataAccessInterface signupDataAccessInterface,
                            SignupOutputBoundary signupOutputBoundary,
                            UserFactory userFactory) {
        this.userDataAccessObject = signupDataAccessInterface;
        this.userPresenter = signupOutputBoundary;
        this.userFactory = userFactory;
    }

    /**
     * Executes the signup process with the provided input data.
     * Validates the input data, checks for existing users, matches passwords, and then creates a new user if validations pass.
     * If the signup process fails due to any reason, it communicates the failure through the user presenter.
     *
     * @param signupInputData An instance of {@link SignupInputData} containing the information needed for the signup process.
     */
    @Override
    public void execute(SignupInputData signupInputData) {
        if (userDataAccessObject.existsUser(signupInputData.getUsername())) {
            userPresenter.prepareFailView("User exists", "User already exists.");
        } else if (!signupInputData.getPassword().equals(signupInputData.getRepeatPassword())) {
            userPresenter.prepareFailView("Password mismatch", "Passwords don't match.");
        } else {

            User user = userFactory.create(signupInputData.getUsername(), signupInputData.getPassword());
            userDataAccessObject.save(user);

            SignupOutputData signupOutputData = new SignupOutputData(user.getUserName());
            userPresenter.prepareSuccessView(signupOutputData);
        }
    }
}
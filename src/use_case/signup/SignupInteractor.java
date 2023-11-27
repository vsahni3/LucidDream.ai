package use_case.signup;

import entity.User;
import entity.UserFactory;

import java.time.LocalDateTime;

public class SignupInteractor implements SignupInputBoundary {
    final SignupUserDataAccessInterface userDataAccessObject;
    final SignupOutputBoundary userPresenter;
    final UserFactory userFactory;

    public SignupInteractor(SignupUserDataAccessInterface signupDataAccessInterface,
                            SignupOutputBoundary signupOutputBoundary,
                            UserFactory userFactory) {
        this.userDataAccessObject = signupDataAccessInterface;
        this.userPresenter = signupOutputBoundary;
        this.userFactory = userFactory;
    }

    @Override
    public void execute(SignupInputData signupInputData) {
        if (userDataAccessObject.existsUser(signupInputData.getUsername())) {
            userPresenter.prepareFailView("User exists", "User already exists.");
        } else if (!signupInputData.getPassword().equals(signupInputData.getRepeatPassword())) {
            userPresenter.prepareFailView("Password mismatch", "Passwords don't match.");
        } else {

            LocalDateTime now = LocalDateTime.now();
            User user = userFactory.create(signupInputData.getUsername(), signupInputData.getPassword(), now);
            userDataAccessObject.save(user);

            SignupOutputData signupOutputData = new SignupOutputData(user.getUserName(), now.toString(), false);
            userPresenter.prepareSuccessView(signupOutputData);
        }
    }
}
package use_case.login;

import entity.User;

/**
 * The LoginInteractor class is responsible for implementing the login logic as defined
 * by the LoginInputBoundary interface. The class handles the authentication of user credentials
 * and orchestrates the response based on the success or failure of these credentials.
 */
public class LoginInteractor implements LoginInputBoundary {
    final LoginUserDataAccessInterface userDataAccessObject;
    final LoginOutputBoundary loginPresenter;

    /**
     * Constructs a LoginInteractor with specified data access and output boundary interfaces.
     * This constructor initializes the LoginInteractor with dependencies necessary for performing
     * user authentication and response preparation.
     *
     * @param userDataAccessInterface The data access object to interact with the user data.
     * @param loginOutputBoundary The output boundary interface to handle the presentation logic
     *                            for the login process.
     */
    public LoginInteractor(LoginUserDataAccessInterface userDataAccessInterface,
                           LoginOutputBoundary loginOutputBoundary) {
        this.userDataAccessObject = userDataAccessInterface;
        this.loginPresenter = loginOutputBoundary;
    }

    /**
     * Executes the login process using the provided login input data. This method authenticates
     * the user by verifying the provided username and password against the stored data. It
     * communicates the outcome (success or failure) to the Login Presenter for appropriate
     * response handling.
     *
     * @param loginInputData The login credentials provided for authentication.
     */
    @Override
    public void execute(LoginInputData loginInputData) {
        String username = loginInputData.getUsername();
        String password = loginInputData.getPassword();
        if (!userDataAccessObject.existsUser(username)) {
            loginPresenter.prepareFailView("username error", username + ": Account does not exist.");
        } else {
            String pwd = userDataAccessObject.getUser(username).getPassword();
            if (!password.equals(pwd)) {
                loginPresenter.prepareFailView("password error", "Incorrect password for " + username + ".");
            } else {

                User user = userDataAccessObject.getUser(loginInputData.getUsername());

                LoginOutputData loginOutputData = new LoginOutputData(user.getUserName(), false);
                loginPresenter.prepareSuccessView(loginOutputData);
            }
        }
    }
}

package interface_adapter.login;

import use_case.login.LoginInputBoundary;
import use_case.login.LoginInputData;

/**
 * Controller for handling login requests.
 * This class is responsible for taking input from the user interface, like username and password,
 * and passing it to the login use case interactor.
 */
public class LoginController {
    final LoginInputBoundary loginUseCaseInteractor;

    /**
     * Constructs a LoginController with a given LoginInputBoundary.
     * The LoginInputBoundary is typically an interactor that handles the actual login process.
     *
     * @param loginUseCaseInteractor The LoginInputBoundary instance responsible for executing the login logic.
     */
    public LoginController(LoginInputBoundary loginUseCaseInteractor) {
        this.loginUseCaseInteractor = loginUseCaseInteractor;
    }

    /**
     * Executes the login process with the provided username and password.
     * This method creates a LoginInputData object from the provided credentials and passes it
     * to the login use case interactor for further processing.
     *
     * @param username The username provided by the user.
     * @param password The password provided by the user.
     */
    public void execute(String username, String password) {
        LoginInputData loginInputData = new LoginInputData(
                username, password);

        loginUseCaseInteractor.execute(loginInputData);
    }
}

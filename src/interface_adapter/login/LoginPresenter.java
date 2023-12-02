package interface_adapter.login;

import interface_adapter.logged_in.LoggedInState;
import interface_adapter.logged_in.LoggedInViewModel;
import interface_adapter.ViewManagerModel;
import interface_adapter.signup.SignupState;
import use_case.login.LoginOutputBoundary;
import use_case.login.LoginOutputData;
import use_case.signup.SignupOutputBoundary;
import use_case.signup.SignupOutputData;

/**
 * LoginPresenter is responsible for handling the output data provided by the LoginInteractor
 * and logs the user in or notifies the user if they've inputted invalid information.
 * @author Eugene Cho
 */
public class LoginPresenter implements LoginOutputBoundary {

    private final LoginViewModel loginViewModel;
    private final LoggedInViewModel loggedInViewModel;
    private ViewManagerModel viewManagerModel;

    /**
     * Constructs a presenter for the Login user use case.
     * @param viewManagerModel
     * @param loggedInViewModel
     * @param loginViewModel
     */
    public LoginPresenter(ViewManagerModel viewManagerModel,
                          LoggedInViewModel loggedInViewModel,
                          LoginViewModel loginViewModel) {
        this.viewManagerModel = viewManagerModel;
        this.loggedInViewModel = loggedInViewModel;
        this.loginViewModel = loginViewModel;
    }

    /**
     * Unpacks the login details and logs the user in by progressing to the LoggedIn view.
     * @param response
     */
    @Override
    public void prepareSuccessView(LoginOutputData response) {
        // On success, switch to the logged in view.

        LoggedInState loggedInState = loggedInViewModel.getState();
        loggedInState.setUsername(response.getUsername());
        this.loggedInViewModel.setState(loggedInState);
        this.loggedInViewModel.firePropertyChanged();
        this.loginViewModel.firePropertyChanged();
        this.viewManagerModel.setActiveView(loggedInViewModel.getViewName());
        this.viewManagerModel.firePropertyChanged();
    }

    /**
     * Unpacks the error type and error and notifies the user in the view.
     * @param errorType
     * @param error
     */
    @Override
    public void prepareFailView(String errorType, String error) {
        LoginState loginState = loginViewModel.getState();

        if (errorType.equals("password error")) {
            loginState.setPasswordError(error);
        } else {
            loginState.setUsernameError(error);
        }

        loginViewModel.firePropertyChanged();
    }
}

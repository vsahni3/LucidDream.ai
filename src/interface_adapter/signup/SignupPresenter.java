package interface_adapter.signup;

import interface_adapter.login.LoginState;
import interface_adapter.login.LoginViewModel;

import interface_adapter.logged_in.LoggedInState;
import interface_adapter.logged_in.LoggedInViewModel;

import interface_adapter.ViewManagerModel;
import use_case.signup.SignupOutputBoundary;
import use_case.signup.SignupOutputData;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * SignupPresenter is responsible for handling the output data provided by the SignupInteractor
 * and logs the user in or notifies the user if they've inputted invalid information.
 * @author Eugene Cho
 */
public class SignupPresenter implements SignupOutputBoundary {

    private final SignupViewModel signupViewModel;
    private final LoggedInViewModel loggedInViewModel;
    private ViewManagerModel viewManagerModel;

    /**
     * Constructs a presenter for the Sign Up user use case.
     * @param viewManagerModel
     * @param signupViewModel
     * @param loggedInViewModel
     */
    public SignupPresenter(ViewManagerModel viewManagerModel,
                           SignupViewModel signupViewModel,
                           LoggedInViewModel loggedInViewModel) {
        this.viewManagerModel = viewManagerModel;
        this.signupViewModel = signupViewModel;
        this.loggedInViewModel = loggedInViewModel;
    }

    /**
     * Unpacks the signup details and logs the user in by progressing to the LoggedIn view.
     * @param response
     */
    @Override
    public void prepareSuccessView(SignupOutputData response) {
        // On success, switch to the login view.
        LocalDateTime responseTime = LocalDateTime.parse(response.getCreationTime());
        response.setCreationTime(responseTime.format(DateTimeFormatter.ofPattern("hh:mm:ss")));

        LoggedInState loggedInState = loggedInViewModel.getState();
        loggedInState.setUsername(response.getUsername());
        this.loggedInViewModel.setState(loggedInState);
        loggedInViewModel.firePropertyChanged();
        signupViewModel.firePropertyChanged();
        viewManagerModel.setActiveView(loggedInViewModel.getViewName());
        viewManagerModel.firePropertyChanged();
    }

    /**
     * Unpacks the error type and error and notifies the user in the view.
     * @param errorType
     * @param error
     */
    @Override
    public void prepareFailView(String errorType, String error) {
        SignupState signupState = signupViewModel.getState();

        if (errorType.equals("User exists")) {
            signupState.setUsernameError(error);
        } else if (errorType.equals("Password mismatch")) {
            signupState.setRepeatPasswordError(error);
        } else {
            signupState.setPasswordError(error);
        }
        signupViewModel.firePropertyChanged();
    }
}

package interface_adapter.signup;

import interface_adapter.ViewModel;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

/**
 * ViewModel for the signup view.
 * This class extends {@link ViewModel} and is responsible for managing the UI state for the signup process.
 * It holds the state of the signup form, including user inputs and validation errors, and provides methods
 * to manage and update this state. It also supports notifying observers about changes to the state through property change listeners.
 */
public class SignupViewModel extends ViewModel {

    public static final String CLEAR_BUTTON_LABEL = "Clear";
    public static final String TITLE_LABEL = "Sign Up View";
    public static final String USERNAME_LABEL = "Choose username";
    public static final String PASSWORD_LABEL = "Choose password";
    public static final String REPEAT_PASSWORD_LABEL = "Enter password again";

    public static final String SIGNUP_BUTTON_LABEL = "Sign up";
    public static final String CANCEL_BUTTON_LABEL = "Cancel";

    private SignupState state = new SignupState();

    /**
     * Constructs a SignupViewModel with the initial state set to 'sign up'.
     */
    public SignupViewModel() {
        super("sign up");
    }

    /**
     * Sets the state of the signup form.
     * This method updates the state which reflects the current user input and validation errors in the signup form.
     *
     * @param state The new {@link SignupState} to be set for the signup form.
     */
    public void setState(SignupState state) {
        this.state = state;
    }

    private final PropertyChangeSupport support = new PropertyChangeSupport(this);

    /**
     * Triggers an update notification to all registered property change listeners.
     * This method should be called when there is a change in the state that needs to be reflected in the UI.
     */
    public void firePropertyChanged() {
        support.firePropertyChange("state", null, this.state);
    }

    /**
     * Adds a property change listener to this ViewModel.
     * Listeners registered through this method will be notified of changes to the state.
     *
     * @param listener The PropertyChangeListener to be added.
     */
    public void addPropertyChangeListener(PropertyChangeListener listener) {
        support.addPropertyChangeListener(listener);
    }

    /**
     * Retrieves the current state of the signup form.
     *
     * @return The current {@link SignupState} of the signup form.
     */
    public SignupState getState() {
        return state;
    }
}

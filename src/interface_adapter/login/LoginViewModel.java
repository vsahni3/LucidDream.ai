package interface_adapter.login;

import interface_adapter.ViewModel;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

/**
 * ViewModel for the login view.
 * This class extends {@link ViewModel} and is responsible for managing the UI state for the login process.
 * It holds the state of the login form, including user inputs and validation errors, and provides methods
 * to manage and update this state. It also supports notifying observers about changes to the state through property change listeners.
 */
public class LoginViewModel extends ViewModel {

    private LoginState state = new LoginState();

    /**
     * Constructs a LoginViewModel with the initial state set to 'log in'.
     */
    public LoginViewModel() {
        super("log in");
    }

    /**
     * Retrieves the current state of the login form.
     * This method returns the {@link LoginState} object representing the current state of the login inputs and errors.
     *
     * @return The current {@link LoginState} of the login form.
     */
    public LoginState getState() {
        return state;
    }

    /**
     * Sets the state of the login form.
     * This method updates the state which reflects the current user input and validation errors in the login form.
     *
     * @param state The new {@link LoginState} to be set for the login form.
     */
    public void setState(LoginState state) {
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
}

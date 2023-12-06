package interface_adapter.logged_in;

import interface_adapter.ViewModel;
import interface_adapter.login.LoginState;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

/**
 * ViewModel for the logged-in user interface.
 * This class extends {@link ViewModel} and is responsible for managing the UI state and data for a user who is logged in.
 * It holds the state of the user interface, such as the current user and labels for UI components, and provides methods
 * to manage and update this state. It also supports notifying observers about changes to the state through property change listeners.
 */
public class LoggedInViewModel extends ViewModel {

    public final String PROMPT_LABEL = "Write a story about...";

    private LoggedInState state = new LoggedInState();

    public static final String CREATE_BUTTON_LABEL = "CREATE";
    public static final String VIEW_STORIES_BUTTON_LABEL = "MY STORIES";
    public static final String LOGOUT_BUTTON_LABEL = "LOG OUT";
    private String loggedInUser;

    /**
     * Constructs a LoggedInViewModel with the initial state set to 'logged in'.
     */
    public LoggedInViewModel() {
        super("logged in");
    }

    /**
     * Sets the state of the logged-in user interface.
     * This method updates the state which reflects the current user information and UI data.
     *
     * @param state The new state to be set for the logged-in user interface.
     */
    public void setState(LoggedInState state) {
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
     * Retrieves the current state of the logged-in user interface.
     *
     * @return The current {@link LoggedInState} of the user interface.
     */
    public LoggedInState getState() {
        return state;
    }

}
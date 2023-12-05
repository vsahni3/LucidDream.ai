/**
 * ViewModel class for the View Stories module, responsible for managing the state and communication with the View.
 */
package interface_adapter.view_stories;

import entity.StoryBook;
import interface_adapter.ViewModel;
import interface_adapter.login.LoginState;
import view.ViewStoriesView;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.ArrayList;

public class ViewStoriesViewModel extends ViewModel {

    /** Constant for the title label in the view. */
    public final String TITLE_LABEL = "Storybooks";

    /** Constant for the label of the SELECT button in the view. */
    public final String SELECT_BUTTON_LABEL = "SELECT";

    /** Constant for the label of the READ STORY button in the view. */
    public final String READ_BUTTON_LABEL = "READ STORY";

    /** The state representing the data of the View Stories module. */
    private ViewStoriesState state = new ViewStoriesState();

    /**
     * Constructs a new ViewStoriesViewModel with the specified module name.
     */
    public ViewStoriesViewModel() {
        super("view stories");
    }

    /**
     * Gets the current state of the View Stories module.
     *
     * @return The current state.
     */
    public ViewStoriesState getState() {
        return state;
    }

    /**
     * Sets the state of the View Stories module.
     *
     * @param state The new state to set.
     */
    public void setState(ViewStoriesState state) {
        this.state = state;
    }

    /** The PropertyChangeSupport instance for managing property change listeners. */
    private final PropertyChangeSupport support = new PropertyChangeSupport(this);

    /**
     * Notifies the ViewModel that a property has changed and triggers an update in the View.
     */
    public void firePropertyChanged() {
        support.firePropertyChange("view-stories", null, this.state);
    }

    /**
     * Adds a property change listener to the ViewModel.
     *
     * @param listener The PropertyChangeListener to add.
     */
    public void addPropertyChangeListener(PropertyChangeListener listener) {
        support.addPropertyChangeListener(listener);
    }
}
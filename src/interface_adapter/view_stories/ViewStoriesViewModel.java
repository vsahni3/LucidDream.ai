package interface_adapter.view_stories;

import entity.StoryBook;
import interface_adapter.ViewModel;
import interface_adapter.login.LoginState;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.ArrayList;

public class ViewStoriesViewModel extends ViewModel {

    public final String TITLE_LABEL = "Storybooks";
    public final ArrayList<StoryBook> storyBooks = new ArrayList<>();

    public final String SELECT_BUTTON_LABEL = "SELECT";
    public final String READ_BUTTON_LABEL = "READ STORY";

    private ViewStoriesState state = new ViewStoriesState();

    public ViewStoriesViewModel() { super("view stories"); }

    public ViewStoriesState getState() {
        return state;
    }

    public void setState(ViewStoriesState state) {
        this.state = state;
    }

    private final PropertyChangeSupport support = new PropertyChangeSupport(this);

    // This is what the Presenter will call to let the ViewModel know
    // to alert the View
    public void firePropertyChanged() {
        support.firePropertyChange("state", null, this.state);
    }

    public void addPropertyChangeListener(PropertyChangeListener listener) {
        support.addPropertyChangeListener(listener);
    }

}

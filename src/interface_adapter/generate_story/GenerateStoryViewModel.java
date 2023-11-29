package interface_adapter.generate_story;

import interface_adapter.ViewModel;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

public class GenerateStoryViewModel extends ViewModel {

    public final String TITLE_LABEL = "LucidDream AI";
    public final String PROMPT_LABEL = "Write a story about...";

    public static final String CREATE_BUTTON_LABEL = "CREATE";
    public static final String VIEW_STORIES_BUTTON_LABEL = "VIEW STORIES";

    private GenerateStoryState state = new GenerateStoryState();

    public GenerateStoryViewModel() { super("generate storybook"); }

    public void setState(GenerateStoryState state) { this.state = state; }

    public GenerateStoryState getState() { return state; }

    private final PropertyChangeSupport support = new PropertyChangeSupport(this);

    // This is what the Signup Presenter will call to let the ViewModel know
    // to alert the View
    public void firePropertyChanged() {
        support.firePropertyChange("state", null, this.state);
    }

    public void addPropertyChangeListener(PropertyChangeListener listener) {
        support.addPropertyChangeListener(listener);
    }
}

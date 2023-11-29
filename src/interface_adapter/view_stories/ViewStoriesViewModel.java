package interface_adapter.view_stories;

import interface_adapter.ViewModel;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

public class ViewStoriesViewModel extends ViewModel {

    public final String TITLE_LABEL = "Storybooks";

    // PLACEHOLDERS FOR NOW
    public final String STORYBOOK_TITLE = "Storybook Title";
    public final String STORYBOOK_DESCRIPTION = "Storybook Description";

    public static final String VIEW_BUTTON_LABEL = "VIEW"; // View storybook
    public static final String EXPORT_BUTTON_LABEL = "EXPORT";

    // NO STATE FOR THIS PAGE???

    public ViewStoriesViewModel() { super("view stories"); }

    private final PropertyChangeSupport support = new PropertyChangeSupport(this);

    // This is what the Presenter will call to let the ViewModel know
    // to alert the View

    // I don't this there would be any property changes for this page?
    public void firePropertyChanged() {
        support.firePropertyChange("state", null, null);
    }

    public void addPropertyChangeListener(PropertyChangeListener listener) {
        support.addPropertyChangeListener(listener);
    }

}

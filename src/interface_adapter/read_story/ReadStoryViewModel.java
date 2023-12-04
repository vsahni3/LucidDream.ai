package interface_adapter.read_story;

import interface_adapter.ViewModel;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

public class ReadStoryViewModel extends ViewModel {

    private ReadStoryState state = new ReadStoryState();

    public ReadStoryViewModel() {
        super("read story");
    }

    public void setState(ReadStoryState state) {
        this.state = state;
    }

    private final PropertyChangeSupport support = new PropertyChangeSupport(this);

    // This is what the Login Presenter will call to let the ViewModel know
    // to alert the View
    public void firePropertyChanged() {
        support.firePropertyChange("state", null, this.state);
    }

    public void addPropertyChangeListener(PropertyChangeListener listener) {
        support.addPropertyChangeListener(listener);
    }

    public ReadStoryState getState() {
        return state;
    }

}

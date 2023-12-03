package interface_adapter.narrate;

import interface_adapter.ViewModel;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

/**
 * View model class for the narrate functionality.
 * This class holds the state relevant to the narrate view and notifies observers about changes.
 */
public class NarrateViewModel extends ViewModel {

    public NarrateViewModel() {
        super("narrate");
    }

    private final PropertyChangeSupport support = new PropertyChangeSupport(this);

    /**
     * Notifies all registered listeners about a property change.
     * This method is used to inform observers about changes in the narrate view state.
     */
    @Override
    public void firePropertyChanged() {
        support.firePropertyChange("narrate", null, null);
    }

    @Override
    public void addPropertyChangeListener(PropertyChangeListener listener) {
        support.addPropertyChangeListener(listener);
    }
}

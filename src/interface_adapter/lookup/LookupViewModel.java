package interface_adapter.lookup;

import interface_adapter.ViewModel;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

public class LookupViewModel extends ViewModel {

    private final PropertyChangeSupport support = new PropertyChangeSupport(this);

    private LookupState state = new LookupState();

    public LookupViewModel() { super("lookup"); }

    public void setState(LookupState state) { this.state = state; }

    public LookupState getState() { return this.state; }

    @Override
    public void firePropertyChanged() {
        support.firePropertyChange("lookup", null, null);
    }

    @Override
    public void addPropertyChangeListener(PropertyChangeListener listener) {
        support.addPropertyChangeListener(listener);

    }
}

package interface_adapter.downloadPDF;

import interface_adapter.ViewModel;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
/**
 * The DownloadPDFViewModel class is responsible for managing the view model for the download PDF functionality.
 * It maintains the state and notifies property change listeners about state changes.
 */
public class DownloadPDFViewModel extends ViewModel {

    private final PropertyChangeSupport support = new PropertyChangeSupport(this);
    private DownloadPDFState state = new DownloadPDFState();

    /**
     * Constructs a new DownloadPDFViewModel with a specific module name.
     */
    public DownloadPDFViewModel() { super("download"); }

    /**
     * Gets the current state of the download PDF process.
     *
     * @return The current DownloadPDFState.
     */
    public DownloadPDFState getState() { return this.state; }

    /**
     * Sets the state of the download PDF process.
     *
     * @param state The new state to set.
     */
    public void setState(DownloadPDFState state) { this.state = state; }

    /**
     * Notifies all registered listeners that a property has changed.
     */
    @Override
    public void firePropertyChanged() {
        support.firePropertyChange("download", null, null);
    }

    /**
     * Adds a property change listener to this model.
     *
     * @param listener The PropertyChangeListener to add.
     */
    @Override
    public void addPropertyChangeListener(PropertyChangeListener listener) {
        support.addPropertyChangeListener(listener);
    }
}

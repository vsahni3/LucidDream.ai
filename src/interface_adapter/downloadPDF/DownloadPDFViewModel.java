package interface_adapter.downloadPDF;

import interface_adapter.ViewModel;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

public class DownloadPDFViewModel extends ViewModel {

    private final PropertyChangeSupport support = new PropertyChangeSupport(this);

    private DownloadPDFState state = new DownloadPDFState();

    public DownloadPDFViewModel() { super("download"); }

    public DownloadPDFState getState() { return this.state; }

    public void setState(DownloadPDFState state) { this.state = state; }


    @Override
    public void firePropertyChanged() {
        support.firePropertyChange("download", null, null);

    }

    @Override
    public void addPropertyChangeListener(PropertyChangeListener listener) {
        support.addPropertyChangeListener(listener);
    }
}

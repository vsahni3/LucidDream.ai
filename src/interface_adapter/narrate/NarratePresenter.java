package interface_adapter.narrate;

import use_case.narrate.NarrateOutputBoundary;

/**
 * Presenter class for the narrate use case.
 * This class is responsible for handling the data received from the narrate interactor
 * and updating the narrate view model accordingly.
 */
public class NarratePresenter implements NarrateOutputBoundary {

    private final NarrateViewModel narrateViewModel;

    /**
     * Constructs a NarratePresenter with the specified narrate view model.
     *
     * @param narrateViewModel The view model to be updated based on narrate results.
     */
    public NarratePresenter(NarrateViewModel narrateViewModel) {
        this.narrateViewModel = narrateViewModel;
    }

    /**
     * Prepares the view model for updates based on the results from the narrate interactor.
     * This method is called when the narrate interactor has completed its processing.
     */
    @Override
    public void prepareView() {
        narrateViewModel.firePropertyChanged();
    }
}

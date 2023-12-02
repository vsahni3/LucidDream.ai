package interface_adapter.narrate;

import use_case.narrate.NarrateOutputBoundary;

public class NarratePresenter implements NarrateOutputBoundary {

    private final NarrateViewModel narrateViewModel;

    public NarratePresenter(NarrateViewModel narrateViewModel) {
        this.narrateViewModel = narrateViewModel;
    }

    @Override
    public void prepareView() {
        narrateViewModel.firePropertyChanged();
    }
}

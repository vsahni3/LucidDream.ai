package interface_adapter.lookup;

import interface_adapter.ViewManagerModel;
import use_case.lookup.LookupOutputBoundary;
import use_case.lookup.LookupOutputData;

public class LookupPresenter implements LookupOutputBoundary {

    private final LookupViewModel lookupViewModel;

    public LookupPresenter(LookupViewModel lookupViewModel) {
        this.lookupViewModel = lookupViewModel;
    }

    @Override
    public void prepareSuccessView(LookupOutputData lookupOutputData) {
        LookupState lookupState = lookupViewModel.getState();
        lookupState.setDefinition(lookupOutputData.getDefinition());
        lookupState.setWord(lookupOutputData.getWord());

        lookupViewModel.setState(lookupState);
        lookupViewModel.firePropertyChanged();

    }
}

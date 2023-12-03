package app;

import interface_adapter.ViewManagerModel;
import interface_adapter.download_story.DownloadController;
import interface_adapter.lookup.LookupController;
import interface_adapter.lookup.LookupPresenter;
import interface_adapter.lookup.LookupViewModel;
import interface_adapter.narrate.NarrateController;
import interface_adapter.narrate.NarratePresenter;
import interface_adapter.narrate.NarrateViewModel;
import interface_adapter.read_story.ReadStoryViewModel;
import use_case.lookup.LookupInputBoundary;
import use_case.lookup.LookupInteractor;
import use_case.lookup.LookupOutputBoundary;
import use_case.narrate.NarrateInputBoundary;
import use_case.narrate.NarrateInteractor;
import use_case.narrate.NarrateOutputBoundary;
import view.ReadStoryView;

public class ReadStoryUseCaseFactory {
    public static ReadStoryView create(ViewManagerModel viewManagerModel, ReadStoryViewModel readStoryViewModel, NarrateViewModel narrateViewModel, LookupViewModel lookupViewModel) {

        NarrateController narrateController = createNarrateUseCase(narrateViewModel);

        LookupController lookupController = createLookupUseCase(lookupViewModel);

        DownloadController downloadController = createDownloadUseCase();

        return new ReadStoryView(viewManagerModel, readStoryViewModel, narrateViewModel, narrateController, lookupController, downloadController, lookupViewModel);
    }

    private static DownloadController createDownloadUseCase() {
        return null;
    }

    private static LookupController createLookupUseCase(LookupViewModel lookupViewModel) {
        LookupOutputBoundary lookupPresenter = new LookupPresenter(lookupViewModel);
        LookupInputBoundary lookupInteractor = new LookupInteractor(lookupPresenter);
        return new LookupController(lookupInteractor);

    }

    private static NarrateController createNarrateUseCase(NarrateViewModel narrateViewModel) {
        NarrateOutputBoundary narratePresenter = new NarratePresenter(narrateViewModel);
        NarrateInputBoundary narrateInteractor = new NarrateInteractor(narratePresenter);
        return new NarrateController(narrateInteractor);
    }
}

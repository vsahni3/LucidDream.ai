package app;

import interface_adapter.ViewManagerModel;
import interface_adapter.downloadPDF.DownloadPDFController;
import interface_adapter.downloadPDF.DownloadPDFPresenter;
import interface_adapter.downloadPDF.DownloadPDFViewModel;
import interface_adapter.lookup.LookupController;
import interface_adapter.lookup.LookupPresenter;
import interface_adapter.lookup.LookupViewModel;
import interface_adapter.narrate.NarrateController;
import interface_adapter.narrate.NarratePresenter;
import interface_adapter.narrate.NarrateViewModel;
import interface_adapter.read_story.ReadStoryViewModel;
import use_case.downloadPDF.DownloadPDFDataAccessInterface;
import use_case.downloadPDF.DownloadPDFInputBoundary;
import use_case.downloadPDF.DownloadPDFInteractor;
import use_case.downloadPDF.DownloadPDFOutputBoundary;
import use_case.lookup.LookupInputBoundary;
import use_case.lookup.LookupInteractor;
import use_case.lookup.LookupOutputBoundary;
import use_case.narrate.NarrateInputBoundary;
import use_case.narrate.NarrateInteractor;
import use_case.narrate.NarrateOutputBoundary;
import view.ReadStoryView;

/**
 * Factory class for creating the ReadStoryView with its associated use cases.
 * This class encapsulates the creation and wiring of the components needed for the ReadStory use case,
 * including narrate, lookup, and download PDF functionalities.
 */
public class ReadStoryUseCaseFactory {
    /**
     * Creates and returns a ReadStoryView with all necessary dependencies and controllers.
     *
     * @param viewManagerModel The model for managing different views.
     * @param readStoryViewModel The view model for reading stories.
     * @param narrateViewModel The view model for narrating text.
     * @param lookupViewModel The view model for looking up words.
     * @param downloadViewModel The view model for downloading PDFs.
     * @param downloadPDFDAO The data access object for downloading PDFs.
     * @return ReadStoryView with all dependencies set up.
     */
    public static ReadStoryView create(ViewManagerModel viewManagerModel, ReadStoryViewModel readStoryViewModel, NarrateViewModel narrateViewModel, LookupViewModel lookupViewModel, DownloadPDFViewModel downloadViewModel, DownloadPDFDataAccessInterface downloadPDFDAO) {

        NarrateController narrateController = createNarrateUseCase(narrateViewModel);

        LookupController lookupController = createLookupUseCase(lookupViewModel);

        DownloadPDFController downloadController = createDownloadUseCase(downloadViewModel, downloadPDFDAO);

        return new ReadStoryView(viewManagerModel, readStoryViewModel, narrateViewModel, narrateController, lookupController, downloadController, lookupViewModel, downloadViewModel);
    }

    /**
     * Creates a DownloadPDFController for handling PDF download use cases.
     *
     * @param downloadViewModel The view model for downloading PDFs.
     * @param downloadPDFDAO The data access object for downloading PDFs.
     * @return DownloadPDFController configured with the necessary interactor and presenter.
     */
    private static DownloadPDFController createDownloadUseCase(DownloadPDFViewModel downloadViewModel, DownloadPDFDataAccessInterface downloadPDFDAO) {
        DownloadPDFOutputBoundary downloadPresenter = new DownloadPDFPresenter(downloadViewModel);
        DownloadPDFInputBoundary downloadInteractor = new DownloadPDFInteractor(downloadPDFDAO, downloadPresenter);

        return new DownloadPDFController(downloadInteractor);
    }

    /**
     * Creates a LookupController for handling lookup use cases.
     *
     * @param lookupViewModel The view model for looking up words.
     * @return LookupController configured with the necessary interactor and presenter.
     */
    private static LookupController createLookupUseCase(LookupViewModel lookupViewModel) {
        LookupOutputBoundary lookupPresenter = new LookupPresenter(lookupViewModel);
        LookupInputBoundary lookupInteractor = new LookupInteractor(lookupPresenter);
        return new LookupController(lookupInteractor);

    }

    /**
     * Creates a NarrateController for handling narrate use cases.
     *
     * @param narrateViewModel The view model for narrating text.
     * @return NarrateController configured with the necessary interactor and presenter.
     */
    private static NarrateController createNarrateUseCase(NarrateViewModel narrateViewModel) {
        NarrateOutputBoundary narratePresenter = new NarratePresenter(narrateViewModel);
        NarrateInputBoundary narrateInteractor = new NarrateInteractor(narratePresenter);
        return new NarrateController(narrateInteractor);
    }
}

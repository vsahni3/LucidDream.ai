package interface_adapter.downloadPDF;

import use_case.downloadPDF.DownloadPDFOutputBoundary;
import use_case.downloadPDF.DownloadPDFOutputData;

/**
 * The DownloadPDFPresenter class is responsible for presenting the result of the download PDF action.
 * It implements the DownloadPDFOutputBoundary interface and updates the view model based on the outcome.
 */
public class DownloadPDFPresenter implements DownloadPDFOutputBoundary {

    private final DownloadPDFViewModel downloadPDFViewModel;

    /**
     * Constructs a new DownloadPDFPresenter with the specified DownloadPDFViewModel.
     *
     * @param downloadPDFViewModel The view model that this presenter will update.
     */
    public DownloadPDFPresenter(DownloadPDFViewModel downloadPDFViewModel) {
        this.downloadPDFViewModel = downloadPDFViewModel;
    }

    /**
     * Prepares the view for a successful PDF download.
     */
    @Override
    public void prepareSuccessView() {
        DownloadPDFState downloadPDFState = downloadPDFViewModel.getState();
        downloadPDFState.setSuccess(true);
        downloadPDFViewModel.setState(downloadPDFState);
        downloadPDFViewModel.firePropertyChanged();
    }

    /**
     * Prepares the view for a failed PDF download.
     */
    @Override
    public void prepareFailureView() {
        DownloadPDFState downloadPDFState = downloadPDFViewModel.getState();
        downloadPDFState.setSuccess(false);
        downloadPDFViewModel.setState(downloadPDFState);
        downloadPDFViewModel.firePropertyChanged();
    }
}

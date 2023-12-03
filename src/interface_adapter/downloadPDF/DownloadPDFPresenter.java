package interface_adapter.downloadPDF;

import use_case.downloadPDF.DownloadPDFOutputBoundary;
import use_case.downloadPDF.DownloadPDFOutputData;

public class DownloadPDFPresenter implements DownloadPDFOutputBoundary {


    private final DownloadPDFViewModel downloadPDFViewModel;

    public DownloadPDFPresenter(DownloadPDFViewModel downloadPDFViewModel) { this.downloadPDFViewModel = downloadPDFViewModel; }


    @Override
    public void prepareSuccessView() {
        DownloadPDFState downloadPDFState = downloadPDFViewModel.getState();
        downloadPDFState.setSuccess(true);
        downloadPDFViewModel.setState(downloadPDFState);
        downloadPDFViewModel.firePropertyChanged();

    }

    @Override
    public void prepareFailureView() {
        DownloadPDFState downloadPDFState = downloadPDFViewModel.getState();
        downloadPDFState.setSuccess(false);
        downloadPDFViewModel.setState(downloadPDFState);
        downloadPDFViewModel.firePropertyChanged();
    }
}

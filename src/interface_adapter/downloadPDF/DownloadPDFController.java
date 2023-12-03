package interface_adapter.downloadPDF;

import use_case.downloadPDF.DownloadPDFInputBoundary;
import use_case.downloadPDF.DownloadPDFInputData;

public class DownloadPDFController {

    final DownloadPDFInputBoundary interactor;

    public DownloadPDFController(DownloadPDFInputBoundary interactor) {
        this.interactor = interactor;
    }

    public void execute(String title) {
        DownloadPDFInputData inputData = new DownloadPDFInputData(title);
        interactor.execute(inputData);
    }
}

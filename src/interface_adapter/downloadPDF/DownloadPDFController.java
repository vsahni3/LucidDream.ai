package interface_adapter.downloadPDF;

import use_case.downloadPDF.DownloadPDFInputBoundary;
import use_case.downloadPDF.DownloadPDFInputData;

/**
 * The DownloadPDFController class is responsible for handling the flow of control in the download PDF feature.
 * It interacts with the DownloadPDFInputBoundary to initiate the PDF download process.
 */
public class DownloadPDFController {

    final DownloadPDFInputBoundary interactor;

    /**
     * Constructs a new DownloadPDFController with the specified DownloadPDFInputBoundary.
     *
     * @param interactor The DownloadPDFInputBoundary that this controller will use to execute download actions.
     */
    public DownloadPDFController(DownloadPDFInputBoundary interactor) {
        this.interactor = interactor;
    }

    /**
     * Initiates the PDF download process for a given title.
     *
     * @param title The title of the content to be downloaded as PDF.
     */
    public void execute(String title) {
        DownloadPDFInputData inputData = new DownloadPDFInputData(title);
        interactor.execute(inputData);
    }
}


package use_case.downloadPDF;

/**
 * The DownloadPDFOutputBoundary interface defines the methods required for presenting the outcome of a PDF download operation.
 */
public interface DownloadPDFOutputBoundary {

    /**
     * Prepares the view model for a successful PDF download.
     */
    void prepareSuccessView();

    /**
     * Prepares the view model for a failed PDF download.
     */
    void prepareFailureView();
}


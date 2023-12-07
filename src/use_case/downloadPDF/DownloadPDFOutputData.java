package use_case.downloadPDF;

/**
 * The DownloadPDFOutputData class encapsulates the output data of a PDF download operation, primarily indicating success or failure.
 */
public class DownloadPDFOutputData {
    private boolean success;

    /**
     * Constructs a new DownloadPDFOutputData with the specified success status.
     *
     * @param success The success status of the PDF download operation.
     */
    public DownloadPDFOutputData(boolean success) {
        this.success = success;
    }

    /**
     * Returns whether the PDF download was successful.
     *
     * @return true if the download was successful, false otherwise.
     */
    public boolean isSuccess() {
        return success;
    }
}

package use_case.downloadPDF;

/**
 * The DownloadPDFInputBoundary interface defines the method for executing the PDF download action.
 */
public interface DownloadPDFInputBoundary {

    /**
     * Executes the PDF download process with the given input data.
     *
     * @param inputData The data required to execute the download process.
     */
    void execute(DownloadPDFInputData inputData);
}

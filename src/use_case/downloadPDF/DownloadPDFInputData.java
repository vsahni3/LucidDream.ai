package use_case.downloadPDF;

/**
 * The DownloadPDFInputData class encapsulates the input data required for downloading a PDF.
 */
public class DownloadPDFInputData {

    final private String title;

    /**
     * Constructs a new DownloadPDFInputData with the specified title.
     *
     * @param title The title of the content to download as a PDF.
     */
    public DownloadPDFInputData(String title) {
        this.title = title;
    }

    /**
     * Returns the title associated with this input data.
     *
     * @return The title.
     */
    String getTitle() {
        return title;
    }
}

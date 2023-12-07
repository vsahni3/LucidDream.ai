package interface_adapter.downloadPDF;

/**
 * The DownloadPDFState class holds the state information for a PDF download operation.
 * It tracks whether the download was successful.
 */
public class DownloadPDFState {

    private boolean success;

    /**
     * Constructs a new DownloadPDFState as a copy of an existing state.
     *
     * @param copy The existing DownloadPDFState to copy.
     */
    public DownloadPDFState(DownloadPDFState copy) {
        success = copy.success;
    }

    /**
     * Constructs a new DownloadPDFState with default values.
     */
    public DownloadPDFState() {}

    /**
     * Clears the current state, setting success to false.
     */
    public void clearState() { success = false; }

    /**
     * Returns whether the download was successful.
     *
     * @return true if the download was successful, false otherwise.
     */
    public boolean isSuccess() { return success; }

    /**
     * Sets the success status of the download.
     *
     * @param success The success status to set.
     */
    public void setSuccess(boolean success) { this.success = success; }
}

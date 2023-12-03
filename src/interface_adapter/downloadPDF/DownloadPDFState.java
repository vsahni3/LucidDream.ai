package interface_adapter.downloadPDF;

public class DownloadPDFState {

    private boolean success;

    public DownloadPDFState(DownloadPDFState copy) {
        success = copy.success;
    }

    public DownloadPDFState() {}

    public void clearState() { success = false; }

    public boolean isSuccess() { return success; }

    public void setSuccess(boolean success) { this.success = success; }

}

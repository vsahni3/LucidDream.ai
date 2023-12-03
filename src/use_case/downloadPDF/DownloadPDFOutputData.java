package use_case.downloadPDF;

public class DownloadPDFOutputData {
    private boolean success;

    public DownloadPDFOutputData(boolean success) {
        this.success = success;
    }

    public boolean isSuccess() {
        return success;
    }
}

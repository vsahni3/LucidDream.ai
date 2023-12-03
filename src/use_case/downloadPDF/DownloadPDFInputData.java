package use_case.downloadPDF;

public class DownloadPDFInputData {

    final private String title;

    public DownloadPDFInputData(String title) {
        this.title = title;
    }

    String getTitle() {
        return title;
    }
}

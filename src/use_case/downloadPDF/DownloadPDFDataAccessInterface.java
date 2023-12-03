package use_case.downloadPDF;

import entity.StoryBook;

public interface DownloadPDFDataAccessInterface {
    StoryBook getBook(String title);
}

package use_case.downloadPDF;

import entity.StoryBook;

/**
 * The DownloadPDFDataAccessInterface defines the necessary methods for accessing data related to PDF downloads.
 */
public interface DownloadPDFDataAccessInterface {

    /**
     * Retrieves a StoryBook object based on the provided title.
     *
     * @param title The title of the book to retrieve.
     * @return The StoryBook object associated with the given title.
     */
    StoryBook getBook(String title);
}

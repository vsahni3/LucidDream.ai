package use_case.downloadPDF;

import entity.Page;
import entity.StoryBook;
import org.junit.jupiter.api.Test;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class downloadPDFInteractorTest {

    @Test
    void successTest() {
        DownloadPDFInputData inputData = new DownloadPDFInputData("Valid Book Title");
        DownloadPDFDataAccessInterface dataAccess = title -> new StoryBook("Valid Book Title", createSamplePages());

        DownloadPDFOutputBoundary successOutput = new DownloadPDFOutputBoundary() {
            @Override
            public void prepareSuccessView() {
                // Success case assertion
                assertTrue(true);  // Dummy assertion for success case
            }

            @Override
            public void prepareFailureView() {
                fail("Use case failure is unexpected.");
            }
        };

        DownloadPDFInteractor interactor = new DownloadPDFInteractor(dataAccess, successOutput);
        interactor.execute(inputData);
    }

    private ArrayList<Page> createSamplePages() {
        ArrayList<Page> pages = new ArrayList<>();
        byte[] mockImage = createMockImage();
        pages.add(new Page("Page 1 Content", 1, mockImage, 1));
        pages.add(new Page("Page 2 Content", 2, mockImage, 2));
        return pages;
    }

    private byte[] createMockImage() {
        // Create a simple image and convert it to a byte array for testing
        BufferedImage img = new BufferedImage(100, 100, BufferedImage.TYPE_INT_RGB);
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        try {
            ImageIO.write(img, "jpg", baos);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return baos.toByteArray();
    }



    @Test
    void failureNonExistentBookTest() {
        DownloadPDFInputData inputData = new DownloadPDFInputData("Nonexistent Book Title");
        DownloadPDFDataAccessInterface dataAccess = title -> null;  // Book does not exist

        DownloadPDFOutputBoundary failureOutput = new DownloadPDFOutputBoundary() {
            @Override
            public void prepareSuccessView() {
                fail("Use case success is unexpected.");
            }

            @Override
            public void prepareFailureView() {
                assertTrue(true);  // Dummy assertion for failure case
            }
        };

        DownloadPDFInteractor interactor = new DownloadPDFInteractor(dataAccess, failureOutput);
        interactor.execute(inputData);
    }

    @Test
    void failureEmptyBookTest() {
        DownloadPDFInputData inputData = new DownloadPDFInputData("Empty Book Title");
        DownloadPDFDataAccessInterface dataAccess = title -> new StoryBook("Empty Book Title", new ArrayList<>());

        DownloadPDFOutputBoundary failureOutput = new DownloadPDFOutputBoundary() {
            @Override
            public void prepareSuccessView() {
                fail("Use case success is unexpected.");
            }

            @Override
            public void prepareFailureView() {
                assertTrue(true);  // Dummy assertion for failure case
            }
        };

        DownloadPDFInteractor interactor = new DownloadPDFInteractor(dataAccess, failureOutput);
        interactor.execute(inputData);
    }


}

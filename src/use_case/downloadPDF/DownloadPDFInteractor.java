package use_case.downloadPDF;

import entity.Page;
import entity.PageFactory;
import entity.StoryBook;
import entity.StoryBookFactory;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.apache.pdfbox.pdmodel.font.PDFont;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.apache.pdfbox.pdmodel.graphics.image.PDImageXObject;
import use_case.generate.GenerateOutputBoundary;
import use_case.generate.GenerateUserDataAccessInterface;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;

public class DownloadPDFInteractor implements DownloadPDFInputBoundary {
    final DownloadPDFDataAccessInterface dataAccess;
    final DownloadPDFOutputBoundary downloadPDFPresenter;

    public DownloadPDFInteractor(DownloadPDFDataAccessInterface dataAccess, DownloadPDFOutputBoundary downloadPDFOutputBoundary) {
        this.dataAccess = dataAccess;
        this.downloadPDFPresenter = downloadPDFOutputBoundary;
    }


    // Create PDF document from array of Page2 objects
    public static void createPdfFromPages(Page[] pages, String title) throws IOException {
        try (PDDocument document = new PDDocument()) {
            for (int i = 0; i < pages.length; i++) {
                Page page2 = pages[i];
                PDPage pdfPage = configurePage(document, PDRectangle.A4); // Create and configure a new page

                PDImageXObject pdImage = PDImageXObject.createFromByteArray(document, page2.getImage(), null);

                // Configurable parameters for image and text
                int imageWidth = 450;
                int imageHeight = 350;
                float textFontSize = 12;
                float textMargin = 60;

                addContentToPage(document, pdfPage, pdImage, imageWidth, imageHeight, page2.getTextContents(), textFontSize, textMargin);

                // Add page number
                addPageNumber(document, pdfPage, i + 1);
            }

            String fileName = toPdfFileName(title);

            document.save("stories/" + fileName);
        }
    }

    private static String toPdfFileName(String title) {

        // Replace invalid characters and spaces
        String safeTitle = title.replaceAll("[\\\\/:*?\"<>|]", "").replaceAll("\\s+", "_");

        // Ensure it ends with .pdf
        if (!safeTitle.toLowerCase().endsWith(".pdf")) {
            safeTitle += ".pdf";
        }

        return safeTitle;
    }


    // Configure a new PDF page with specified size
    private static PDPage configurePage(PDDocument document, PDRectangle pageSize) {
        PDPage page = new PDPage(pageSize);
        document.addPage(page);
        return page;
    }

    // Add image and text content to a PDF page
    private static void addContentToPage(PDDocument document, PDPage page, PDImageXObject image, int imageWidth, int imageHeight, String text, float fontSize, float margin) throws IOException {
        PDRectangle mediaBox = page.getMediaBox();
        float pageWidth = mediaBox.getWidth();
        float pageHeight = mediaBox.getHeight();
        text = text.replace("\n", "").replace("\r", "");


        float startX = (pageWidth - imageWidth) / 2;
        float startY = (pageHeight - imageHeight) / 2;

        try (PDPageContentStream contentStream = new PDPageContentStream(document, page)) {
            // Draw the centered image
            contentStream.drawImage(image, startX, startY, imageWidth, imageHeight);

            // Add and format text
            addFormattedText(contentStream, text, fontSize, margin, startX, startY - 50, page);
        }
    }

    // Add formatted text to the page
    private static void addFormattedText(PDPageContentStream contentStream, String text, float fontSize, float margin, float x, float y, PDPage page) throws IOException {
        PDFont font = PDType1Font.HELVETICA;
        contentStream.beginText();
        contentStream.setFont(font, fontSize);
        contentStream.newLineAtOffset(x, y);

        String[] words = text.split(" ");
        String line = "";
        float maxWidth = page.getMediaBox().getWidth() - 2 * margin;
        for (String word : words) {
            float size = fontSize * font.getStringWidth(line + word) / 1000;
            if (size > maxWidth) {
                contentStream.showText(line);
                contentStream.newLineAtOffset(0, -fontSize * 1.5f);
                line = word + " ";
            } else {
                line += word + " ";
            }
        }
        contentStream.showText(line); // Draw the last line
        contentStream.endText();
    }

    // Add page number to the bottom of the page
    private static void addPageNumber(PDDocument document, PDPage page, int pageNumber) throws IOException {
        PDFont font = PDType1Font.HELVETICA;
        float fontSize = 10;
        PDRectangle pageSize = page.getMediaBox();
        float x = pageSize.getLowerLeftX() + pageSize.getWidth() / 2;
        float y = pageSize.getLowerLeftY() + 30; // Adjust as necessary

        try (PDPageContentStream contentStream = new PDPageContentStream(document, page, PDPageContentStream.AppendMode.APPEND, true, true)) {
            contentStream.beginText();
            contentStream.setFont(font, fontSize);
            contentStream.newLineAtOffset(x, y);
            contentStream.showText("Page " + pageNumber);
            contentStream.endText();
        }
    }

    @Override
    public void execute(DownloadPDFInputData inputData) {
        StoryBook storybook = dataAccess.getBook(inputData.getTitle());
        System.out.println(inputData.getTitle());

        if (storybook == null || storybook.getPages().isEmpty()) {
            System.out.println("Couldnt find the book");
            downloadPDFPresenter.prepareFailureView();
            return;
        }

        ArrayList<Page> pages = storybook.getPages();
        Page[] pagesArray = pages.toArray(new Page[0]);

        try {
            createPdfFromPages(pagesArray, inputData.getTitle());
            downloadPDFPresenter.prepareSuccessView();
        } catch (Exception e) {
            downloadPDFPresenter.prepareFailureView();
        }
    }

}

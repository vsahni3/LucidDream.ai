package use_case.exportPDF;

import entity.Page;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Arrays;
import javax.imageio.ImageIO;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.apache.pdfbox.pdmodel.font.PDFont;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.apache.pdfbox.pdmodel.graphics.image.PDImageXObject;

public class dynamicExport {

    private static final Log log = LogFactory.getLog(dynamicExport.class);

    public static void main(String[] args) {
        try {
            // Sample text and page numbers for pages
            String[] sampleTexts = {
                    "Hello, this is page 1",
                    "Welcome to page 2",
                    "This is the third page"
            };

            // Create a simple image and convert it to byte array
            BufferedImage img = createSampleImage();
            byte[] imageBytes = convertImageToByteArray(img);

            // Initialize array of Page objects
            Page[] pages = new Page[sampleTexts.length];
            for (int i = 0; i < sampleTexts.length; i++) {
                pages[i] = new Page(sampleTexts[i], i + 1, imageBytes, i);
            }

            createPdfFromPages(pages);
        } catch (IOException e) {
            log.error("Error occurred while creating PDF", e);
        }
    }

    // Create a sample image for testing
    private static BufferedImage createSampleImage() {
        BufferedImage img = new BufferedImage(100, 100, BufferedImage.TYPE_INT_RGB);
        // Image creation logic here (e.g., drawing shapes or text)
        return img;
    }

    // Convert BufferedImage to byte array
    private static byte[] convertImageToByteArray(BufferedImage img) throws IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ImageIO.write(img, "jpg", baos);
        return baos.toByteArray();
    }

    // Create PDF document from array of Page2 objects
    private static void createPdfFromPages(Page[] pages) throws IOException {
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

            document.save("src/use_case/exportPDF/test.pdf");
            log.info("PDF created successfully.");
        }
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
        PDFont font = PDType1Font.TIMES_ROMAN;
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
}

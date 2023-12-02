package entity;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class PageTest {

    @Test
    void testPageCreationAndAttributes() {
        byte[] image = new byte[10]; // Dummy image data
        Page page = new Page("Text Contents", 1, image, 123);

        assertEquals("Text Contents", page.getTextContents(), "Text contents should match");
        assertEquals(1, page.getPageNumber(), "Page number should match");
        assertEquals(123, page.getPageID(), "Page ID should match");
        assertArrayEquals(image, page.getImage(), "Image data should match");
    }

    @Test
    void testSetPageID() {
        Page page = new Page("Text", 1, new byte[10], 123);
        page.setPageID(456);
        assertEquals(456, page.getPageID(), "Page ID should be updated");
    }

    @Test
    void testSetTextContents() {
        Page page = new Page("Text", 1, new byte[10], 123);
        page.setTextContents("New Text");
        assertEquals("New Text", page.getTextContents(), "Text contents should be updated");
    }
}

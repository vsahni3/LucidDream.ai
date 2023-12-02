package entity;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class PageFactoryTest {

    @Test
    void testCreate() {
        PageFactory factory = new PageFactory();
        byte[] image = new byte[10]; // Dummy image data
        Page page = factory.create("Text Contents", 1, image, 123);

        assertNotNull(page, "Page should not be null");
        assertEquals("Text Contents", page.getTextContents(), "Text contents should match");
        assertEquals(1, page.getPageNumber(), "Page number should match");
        assertEquals(123, page.getPageID(), "Page ID should match");
        assertArrayEquals(image, page.getImage(), "Image data should match");
    }
}

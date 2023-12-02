package entity;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.util.ArrayList;

public class StoryBookFactoryTest {

    @Test
    public void testCreateWithEmptyTitleAndPages() {
        StoryBookFactory factory = new StoryBookFactory();
        StoryBook storyBook = factory.create("", new ArrayList<>());

        assertNotNull(storyBook, "StoryBook should not be null");
        assertEquals("", storyBook.getTitle());
        assertTrue(storyBook.getPages().isEmpty(), "Pages list should be empty");

    }

    @Test
    public void testCreateWithValidTitleAndEmptyPages() {
        StoryBookFactory factory = new StoryBookFactory();
        StoryBook storyBook = factory.create("My Story Book", new ArrayList<>());

        assertNotNull(storyBook, "StoryBook should not be null");
        assertEquals("My Story Book", storyBook.getTitle());
        assertTrue(storyBook.getPages().isEmpty(), "Pages list should be empty");
    }

    @Test
    public void testCreateWithValidTitleAndPages() {
        StoryBookFactory factory = new StoryBookFactory();
        ArrayList<Page> pages = new ArrayList<>();
        pages.add(new Page("Page 1", 1, new byte[0], -1)); // Example page

        StoryBook storyBook = factory.create("Adventure Story", pages);

        assertNotNull(storyBook, "StoryBook should not be null");
        assertEquals("Adventure Story", storyBook.getTitle());
        assertFalse(storyBook.getPages().isEmpty(), "Pages list should not be empty");
        assertEquals(1, storyBook.getPages().size(), "Pages list should have 1 page");
    }

}


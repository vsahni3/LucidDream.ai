package entity;
import org.junit.Test;
import static org.junit.Assert.*;
import java.util.ArrayList;

public class StoryBookFactoryTest {

    @Test
    public void testCreateWithEmptyTitleAndPages() {
        StoryBookFactory factory = new StoryBookFactory();
        StoryBook storyBook = factory.create("", new ArrayList<>());

        assertNotNull("StoryBook should not be null", storyBook);
        assertEquals("Title should be empty", "", storyBook.getTitle());
        assertTrue("Pages list should be empty", storyBook.getPages().isEmpty());
    }

    @Test
    public void testCreateWithValidTitleAndEmptyPages() {
        StoryBookFactory factory = new StoryBookFactory();
        StoryBook storyBook = factory.create("My Story Book", new ArrayList<>());

        assertNotNull("StoryBook should not be null", storyBook);
        assertEquals("Title should be 'My Story Book'", "My Story Book", storyBook.getTitle());
        assertTrue("Pages list should be empty", storyBook.getPages().isEmpty());
    }

    @Test
    public void testCreateWithValidTitleAndPages() {
        StoryBookFactory factory = new StoryBookFactory();
        ArrayList<Page> pages = new ArrayList<>();
        pages.add(new Page("Page 1", 1, new byte[0], -1)); // Example page

        StoryBook storyBook = factory.create("Adventure Story", pages);

        assertNotNull("StoryBook should not be null", storyBook);
        assertEquals("Title should be 'Adventure Story'", "Adventure Story", storyBook.getTitle());
        assertFalse("Pages list should not be empty", storyBook.getPages().isEmpty());
        assertEquals("Pages list should have 1 page", 1, storyBook.getPages().size());
    }

}


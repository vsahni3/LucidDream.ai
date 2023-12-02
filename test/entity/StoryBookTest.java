package entity;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class StoryBookTest {

    @Test
    void testStoryBookCreationAndAttributes() {
        ArrayList<Page> pages = new ArrayList<>();
        pages.add(new Page("Page 1", 1, new byte[10], 1));
        StoryBook book = new StoryBook("Title", pages);

        assertEquals("Title", book.getTitle(), "Title should match");
        assertEquals(pages, book.getPages(), "Pages should match");
    }

    @Test
    void testSetTitleAndSetPages() {
        StoryBook book = new StoryBook("Old Title", new ArrayList<>());
        ArrayList<Page> newPages = new ArrayList<>();
        newPages.add(new Page("Page 1", 1, new byte[10], 1));

        book.setTitle("New Title");
        book.setPages(newPages);

        assertEquals("New Title", book.getTitle(), "Title should be updated");
        assertEquals(newPages, book.getPages(), "Pages should be updated");
    }

    @Test
    void testAddPage() {
        StoryBook book = new StoryBook("Title", new ArrayList<>());
        Page newPage = new Page("Page 1", 1, new byte[10], 1);

        book.addPage(newPage);

        assertTrue(book.getPages().contains(newPage), "New page should be added");
    }
}

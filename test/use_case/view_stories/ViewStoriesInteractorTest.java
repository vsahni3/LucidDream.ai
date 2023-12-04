package use_case.view_stories;

import data_access.InMemoryDAO;
import entity.*;
import use_case.login.LoginUserDataAccessInterface;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;


class ViewStoriesInteractorTest {
    @Test
    void successTest() {

        ViewStoriesDataAccessInterface userRepository = new InMemoryDAO();
        UserFactory factory = new CommonUserFactory();
        User user = factory.create("bob", "password");

        // Create fake book
        Page testPage = new Page("once upon a time", 1, new byte[] {}, 69);
        ArrayList<Page> testPages = new ArrayList<>();
        testPages.add(testPage);
        StoryBook testBook = new StoryBook("test story", testPages);

        // Create fake book collection
        ArrayList<StoryBook> testStorybooks = new ArrayList<>();
        testStorybooks.add(testBook);
        user.setStoryBooks(testStorybooks);
        ((InMemoryDAO) userRepository).save(user);


        ViewStoriesOutputBoundary successPresenter = new ViewStoriesOutputBoundary() {
            @Override
            public void prepareSuccessView(ViewStoriesOutputData storybooks) {
                ArrayList<StoryBook> expectedStoryBooks = storybooks.getStoryBooks();

                // Check if user only has one book in their collection
                assertEquals(1, expectedStoryBooks.size());

                StoryBook book = expectedStoryBooks.get(0);

                // Check if the titles match
                assertEquals("test story", book.getTitle());

                ArrayList<Page> pages = book.getPages();

                // Check if the book has only one page
                assertEquals(1, pages.size());

                // Check if page has all the defined properties above
                Page page = pages.get(0);
                assertEquals("once upon a time", page.getTextContents());
                assertEquals(1, page.getPageNumber());
                assertEquals(69, page.getPageID());
                assertEquals(0, page.getImage().length);

            }
        };


        ViewStoriesInputData inputData = new ViewStoriesInputData("bob");
        ViewStoriesInputBoundary interactor = new ViewStoriesInteractor(userRepository, successPresenter);
        interactor.execute(inputData);

    }

}



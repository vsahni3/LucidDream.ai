package entity;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import static org.junit.jupiter.api.Assertions.*;

class CommonUserTest {

    private CommonUser user;
    private StoryBook storyBook1;
    private StoryBook storyBook2;

    @BeforeEach
    void setUp() {
        user = new CommonUser("username", "password");
        storyBook1 = new StoryBook("Title1", new ArrayList<>());
        storyBook2 = new StoryBook("Title2", new ArrayList<>());
    }

    @Test
    void testUserCreation() {
        assertEquals("username", user.getUserName(), "Username should match");
        assertEquals("password", user.getPassword(), "Password should match");
        assertTrue(user.getStoryBooks().isEmpty(), "Storybooks should initially be empty");
    }

    @Test
    void testSetUserNameAndPassword() {
        user.setUserName("newUsername");
        user.setPassword("newPassword");

        assertEquals("newUsername", user.getUserName(), "Username should be updated");
        assertEquals("newPassword", user.getPassword(), "Password should be updated");
    }

    @Test
    void testAddStoryBook() {
        user.addStoryBook(storyBook1);
        assertEquals(Arrays.asList(storyBook1), user.getStoryBooks(), "Storybook should be added");

        user.addStoryBook(storyBook2);
        assertEquals(Arrays.asList(storyBook1, storyBook2), user.getStoryBooks(), "Second storybook should be added");
    }

    @Test
    void testDeleteStoryBook() {
        user.addStoryBook(storyBook1);
        user.addStoryBook(storyBook2);
        user.deleteStoryBook(storyBook1);

        assertEquals(Arrays.asList(storyBook2), user.getStoryBooks(), "First storybook should be deleted");
    }

    @Test
    void testSetStoryBooks() {
        ArrayList<StoryBook> newStoryBooks = new ArrayList<>(Arrays.asList(storyBook1));
        user.setStoryBooks(newStoryBooks);

        assertEquals(newStoryBooks, user.getStoryBooks(), "Storybooks should be updated");
    }
}

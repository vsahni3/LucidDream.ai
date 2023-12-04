package data_access;
import entity.Page;
import entity.User;
import java.util.HashMap;
import java.util.Map;
import entity.StoryBook;
import use_case.generate.GenerateUserDataAccessInterface;
import use_case.login.LoginUserDataAccessInterface;
import use_case.signup.SignupUserDataAccessInterface;
import use_case.view_stories.ViewStoriesDataAccessInterface;

import java.util.ArrayList;

/**
 * CombinedDAO is a data access object class that integrates the operations of user, book, and page data.
 * It utilizes individual data access objects (DAOs) for users, books, and pages to perform complex
 * operations that involve multiple entities. This class serves as a unified interface to handle
 * user-related, book-related, and page-related data transactions in a cohesive manner.
 *
 * It acts as an aggregator of SqlUserDataAccessObject, SqlBookDataAccessObject, and SqlPageDataAccessObject,
 * facilitating operations that span across these entities, such as loading and saving users along with their
 * associated books and pages. The class ensures that the data integrity is maintained across these different
 * entities in the database.
 *
 * Usage of this class simplifies interactions with the database for operations involving complex relationships
 * between users, books, and pages.
 */
public class InMemoryDAO implements GenerateUserDataAccessInterface, LoginUserDataAccessInterface, SignupUserDataAccessInterface, ViewStoriesDataAccessInterface {

    private final Map<String, User> users = new HashMap<>();


    /**
     * Saves a user and their associated books and pages to the database.
     * @param user the User object to be saved.
     */
    @Override
    public void save(User user) {
        users.put(user.getUserName(), user);
    }

    /**
     * Retrieves a User object based on a given username.
     * @param userName the username to search for.
     * @return the User object associated with the given username.
     */
    @Override
    public User getUser(String userName) {
        return users.get(userName);
    }


    /**
     * Checks whether a user exists in the database based on a username identifier.
     * @param identifier the username to check for existence.
     * @return true if the user exists, false otherwise.
     */
    @Override
    public boolean existsUser(String identifier) {
        return users.containsKey(identifier);
    }


    @Override
    public ArrayList<StoryBook> getStoryBooks(String username) {
        return users.get(username).getStoryBooks();
    }
}


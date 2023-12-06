package use_case.login;

import entity.User;

/**
 * Interface for data access operations related to user login.
 * This interface defines methods for checking if a user exists, saving a user, and retrieving a user.
 * Implementations of this interface are responsible for the actual data handling logic,
 * which might involve database interactions or other forms of data storage and retrieval.
 */
public interface LoginUserDataAccessInterface {

    /**
     * Checks if a user with the specified username exists.
     *
     * @param username The username to check for existence.
     * @return true if a user with the given username exists, false otherwise.
     */
    boolean existsUser(String username);

    /**
     * Saves the user information.
     * This method is used to persist user data, for example, creating a new user or updating an existing user's data.
     *
     * @param user The User object containing the user's data to be saved.
     */
    void save(User user);

    /**
     * Retrieves the user with the specified username.
     * This method is used to fetch user data based on the username.
     *
     * @param username The username of the user to retrieve.
     * @return The User object corresponding to the specified username, or null if no such user exists.
     */
    User getUser(String username);
}

package use_case.signup;

import entity.User;

/**
 * Interface for data access operations related to user signup.
 * This interface defines methods for operations such as checking if a user already exists and saving a new user.
 * Implementations of this interface are responsible for the actual data handling logic,
 * which might involve interactions with a database or other forms of data storage.
 */
public interface SignupUserDataAccessInterface {

    /**
     * Checks if a user with the specified identifier already exists.
     * This method is primarily used to prevent duplicate user registrations.
     *
     * @param identifier The unique identifier for the user, typically a username or email address.
     * @return true if a user with the given identifier exists, false otherwise.
     */
    boolean existsUser(String identifier);

    /**
     * Saves a new user to the data storage.
     * This method is used for persisting user information during the signup process.
     *
     * @param user The {@link User} entity representing the user to be saved.
     */
    void save(User user);
}

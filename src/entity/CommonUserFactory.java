package entity;

import java.time.LocalDateTime;
import java.util.ArrayList;

/**
 * CommonUserFactory is a class that implements the UserFactory interface,
 * providing a method to create instances of CommonUser. It specializes in the
 * creation of CommonUser objects with the specified username and password.
 *
 * <p>The class follows the factory pattern, encapsulating the instantiation
 * logic for CommonUser objects. It ensures that the creation of CommonUser
 * instances adheres to specific requirements, such as a valid password.
 *
 * <p>Instances of CommonUserFactory are used to centralize the process of
 * creating CommonUser objects, promoting consistency and maintainability
 * in the creation of user entities within a larger application context.
 */

public class CommonUserFactory implements UserFactory {
    /**
     * Requires: password is valid.
     * @param userName
     * @param password
     * @return
     */

    @Override
    public User create(String userName, String password) {
        return new CommonUser(userName, password);
    }
}

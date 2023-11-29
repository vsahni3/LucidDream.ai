package entity;

/**
 * PasswordValidatorService is a class that implements the PasswordValidator interface,
 * providing a simple password validation service. It checks whether a given password is
 * valid based on a predefined set of criteria.
 *
 * <p>The class follows the strategy pattern, encapsulating the logic for password validation
 * in a separate service. The validation criteria in this implementation require the password
 * to be non-null and have a length greater than 5 characters to be considered valid.
 *
 * <p>Instances of PasswordValidatorService are used to perform consistent and standardized
 * password validation within a larger application context, ensuring that passwords adhere
 * to the defined criteria.
 *
 * @author YourName
 * @version 1.0
 */

public class PasswordValidatorService implements PasswordValidator {
    public boolean passwordIsValid(String password) {
        return password != null && password.length() > 5;
    }
}

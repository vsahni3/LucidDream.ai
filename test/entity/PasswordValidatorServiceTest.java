package entity;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class PasswordValidatorServiceTest {

    @Test
    void testPasswordIsValid() {
        PasswordValidatorService validator = new PasswordValidatorService();

        assertTrue(validator.passwordIsValid("password123"), "Valid password should return true");
        assertFalse(validator.passwordIsValid("pass"), "Invalid password should return false");
        assertFalse(validator.passwordIsValid(null), "Null password should return false");
    }
}

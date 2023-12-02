package entity;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class CommonUserFactoryTest {

    @Test
    void testCreate() {
        CommonUserFactory factory = new CommonUserFactory();
        User user = factory.create("username", "password");

        assertNotNull(user, "User should not be null");
        assertEquals("username", user.getUserName(), "Username should match");
        assertEquals("password", user.getPassword(), "Password should match");
    }
}

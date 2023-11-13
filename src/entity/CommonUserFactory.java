package entity;

import java.time.LocalDateTime;

public class CommonUserFactory implements UserFactory {
    /**
     * Requires: password is valid.
     * @param userName
     * @param password
     * @return
     */

    @Override
    public User create(String userName, String password, LocalDateTime ltd) {
        return new CommonUser(userName, password, ltd);
    }
}

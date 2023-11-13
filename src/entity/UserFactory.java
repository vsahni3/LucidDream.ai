package entity;

import java.time.LocalDateTime;

public interface UserFactory {
    /** Requires: password is valid. */
    User create(String userName, String password, LocalDateTime ltd);
}

package entity;

import java.time.LocalDateTime;

public interface UserFactory {
    User create(String username, String password, LocalDateTime now);
}

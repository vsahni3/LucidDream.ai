package entity;

import java.time.LocalDateTime;

public interface User {

    String getUserName();

    String getPassword();

    LocalDateTime getCreationTime();
}

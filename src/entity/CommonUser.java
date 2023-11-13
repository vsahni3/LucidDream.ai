package entity;

import java.time.LocalDateTime;

class CommonUser implements User {

    private final String userName;
    private final String password;
    private final LocalDateTime creationTime;

    /**
     * Requires: password is valid.
     * @param userName
     * @param password
     */
    public CommonUser(String userName, String password, LocalDateTime creationTime) {
        this.userName = userName;
        this.password = password;
        this.creationTime = creationTime;
    }

    @Override
    public String getUserName() {
        return userName;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public LocalDateTime getCreationTime() {
        return creationTime;
    }
}

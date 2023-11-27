package use_case.login;

import entity.User;

public interface LoginUserDataAccessInterface {
    boolean existsUser(String username);

    void save(User user);

    User getUser(String username);
}

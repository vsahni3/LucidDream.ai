package use_case.login;

import entity.User;

public interface LoginUserDataAccessInterface {
    boolean existsByName(String username);

    void save(User user);

    User get(String username);
}

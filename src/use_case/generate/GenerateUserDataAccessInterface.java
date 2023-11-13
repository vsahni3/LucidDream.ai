package use_case.generate;

import entity.User;

public interface GenerateUserDataAccessInterface {

    void save(User user);

    User get(String username);
}

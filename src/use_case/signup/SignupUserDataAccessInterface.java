package use_case.signup;

import entity.User;

public interface SignupUserDataAccessInterface {
    boolean existsUser(String identifier);

    void save(User user);
}

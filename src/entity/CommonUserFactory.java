package entity;

import java.time.LocalDateTime;
import java.util.ArrayList;

public class CommonUserFactory implements UserFactory {
    /**
     * Requires: password is valid.
     * @param userName
     * @param password
     * @return
     */

    @Override
    public User create(String userName, String password, ArrayList<StoryBook> storybooks) {
        return new CommonUser(userName, password, storybooks);
    }
}

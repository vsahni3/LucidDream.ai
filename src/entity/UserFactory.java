package entity;

import java.time.LocalDateTime;
import java.util.ArrayList;

public interface UserFactory {
    /** Requires: password is valid. */
    User create(String userName, String password, LocalDateTime ltd, ArrayList<StoryBook> storybooks);
}

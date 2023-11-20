package entity;

import java.time.LocalDateTime;
import java.util.ArrayList;

public interface User {

    String getUserName();

    String getPassword();

    ArrayList<StoryBook> getStoryBooks();
}

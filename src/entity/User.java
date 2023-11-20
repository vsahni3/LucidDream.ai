package entity;

import java.time.LocalDateTime;
import java.util.ArrayList;

public interface User {

    String getUserName();

    String getPassword();

    ArrayList<StoryBook> getStoryBooks();

    void setUserName(String newUserName);

    void setPassword(String newPassword);

    void setStoryBooks(ArrayList<StoryBook> newStoryBooks);

    void addStoryBook(StoryBook newStoryBook);

    void deleteStoryBook(StoryBook deletedStoryBook);
}

package data_access;

import entity.Page;
import entity.StoryBook;
import entity.User;

public interface CombinedDataAcessInterface {
    void save(User user);
    User getUser(String userName);

    StoryBook getBook(String title);
    Page getPage(Integer id);
    boolean existsUser(String identifier);
    boolean existsBook(String identifier);
    boolean existsPage(Integer identifier);
}

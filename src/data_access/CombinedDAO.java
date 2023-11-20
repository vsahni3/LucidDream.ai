package data_access;
import entity.Page;
import entity.User;
import java.util.HashMap;
import java.util.Map;
import entity.StoryBook;
import java.util.ArrayList;
public class CombinedDAO implements CombinedDataAcessInterface {
    private SqlUserDataAccessObject userDAO;
    private SqlBookDataAccessObject bookDAO;
    private SqlPageDataAccessObject pageDAO;
    private final Map<String, User> users = new HashMap<>();


    public UserAndBook(SqlUserDataAccessObject userDAO, SqlBookDataAccessObject bookDAO, SqlPageDataAccessObject pageDAO) {
        this.userDAO = userDAO;
        this.pageDAO = pageDAO;
        this.bookDAO = bookDAO;

        loadData(users);
    }

    public void loadData(Map<String, User> map) {
        Map<String, User> tempUsers = userDAO.loadAll();


        for (String userName : tempUsers.keySet()) {
            User user = tempUsers.get(userName);
            ArrayList<StoryBook> userBooks = bookDAO.getUserBooks(userName);
            for (StoryBook book : userBooks) {
                ArrayList<Page> bookPages = pageDAO.getBookPages(book.getTitle());
                book.setPages(bookPages);
            }
            user.setBooks(userBooks);
            map.put(userName, user);


        }

    }

    @Override
    public void save(User user) {
        users.put(user.getUserName(), user);
        this.save();
    }

    @Override
    public User getUser(String userName) {
        return users.get(userName);
    }

    @Override
    public StoryBook getBook(String title) {
        for (String userName : users.keySet()) {
            User user = users.get(userName);
            for (StoryBook book : user.getBooks()) {
                if (book.getTitle().equals(title)) {
                    return book;
                }
            }
        }
    }

    @Override
    public Page getPage(Integer id) {

        // you could also implement a getPage for the pageDAO as the page doesn't have any extra info about other entities
        for (String userName : users.keySet()) {
            User user = users.get(userName);
            for (StoryBook book : user.getBooks()) {
                for (Page page : book.getPages())
                    if (page.getId().equals(id)) {
                        return page;
                    }
            }
        }
    }

    @Override
    public boolean existsUser(String identifier) {
        return users.containsKey(identifier);
    }

    @Override
    public boolean existsBook(String identifier) {
        return bookDAO.existsBook(identifier);
    }

    @Override
    public boolean existsPage(Integer identifier) {
        return pageDAO.existsPage(identifier);
    }

    private void save() {
        Map<String, User> tempMap = new HashMap<>();
        loadData(tempMap);

        for (String username : users.keySet()) {
            User user = users.get(username);
            userDAO.saveUser(user);
            bookDAO.saveBooks(user.getBooks(), username);
            for (StoryBook book : user.getBooks()) {
                pageDAO.savePages(book.getPages(), book.getTitle());
            }
        }

    }






}

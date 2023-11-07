package data_access;

import entity.User;
import entity.UserFactory;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class CombinedDAO {
    private SqlUserDataAccessObject userDAO;
    private SqlBookDataAccessObject bookDAO;
    private SqlPageDataAccessObject pageDAO;
    private final Map<String, User> users = new HashMap<>();
    private UserFactory userFactory;
    private BookFactory bookFactory;

    public UserAndBook(SqlUserDataAccessObject userDAO, SqlBookDataAccessObject bookDAO, UserFactory userFactory, SqlPageDataAccessObject pageDAO) {
        this.userDAO = userDAO;
        this.pageDAO = pageDAO;
        this.bookDAO = bookDAO;
        this.userFactory = userFactory;
        loadData(users);
    }

    public void loadData(Map<String, User> map) {
        Map<String, User> tempUsers = userDAO.loadAll();


        for (String username : tempUsers.keySet()) {
            User user = tempUsers.get(username);
            ArrayList<Book> userBooks = bookDAO.getUserBooks(username);
            for (Book book : userBooks) {
                ArrayList<Page> bookPages = pageDAO.getBookPages(book.getTitle());
                book.setPages(bookPages);
            }
            user.setBooks(userBooks);
            map.put(username, user);


        }

    }

    @Override
    public void save(User user) {
        users.put(user.getName(), user);
        this.save();
    }

    @Override
    public User get(String username) {
        return users.get(username);
    }

    @Override
    public boolean existsByName(String identifier) {
        return accounts.containsKey(identifier);
    }

    private void save() {
        Map<String, User> tempMap = new HashMap<>();
        loadData(tempMap);

        for (String username : users.keySet()) {
            User user = users.get(username);
            userDAO.save(user);
            bookDAO.saveBooks(user.getBooks(), username);
            for (Book book : user.getBooks()) {
                pageDAO.savePages(book.getPages(), book.title())
            }
        }

    }






}

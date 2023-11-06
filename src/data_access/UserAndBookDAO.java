package data_access;

import entity.User;
import entity.UserFactory;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class UserAndBook {
    private SqlUserDataAccessObject userDAO;
    private SqlBookDataAccessObject bookDAO;
    private final Map<String, User> users = new HashMap<>();
    private final Map<String, Book> books = new HashMap<>();
    private UserFactory userFactory;
    private BookFactory bookFactory;

    public UserAndBook(SqlUserDataAccessObject userDAO, SqlBookDataAccessObject bookDAO, UserFactory userFactory, BookFactory bookFactory) {
        this.userDAO = userDAO;
        this.bookDAO = bookDAO;
        this.bookFactory = bookFactory;
        this.userFactory = userFactory;
        loadData(users);
    }

    public void loadData(Map<String, User> map) {
        Map<String, User> tempUsers = userDAO.loadAll();
        Map<String, Book> tempBooks = bookDAO.loadAll();

        for (String username : tempUsers.keySet()) {
            User user = tempUsers.get(username);
            ArrayList<Book> userBooks = bookDAO.getUserBooks(username);
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
            if (tempMap.containsKey(username)) {
                userDAO.updateUser(user.getPassword());
                bookDAO.addBooks(user.getBooks(), username);

            } else {
                userDAO.insertUser(user);
                bookDAO.addBooks(user.getBooks(), username);


            }
        }

    }






}

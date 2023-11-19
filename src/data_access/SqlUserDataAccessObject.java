package data_access;
import java.sql.*;
import entity.User;
import entity.UserFactory;
import use_case.login.LoginUserDataAccessInterface;
import use_case.signup.SignupUserDataAccessInterface;
import entity.User;
import java.io.*;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

public class SqlUserDataAccessObject implements SignupUserDataAccessInterface, LoginUserDataAccessInterface {

    private final Connection c;
    private final SqlBookDataAccessObject bookDAO;

    private final Map<String, Integer> headers = new LinkedHashMap<>();

    private final Map<String, User> accounts = new HashMap<>();

    private UserFactory userFactory;

    public SqlUserDataAccessObject(SQLiteJDBC connector, UserFactory userFactory) throws IOException {
        this.userFactory = userFactory;
        this.c = connector.getConnection();
        connector.createUserTable();

        loadData(accounts);

    }

    public void loadData(Map<String, User> map) {
        String sql = "SELECT userName, password FROM USER";

        try (Statement stmt = c.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                String userName = rs.getString("userName");
                String password = rs.getString("password");
                User user = userFactory.create(userName, password);
                map.put(userName, user);
            }
        } catch (SQLException e) {
            System.err.println(e.getClass().getUserName() + ": " + e.getMessage());
        }

    }

    public Map<String, User> loadALl() {
        return accounts;

    }

    @Override
    public void saveUser(User user) {
        accounts.put(user.geUserName(), user);
        this.saveUser();
    }

    @Override
    public User get(String userName) {
        return accounts.get(userName);
    }

    private void saveUser() {
        Map<String, User> tempMap = new HashMap<>();
        loadData(tempMap);

        for (String name : accounts.keySet()) {
            User user = accounts.get(name);
            if (tempMap.containsKey(name)) {
                String sql = "UPDATE USER SET password = ? WHERE userName = ?";
                try (PreparedStatement pstmt = c.prepareStatement(sql)) {
                    pstmt.setString(1, user.getPassword()); // Set the password parameter
                    pstmt.setString(2, userName); // Set the userna,e parameter
                    pstmt.executeUpdate(); // Execute the insert statement
                } catch (SQLException e) {
                    System.err.println(e.getMessage());
                }
            } else {
                String sql = "INSERT INTO USER (userName, password) VALUES (?, ?)";
                try (PreparedStatement pstmt = c.prepareStatement(sql)) {
                    pstmt.setString(1, userName); // Set the username parameter
                    pstmt.setString(2, user.getPassword()); // Set the password parameter
                    pstmt.executeUpdate(); // Execute the insert statement

                } catch (SQLException e) {
                    System.err.println(e.getMessage());
                }

            }
        }

    }


    /**
     * Return whether a user exists with username identifier.
     * @param identifier the username to check.
     * @return whether a user exists with username identifier
     */
    @Override
    public boolean existsByName(String identifier) {
        return accounts.containsKey(identifier);
    }


}

package data_access;
import java.sql.*;
import entity.User;
import entity.UserFactory;
import use_case.login.LoginUserDataAccessInterface;
import use_case.signup.SignupUserDataAccessInterface;

import java.io.*;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

public class SqlUserDataAccessObject implements SignupUserDataAccessInterface, LoginUserDataAccessInterface {

    private final Connection c;

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
        String sql = "SELECT USERNAME, PASSWORD FROM USER";

        try (Statement stmt = c.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                String username = rs.getString("USERNAME");
                String password = rs.getString("PASSWORD");
                User user = userFactory.create(username, password);
                map.put(username, user);
            }
        } catch (SQLException e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
        }

    }

    @Override
    public void save(User user) {
        accounts.put(user.getName(), user);
        this.save();
    }

    @Override
    public User get(String username) {
        return accounts.get(username);
    }

    private void save() {
        Map<String, User> tempMap = new HashMap<>();
        loadData(tempMap);

        for (String username : accounts.keySet()) {
            User user = accounts.get(username);
            if (tempMap.containsKey(username)) {
                String sql = "UPDATE USER SET PASSWORD = ? WHERE USERNAME = ?";
                try (PreparedStatement pstmt = c.prepareStatement(sql)) {
                    pstmt.setString(1, user.getPassword()); // Set the password parameter
                    pstmt.executeUpdate(); // Execute the insert statement
                } catch (SQLException e) {
                    System.err.println(e.getMessage());
                }
            } else {
                String sql = "INSERT INTO USER (USERNAME, PASSWORD) VALUES (?, ?)";
                try (PreparedStatement pstmt = c.prepareStatement(sql)) {
                    pstmt.setString(1, username); // Set the username parameter
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

package data_access;
import java.sql.*;
import entity.User;
import entity.UserFactory;

import java.io.*;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * The SqlUserDataAccessObject class is responsible for handling all database operations related to User entities.
 * It implements both the SignupUserDataAccessInterface and LoginUserDataAccessInterface, enabling functionalities
 * for user-related operations in the context of signup and login processes.
 *
 * This class manages a collection of User objects, offering methods to load, retrieve, and save user data. It utilizes
 * a SQL database connection through a provided SQLiteJDBC connector and a UserFactory for creating User instances.
 *
 * Key functionalities include loading all user data from the database, saving individual users or updating their information,
 * and internally managing a collection of users for quick access. The class ensures efficient and consistent management
 * of user data, aligning with the system's authentication and user management requirements.
 */
public class SqlUserDataAccessObject {

    private final Connection c;


    private final Map<String, Integer> headers = new LinkedHashMap<>();

    private final Map<String, User> accounts = new HashMap<>();

    private UserFactory userFactory;


    /**
     * Constructs a new SqlUserDataAccessObject with a given SQLiteJDBC connector and UserFactory.
     * @param connector the SQLiteJDBC connector to establish a connection with the database.
     * @param userFactory the factory to create User objects.
     * @throws IOException if an I/O error occurs.
     */
    public SqlUserDataAccessObject(SQLiteJDBC connector, UserFactory userFactory) throws IOException {
        this.userFactory = userFactory;
        this.c = connector.getConnection();
        connector.createUserTable();

        loadData(accounts);

    }

    private void loadData(Map<String, User> map) {
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
            System.err.println(e.getClass() + ": " + e.getMessage());
        }

    }

    /**
     * Loads all user data from the database.
     * @return a Map containing username as key and User object as value.
     */
    public Map<String, User> loadAll() {
        return accounts;

    }

     /**
     * Saves a user to the database. If the user already exists, updates their information.
     * @param user the User object to be saved or updated.
     */
    public void saveUser(User user) {
        accounts.put(user.getUserName(), user);
        this.saveUser();
    }




    private void saveUser() {
        Map<String, User> tempMap = new HashMap<>();
        loadData(tempMap);

        for (String userName : accounts.keySet()) {
            User user = accounts.get(userName);
            if (tempMap.containsKey(userName)) {
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
}

package data_access;
import java.sql.*;

import data_access.SQLiteJDBC;
import entity.User;
import entity.UserFactory;
import use_case.login.LoginUserDataAccessInterface;
import use_case.signup.SignupUserDataAccessInterface;

import java.io.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

public class SqlBookDataAccessObject implements SignupUserDataAccessInterface, LoginUserDataAccessInterface {

    private final Connection c;

    private final Map<String, Integer> headers = new LinkedHashMap<>();

    private final Map<String, Book> books = new HashMap<>();

    private BookFactory bookFactory;

    public SqlUserDataAccessObject(SQLiteJDBC connector, BookFactory bookFactory) throws IOException {
        this.bookFactory = bookFactory;
        this.c = connector.getConnection();
        connector.createBookTable();

        loadData(books);

    }

    public void loadData(Map<String, User> map) {
        String sql = "SELECT USERNAME, PASSWORD FROM BOOK";

        try (Statement stmt = c.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                String title = rs.getString("TITLE");
                String storyText = rs.getString("StoryText");
                Book book = bookFactory.create(title, storyText);
                map.put(title, book);
            }
        } catch (SQLException e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
        }

    }

    @Override
    public void save(Book book) {
        books.put(book.getTitle(), book);
        this.save();
    }

    @Override
    public Book get(String title) {
        return books.get(title);
    }

    private void save() {
        Map<String, Book> tempMap = new HashMap<>();
        loadData(tempMap);

        for (String title : books.keySet()) {
            Book book = books.get(title);
            if (tempMap.containsKey(title)) {
                String sql = "UPDATE BOOK SET StoryText = ? WHERE TITLE = ?";
                try (PreparedStatement pstmt = c.prepareStatement(sql)) {
                    pstmt.setString(1, book.getStoryText()); // Set the text parameter
                    pstmt.setString(2, title); // Set the title parameter
                    pstmt.executeUpdate(); // Execute the insert statement
                } catch (SQLException e) {
                    System.err.println(e.getMessage());
                }
            } else {
                String sql = "INSERT INTO BOOK (TITLE, StoryText) VALUES (?, ?)";
                try (PreparedStatement pstmt = c.prepareStatement(sql)) {
                    pstmt.setString(1, title); // Set the username parameter
                    pstmt.setString(2, book.getStoryText()); // Set the password parameter
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
        return books.containsKey(identifier);
    }

    public String getBookUser(String identifier) {
        String username = "";
        String sql = "SELECT userId FROM Book WHERE title = ?";
        try (PreparedStatement pstmt = c.prepareStatement(sql)) {
            pstmt.setString(1, identifier); // Set the password parameter
            ResultSet rs = pstmt.executeQuery();
            username = rs.getString("username");


            pstmt.executeUpdate(); // Execute the insert statement
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
        return username;
    }




    public void addBooks(ArrayList<Book> books, String username) {

        for (String title : books.keySet()) {

            String curName = getBookUser(title);

            String sql = "UPDATE BOOK SET UserID = ? WHERE TTILE = ?";
            try (PreparedStatement pstmt = c.prepareStatement(sql)) {
                pstmt.setString(1, curName); // Set the password parameter
                pstmt.setString(2, title); // Set the userna,e parameter
                pstmt.executeUpdate(); // Execute the insert statement
            } catch (SQLException e) {
                System.err.println(e.getMessage());
            }

        }

    }


}

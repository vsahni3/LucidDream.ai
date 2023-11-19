package data_access;
import java.sql.*;

import entity.StoryBook;
import entity.User;

import use_case.login.LoginUserDataAccessInterface;
import use_case.signup.SignupUserDataAccessInterface;

import java.io.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

public class SqlBookDataAccessObject implements SignupUserDataAccessInterface, LoginUserDataAccessInterface {

    private final Connection c;

    private final Map<String, Integer> headers = new LinkedHashMap<>();

    private Map<String, StoryBook> storyBooks = new HashMap<>();

    private StoryBookFactory bookFactory;

    public SqlUserDataAccessObject(SQLiteJDBC connector, StoryBookFactory storyBookFactory) throws IOException {
        this.storyBookFactory = storyBookFactory;
        this.c = connector.getConnection();
        connector.createBookTable();

        loadData(storyBooks);

    }

    public void loadData(Map<String, StoryBook> map) {
        String sql = "SELECT title FROM BOOK";

        try (Statement stmt = c.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                String title = rs.getString("title");

                StoryBook storyBook = storyBookFactory.create(title);
                map.put(title, storyBook);
            }
        } catch (SQLException e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
        }

    }






    /**
     * Return whether a user exists with username identifier.
     * @param identifier the username to check.
     * @return whether a user exists with username identifier
     */

    public ArrayList<StoryBook> getUserBooks(String userName) {
        ArrayList<StoryBook> books = new ArrayList<>();
        String sql = "SELECT title FROM Book WHERE userName = ?";
        try (PreparedStatement pstmt = c.prepareStatement(sql)) {
            pstmt.setString(1, userName); // Set the password parameter
            ResultSet rs = pstmt.executeQuery();

            // Iterate through the result set to get all titles
            while (rs.next()) {
                String title = rs.getString("title");
                // Now you have the title, and you can do something with it,
                books.add(storyBooks.get(title));
            }



        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
        return books;
    }


    public String getBookUser(String identifier) {
        String userName = "";
        String sql = "SELECT userId FROM Book WHERE title = ?";
        try (PreparedStatement pstmt = c.prepareStatement(sql)) {
            pstmt.setString(1, identifier); // Set the password parameter
            ResultSet rs = pstmt.executeQuery();
            userName = rs.getString("userName");


            pstmt.executeUpdate(); // Execute the insert statement
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
        return userName;
    }

    public void saveStoryBook(StoryBook storyBook, String userName, Map<String, StoryBook> tempMap) {
        if (tempMap.containsKey(userName)) {
            String sql = "UPDATE BOOK SET userID = ? WHERE title = ?";
            try (PreparedStatement pstmt = c.prepareStatement(sql)) {
                pstmt.setString(1, userName); // Set the password parameter
                 // Set the userna,e parameter
                pstmt.setString(2, storyBook.getTitle());
                pstmt.executeUpdate(); // Execute the insert statement
            } catch (SQLException e) {
                System.err.println(e.getMessage());
            }
        } else {
            String sql = "INSERT INTO USER (title, userID) VALUES (?, ?)";
            try (PreparedStatement pstmt = c.prepareStatement(sql)) {
                pstmt.setString(1, storyBook.getTitle()); // Set the username parameter
                // Set the password parameter
                pstmt.setString(2, userName);
                pstmt.executeUpdate(); // Execute the insert statement

            } catch (SQLException e) {
                System.err.println(e.getMessage());
            }

        }
    }





    public void saveStoryBooks(ArrayList<StoryBook> books, String userName) {
        Map<String, StoryBook> tempMap = new HashMap<>();
        loadData(tempMap);
        for (StoryBook storyBook : books) {

            saveStoryBook(storyBook, userName, tempMap);
        }
        this.storyBooks = new HashMap<String, StoryBook>();
        loadData(this.storyBooks);


    }

    }


}

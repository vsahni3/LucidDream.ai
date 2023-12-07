package data_access;
import java.sql.*;

import entity.Page;
import entity.StoryBook;
import entity.StoryBookFactory;

import java.io.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * The SqlBookDataAccessObject class is responsible for handling all database operations related to StoryBook entities.
 * It implements the SignupUserDataAccessInterface and LoginUserDataAccessInterface, providing specific functionalities
 * for book-related operations in the context of user signup and login.
 *
 * This class manages a collection of StoryBook objects, offering methods to load, retrieve, and save book data. It
 * interacts with a SQL database through a provided SQLiteJDBC connector and utilizes a StoryBookFactory for creating
 * StoryBook instances.
 *
 * Key functionalities include retrieving books associated with a specific user, getting the user associated with a book,
 * and saving individual books or a collection of books in the database. The class ensures proper management and persistence
 * of book data, aligning with the requirements of the system's business logic.
 */
public class SqlBookDataAccessObject {


    private final Map<String, Integer> headers = new LinkedHashMap<>();

    private Map<String, StoryBook> storyBooks = new HashMap<>();

    private StoryBookFactory storyBookFactory;


    /**
     * Constructs a new SqlBookDataAccessObject with a given SQLiteJDBC connector and StoryBookFactory.
     * @param storyBookFactory the factory to create StoryBook objects.
     * @throws IOException if an I/O error occurs.
     */
    public SqlBookDataAccessObject(StoryBookFactory storyBookFactory) throws IOException {
        this.storyBookFactory = storyBookFactory;
        SQLiteJDBC connector = new SQLiteJDBC("jdbc:sqlite:dream.db");
        Connection c = connector.getConnection();


        loadData(c, storyBooks);
        try {
            c.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    private void loadData(Connection c, Map<String, StoryBook> map) {
        String sql = "SELECT title FROM BOOK";

        try (Statement stmt = c.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                String title = rs.getString("title");

                StoryBook storyBook = storyBookFactory.create(title, new ArrayList<Page>());
                map.put(title, storyBook);
            }
        } catch (SQLException e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
        }

    }
    /**
     * Delete all pages
     */
    public void deleteAll(Connection c) {
        String sql = "DELETE FROM BOOK";
        try (PreparedStatement pstmt = c.prepareStatement(sql)) {

            pstmt.executeUpdate(); // Execute the insert statement
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
        this.storyBooks.clear();

    }

    /**
     * Retrieves a list of StoryBook objects associated with a given user.
     * @param userName the username for which the books are to be retrieved.
     * @return an ArrayList of StoryBook objects belonging to the specified user.
     */
    public ArrayList<StoryBook> getUserBooks(Connection c, String userName) {
        ArrayList<StoryBook> books = new ArrayList<>();
        String sql = "SELECT title FROM Book WHERE userId = ?";
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


    /**
     * Saves a story book in the database with the given user's name and updates the provided temporary map.
     * @param storyBook the StoryBook object to be saved.
     * @param userName the username to associate with the story book.
     * @param tempMap a temporary map to be updated with the new story book data.
     */
    public void saveStoryBook(Connection c, StoryBook storyBook, String userName, Map<String, StoryBook> tempMap) {
        if (tempMap.containsKey(storyBook.getTitle())) {
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

            String sql = "INSERT INTO BOOK (title, userID) VALUES (?, ?)";
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

    /**
     * Saves a list of StoryBook objects in the database associated with a given user.
     * @param books the ArrayList of StoryBook objects to be saved.
     * @param userName the username to associate with the story books.
     */
    public void saveStoryBooks(Connection c, ArrayList<StoryBook> books, String userName) {

        for (StoryBook storyBook : books) {

            saveStoryBook(c, storyBook, userName, this.storyBooks);
        }
        this.storyBooks = new HashMap<String, StoryBook>();
        loadData(c, this.storyBooks);


    }
    /**
     * Check the existence of a book
     * @param identifier the title of the book
     * @return whether the book exists
     */
    public boolean existsBook(String identifier) {
        return this.storyBooks.containsKey(identifier);
    }
}



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

    private final Map<String, Page> pages = new HashMap<>();

    private PageFactory pageFactory;

    public SqlUserDataAccessObject(SQLiteJDBC connector, PageFactory pageFactory) throws IOException {
        this.pageFactory = pageFactory;
        this.c = connector.getConnection();
        connector.createBookTable();

        loadData(pages);

    }

    public void loadData(Map<String, User> map) {
        String sql = "SELECT TextContent, PageNumber FROM BOOK";

        try (Statement stmt = c.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                Integer id = rs.getInt("ID");
                String content = rs.getString("TextContent");
                String pageNumber = rs.getString("PageNumber");
                Page page = pageFactory.create(id, content, pageNumber);
                map.put(id, page);
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
        Map<String, Page> tempMap = new HashMap<>();
        loadData(tempMap);

        for (Integer id : pages.keySet()) {
            Page page = books.get(id);
            if (tempMap.containsKey(id)) {
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

    public String getPageBook(Integer id) {
        String title = "";
        String sql = "SELECT BookID FROM Book WHERE title = ?";
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

    public void insertBook(Page page, String title) {

        String sql = "INSERT INTO PAGE (TextContent, PageNumber, BookID) VALUES (?, ?, ?)";
        try (PreparedStatement pstmt = c.prepareStatement(sql)) {
            pstmt.setString(1, page.getContent()); // Set the username parameter
            pstmt.setString(2, page.getPageNumber()); // Set the password parameter
            pstmt.setString(3, title);
            pstmt.executeUpdate(); // Execute the insert statement

        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }

    }

    public int savePages(ArrayList<Page> pages, String title) {
        Map<String, Page> tempMap = new HashMap<>();
        loadData(tempMap);
        for (Page page : pages) {

            if (tempMap.containsKey(title)) {
                String sql = "UPDATE PAGE SET TextContent = ?, PageNumber = ?, BookID = ? WHERE ID = ?";
                try (PreparedStatement pstmt = c.prepareStatement(sql)) {
                    pstmt.setString(1, page.getTextContent()); // Set the password parameter
                    pstmt.setString(2, page.getPageNumber()); // Set the userna,e parameter
                    pstmt.setString(3, title);
                    pstmt.setString(3, book.getID());
                    pstmt.executeUpdate(); // Execute the insert statement
                    return -1;
                } catch (SQLException e) {
                    System.err.println(e.getMessage());
                }
            } else {
                String sql = "INSERT INTO USER (TextContent, PageNumber, BookID) VALUES (?, ?, ?)";
                try (PreparedStatement pstmt = c.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
                    pstmt.setString(1, page.getTextContent()); // Set the username parameter
                    pstmt.setString(2, page.getTextContent()); // Set the password parameter
                    pstmt.setString(3, title);
                    pstmt.executeUpdate(); // Execute the insert statement
                    ResultSet generatedKeys = pstmt.getGeneratedKeys();
                    int generatedId = generatedKeys.getInt(1);
                    return generatedId;

                } catch (SQLException e) {
                    System.err.println(e.getMessage());
                }

            }
        }


    }


}

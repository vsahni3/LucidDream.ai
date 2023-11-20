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

    private final Map<Integer, Page> pages = new HashMap<>();

    private PageFactory pageFactory;

    public SqlPageDataAccessObject(SQLiteJDBC connector, PageFactory pageFactory) throws IOException {
        this.pageFactory = pageFactory;
        this.c = connector.getConnection();
        connector.createBookTable();

        loadData(pages);

    }

    public void loadData(Map<Integer, Page> map) {
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
    public boolean existsPage(String identifier) {
        return pages.containsKey(identifier);
    }

    public void savePage(Page page, String title) {
        if (tempMap.containsKey(title)) {
            String sql = "UPDATE PAGE SET TextContent = ?, PageNumber = ?, BookID = ? WHERE ID = ?";
            try (PreparedStatement pstmt = c.prepareStatement(sql)) {
                pstmt.setString(1, page.getTextContent()); // Set the password parameter
                pstmt.setString(2, page.getPageNumber()); // Set the userna,e parameter
                pstmt.setString(3, title);
                pstmt.setString(3, book.getID());
                pstmt.executeUpdate(); // Execute the insert statement

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
                page.setId(generatedId);

            } catch (SQLException e) {
                System.err.println(e.getMessage());
            }

        }

    }


    public void savePages(ArrayList<Page> pages, String title) {
        Map<String, Page> tempMap = new HashMap<>();

        loadData(tempMap);
        for (Page page : pages) {
            savePage(page);


        }
        this.pages = new HashMap<Integer, Page>();
        loadData(this.pages);



    }


}

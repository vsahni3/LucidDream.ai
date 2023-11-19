package data_access;
import java.sql.*;

import entity.PageFactory;
import entity.StoryBook;
import use_case.login.LoginUserDataAccessInterface;
import use_case.signup.SignupUserDataAccessInterface;
import entity.Page;
import java.io.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


public class SqlPageDataAccessObject implements SignupUserDataAccessInterface, LoginUserDataAccessInterface {

    private final Connection c;

    private final Map<Integer, Page> pages = new HashMap<>();

    private PageFactory pageFactory;

    public SqlPageDataAccessObject(SQLiteJDBC connector, PageFactory pageFactory) throws IOException {
        this.pageFactory = pageFactory;
        this.c = connector.getConnection();
        connector.createBookTable();

        loadData(pages);

    }

    public void loadData(Map<Integer, Page> map) {
        String sql = "SELECT pageContents, pageNumber FROM BOOK";

        try (Statement stmt = c.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                Integer id = rs.getInt("ID");
                String pageContents = rs.getString("pageContents");
                String pageNumber = rs.getString("pageNumber");
                Page page = pageFactory.create(id, pageContents, pageNumber);
                map.put(id, page);
            }
        } catch (SQLException e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
        }

    }

    public ArrayList<Page> getBookPages(String title) {
        ArrayList<Page> curPages = new ArrayList<>();
        String sql = "SELECT pageID FROM Page WHERE title = ?";
        try (PreparedStatement pstmt = c.prepareStatement(sql)) {
            pstmt.setString(1, title); // Set the password parameter
            ResultSet rs = pstmt.executeQuery();

            // Iterate through the result set to get all titles
            while (rs.next()) {
                String pageID = rs.getString("pageID");
                // Now you have the title, and you can do something with it,
                curPages.add(pages.get(title));
            }



        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
        return curPages;
    }



    /**
     * Return whether a user exists with username identifier.
     * @param identifier the username to check.
     * @return whether a user exists with username identifier
     */

    @Override
    public boolean existsPage(Integer identifier) {
        return pages.containsKey(identifier);
    }

    public void savePage(Page page, String title, Map<String, Page> tempMap) {
        if (tempMap.containsKey(title)) {
            String sql = "UPDATE PAGE SET pageContents = ?, pageNumber = ?, image = ?, bookID = ? WHERE ID = ?";
            try (PreparedStatement pstmt = c.prepareStatement(sql)) {
                pstmt.setString(1, page.getPageContents()); // Set the password parameter
                pstmt.setString(2, page.getPageNumber()); // Set the userna,e parameter
                pstmt.setString(3, page.getImage());
                pstmt.setString(4, title);
                pstmt.setString(5, book.getID());
                pstmt.executeUpdate(); // Execute the insert statement

            } catch (SQLException e) {
                System.err.println(e.getMessage());
            }
        } else {
            String sql = "INSERT INTO USER (pageContents, pageNumber, image, bookID) VALUES (?, ?, ?, ?)";
            try (PreparedStatement pstmt = c.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
                pstmt.setString(1, page.getPageContents()); // Set the username parameter
                pstmt.setString(2, page.getPageNumber()); // Set the password parameter
                pstmt.setString(3, page.getImage());
                pstmt.setString(4, title);
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
            savePage(page, title, tempMap);


        }
        this.pages = new HashMap<Integer, Page>();
        loadData(this.pages);



    }


}

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

/**
 * The SqlPageDataAccessObject class is designed for handling all database operations related to Page entities.
 * It implements the SignupUserDataAccessInterface and LoginUserDataAccessInterface, providing specific functionalities
 * for page-related operations during user signup and login processes.
 *
 * This class manages a collection of Page objects, offering methods to load, retrieve, and save page data. It uses
 * a SQL database connection provided by a SQLiteJDBC connector and a PageFactory for creating Page instances.
 *
 * The key functionalities include retrieving pages associated with a specific book title, and saving individual pages
 * or a collection of pages in the database. It ensures efficient and effective management of page data in alignment
 * with the system's requirements for content management and presentation.
 */
public class SqlPageDataAccessObject {


    private Map<Integer, Page> pages = new HashMap<>();

    private PageFactory pageFactory;


    /**
     * Constructs a new SqlPageDataAccessObject with a given SQLiteJDBC connector and PageFactory.

     * @param pageFactory the factory to create Page objects.
     * @throws IOException if an I/O error occurs.
     */
    public SqlPageDataAccessObject(PageFactory pageFactory) throws IOException {
        this.pageFactory = pageFactory;
        SQLiteJDBC connector = new SQLiteJDBC("jdbc:sqlite:dream.db");
        Connection c = connector.getConnection();


        loadData(c, pages);
        try {
            c.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    private void loadData(Connection c, Map<Integer, Page> map) {
        String sql = "SELECT pageContents, pageNumber, image, pageID FROM PAGE";

        try (Statement stmt = c.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {

                Integer id = rs.getInt("pageID");
                String pageContents = rs.getString("pageContents");
                Integer pageNumber = rs.getInt("pageNumber");
                byte[] image = rs.getBytes("image");

                Page page = pageFactory.create(pageContents, pageNumber, image, id);


                map.put(id, page);
            }
        } catch (SQLException e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
        }

    }

    /**
     * Retrieves a list of Page objects associated with a given book title.
     * @param title the title of the book for which the pages are to be retrieved.
     * @return an ArrayList of Page objects for the specified book.
     */
    public ArrayList<Page> getBookPages(Connection c, String title) {
        ArrayList<Page> curPages = new ArrayList<>();
        String sql = "SELECT pageID FROM Page WHERE bookID = ?";
        try (PreparedStatement pstmt = c.prepareStatement(sql)) {
            pstmt.setString(1, title); // Set the password parameter
            ResultSet rs = pstmt.executeQuery();

            // Iterate through the result set to get all titles
            while (rs.next()) {
                Integer pageID = rs.getInt("pageID");
                // Now you have the title, and you can do something with it,
                curPages.add(pages.get(pageID));
            }



        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
        return curPages;
    }


    /**
     * Saves a Page object to the database associated with a given book title and updates the provided temporary map.
     * @param page the Page object to be saved or updated.
     * @param title the title of the book to which the page belongs.
     * @param tempMap a temporary map to be updated with the new page data.
     */
    public void savePage(Connection c, Page page, String title, Map<Integer, Page> tempMap) {

        if (tempMap.containsKey(page.getPageID())) {
            String sql = "UPDATE PAGE SET pageContents = ?, pageNumber = ?, image = ?, bookID = ? WHERE pageID = ?";
            try (PreparedStatement pstmt = c.prepareStatement(sql)) {
                pstmt.setString(1, page.getTextContents());
                pstmt.setInt(2, page.getPageNumber());
                pstmt.setBytes(3, page.getImage());
                pstmt.setString(4, title);
                pstmt.setInt(5, page.getPageID());
                pstmt.executeUpdate(); // Execute the insert statement

            } catch (SQLException e) {
                System.err.println(e.getMessage());
            }
        } else {

            String sql = "INSERT INTO PAGE (pageContents, pageNumber, image, bookID) VALUES (?, ?, ?, ?)";
            try (PreparedStatement pstmt = c.prepareStatement(sql)) {
                pstmt.setString(1, page.getTextContents()); // Set the username parameter
                pstmt.setInt(2, page.getPageNumber()); // Set the password parameter
                pstmt.setBytes(3, page.getImage());
                pstmt.setString(4, title);
                pstmt.executeUpdate(); // Execute the insert statement
                try (Statement stmt = c.createStatement()) {
                    ResultSet rs = stmt.executeQuery("SELECT last_insert_rowid()");
                    if (rs.next()) {
                        int generatedId = rs.getInt(1);

                        page.setPageID(generatedId);
                    }
                }
            } catch (SQLException e) {
                System.err.println(e.getMessage());
            }

        }

    }

    public void deleteAll(Connection c) {
        String sql = "DELETE FROM PAGE";
        try (PreparedStatement pstmt = c.prepareStatement(sql)) {

            pstmt.executeUpdate(); // Execute the insert statement
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
        this.pages.clear();

    }

    /**
     * Saves a list of Page objects in the database associated with a given book title.
     * @param pages the ArrayList of Page objects to be saved.
     * @param title the title of the book to which these pages belong.
     */
    public void savePages(Connection c, ArrayList<Page> pages, String title) {


        for (Page page : pages) {
            savePage(c, page, title, this.pages);


        }
        this.pages = new HashMap<Integer, Page>();
        loadData(c, this.pages);
;




    }
    public boolean existsPage(Integer identifier) {
        return this.pages.containsKey(identifier);
    }


}

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
public class SqlPageDataAccessObject implements SignupUserDataAccessInterface, LoginUserDataAccessInterface {

    private final Connection c;

    private final Map<Integer, Page> pages = new HashMap<>();

    private PageFactory pageFactory;


    /**
     * Constructs a new SqlPageDataAccessObject with a given SQLiteJDBC connector and PageFactory.
     * @param connector the SQLiteJDBC connector to establish a connection with the database.
     * @param pageFactory the factory to create Page objects.
     * @throws IOException if an I/O error occurs.
     */
    public SqlPageDataAccessObject(SQLiteJDBC connector, PageFactory pageFactory) throws IOException {
        this.pageFactory = pageFactory;
        this.c = connector.getConnection();
        connector.createBookTable();

        loadData(pages);

    }

    private void loadData(Map<Integer, Page> map) {
        String sql = "SELECT pageContents, pageNumber, image FROM PAGE";

        try (Statement stmt = c.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                Integer id = rs.getInt("ID");
                String pageContents = rs.getString("pageContents");
                String pageNumber = rs.getString("pageNumber");
                Blob imageBlob = rs.getBlob("image");
                byte[] image = imageBlob.getBytes(1, (int) imageBlob.length());
                imageBlob.free(); // Free the blob resource
                Page page = pageFactory.create(id, pageContents, pageNumber, image);
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
     * Saves a Page object to the database associated with a given book title and updates the provided temporary map.
     * @param page the Page object to be saved or updated.
     * @param title the title of the book to which the page belongs.
     * @param tempMap a temporary map to be updated with the new page data.
     */
    public void savePage(Page page, String title, Map<String, Page> tempMap) {
        if (tempMap.containsKey(title)) {
            String sql = "UPDATE PAGE SET pageContents = ?, pageNumber = ?, image = ?, bookID = ? WHERE ID = ?";
            try (PreparedStatement pstmt = c.prepareStatement(sql)) {
                pstmt.setString(1, page.getPageContents()); // Set the password parameter
                pstmt.setString(2, page.getPageNumber()); // Set the userna,e parameter
                pstmt.setBytes(3, page.getImage());
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
                pstmt.setBytes(3, page.getImage());
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

    /**
     * Saves a list of Page objects in the database associated with a given book title.
     * @param pages the ArrayList of Page objects to be saved.
     * @param title the title of the book to which these pages belong.
     */
    public void savePages(ArrayList<Page> pages, String title) {
        Map<String, Page> tempMap = new HashMap<>();

        loadData(tempMap);
        for (Page page : pages) {
            savePage(page, title, tempMap);


        }
        this.pages = new HashMap<Integer, Page>();
        loadData(this.pages);



    }
    public boolean existsPage(Integer identifier) {
        return this.pages.containsKey(identifier);
    }


}

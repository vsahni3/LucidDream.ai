package data_access;
import entity.Page;
import entity.User;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import entity.StoryBook;
import use_case.downloadPDF.DownloadPDFDataAccessInterface;
import use_case.generate.GenerateUserDataAccessInterface;
import use_case.login.LoginUserDataAccessInterface;
import use_case.signup.SignupUserDataAccessInterface;
import use_case.view_stories.ViewStoriesDataAccessInterface;

import java.util.ArrayList;

/**
 * CombinedDAO is a data access object class that integrates the operations of user, book, and page data.
 * It utilizes individual data access objects (DAOs) for users, books, and pages to perform complex
 * operations that involve multiple entities. This class serves as a unified interface to handle
 * user-related, book-related, and page-related data transactions in a cohesive manner.
 *
 * It acts as an aggregator of SqlUserDataAccessObject, SqlBookDataAccessObject, and SqlPageDataAccessObject,
 * facilitating operations that span across these entities, such as loading and saving users along with their
 * associated books and pages. The class ensures that the data integrity is maintained across these different
 * entities in the database.
 *
 * Usage of this class simplifies interactions with the database for operations involving complex relationships
 * between users, books, and pages.
 */
public class CombinedDAO implements GenerateUserDataAccessInterface, LoginUserDataAccessInterface, SignupUserDataAccessInterface, ViewStoriesDataAccessInterface, DownloadPDFDataAccessInterface {
    private SqlUserDataAccessObject userDAO;
    private SqlBookDataAccessObject bookDAO;
    private SqlPageDataAccessObject pageDAO;
    private final Map<String, User> users = new HashMap<>();

    /**
     * Constructs a new CombinedDAO with given data access objects for users, books, and pages.
     * @param userDAO the data access object for user-related operations.
     * @param bookDAO the data access object for book-related operations.
     * @param pageDAO the data access object for page-related operations.
     */
    public CombinedDAO(SqlUserDataAccessObject userDAO, SqlBookDataAccessObject bookDAO, SqlPageDataAccessObject pageDAO) {
        this.userDAO = userDAO;
        this.pageDAO = pageDAO;
        this.bookDAO = bookDAO;

        loadData(users);

    }
    /**
     * Give a map of all the user entities.
     * @return all users
     */
    public Map<String, User> getAll(){
        return users;
    }

    private Connection createConnection(){
        SQLiteJDBC connector = new SQLiteJDBC("jdbc:sqlite:dream.db");
        return connector.getConnection();
    }

    private void closeConnection(Connection c){
        try {
            c.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    private void loadData(Map<String, User> map) {
        Connection c = createConnection();

        Map<String, User> tempUsers = userDAO.loadAll();



        for (String userName : tempUsers.keySet()) {
            User user = tempUsers.get(userName);
            ArrayList<StoryBook> userBooks = bookDAO.getUserBooks(c, userName);
            for (StoryBook book : userBooks) {
                ArrayList<Page> bookPages = pageDAO.getBookPages(c, book.getTitle());
                book.setPages(bookPages);
            }
            user.setStoryBooks(userBooks);
            map.put(userName, user);


        }
        closeConnection(c);


    }

    /**
     * Saves a user and their associated books and pages to the database.
     * @param user the User object to be saved.
     */
    @Override
    public void save(User user) {
        Connection c = createConnection();
        this.users.put(user.getUserName(), user);

        this.save(c);
    }

    /**
     * Retrieves a User object based on a given username.
     * @param userName the username to search for.
     * @return the User object associated with the given username.
     */
    @Override
    public User getUser(String userName) {
        return users.get(userName);
    }


    /**
     * Retrieves a StoryBook object based on a given book title.
     * @param title the title of the book to search for.
     * @return the StoryBook object associated with the given title.
     */
    @Override
    public StoryBook getBook(String title) {
        StoryBook chosenBook = null;
        for (String userName : users.keySet()) {
            User user = users.get(userName);
            for (StoryBook book : user.getStoryBooks()) {
                if (book.getTitle().equals(title)) {
                    chosenBook = book;
                }
            }
        }
        return chosenBook;

    }



    /**
     * Retrieves a Page object based on a given page ID.
     * @param id the ID of the page to search for.
     * @return the Page object associated with the given ID.
     */

    public Page getPage(Integer id) {
        Page chosenPage = null;

        // you could also implement a getPage for the pageDAO as the page doesn't have any extra info about other entities
        for (String userName : users.keySet()) {
            User user = users.get(userName);
            for (StoryBook book : user.getStoryBooks()) {
                for (Page page : book.getPages())
                    if (page.getPageID().equals(id)) {


                        chosenPage = page;

                    }
            }
        }
        return chosenPage;
    }

    /**
     * Checks whether a user exists in the database based on a username identifier.
     * @param identifier the username to check for existence.
     * @return true if the user exists, false otherwise.
     */
    @Override
    public boolean existsUser(String identifier) {
        return users.containsKey(identifier);
    }


    /**
     * Checks whether a book exists in the database based on a book identifier.
     * @param identifier the book title to check for existence.
     * @return true if the book exists, false otherwise.
     */

    public boolean existsBook(String identifier) {
        return bookDAO.existsBook(identifier);
    }


    /**
     * Checks whether a page exists in the database based on a page identifier.
     * @param identifier the page ID to check for existence.
     * @return true if the page exists, false otherwise.
     */
    public boolean existsPage(Integer identifier) {
        return pageDAO.existsPage(identifier);
    }
    /**
     * Delete all entities from all tables.
     */
    public void deleteAll() {
        Connection c = createConnection();
        userDAO.deleteAll(c);
        bookDAO.deleteAll(c);
        pageDAO.deleteAll(c);
        this.users.clear();
    }

    private void save(Connection c) {



        for (String username : users.keySet()) {
            User user = users.get(username);


            userDAO.saveUser(c, user);
            bookDAO.saveStoryBooks(c, user.getStoryBooks(), username);
            for (StoryBook book : user.getStoryBooks()) {

                pageDAO.savePages(c, book.getPages(), book.getTitle());
            }
        }
        closeConnection(c);

    }
    /**
     * Return the storybooks associated with a user
     * @param username the username of the user
     * @return the matching storybooks
     */
    @Override
    public ArrayList<StoryBook> getStoryBooks(String username) {
        Connection c = createConnection();
        ArrayList<StoryBook> books = users.get(username).getStoryBooks();
        closeConnection(c);
        return books;
    }
}

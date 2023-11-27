package data_access;
import entity.Page;
import entity.User;
import java.util.HashMap;
import java.util.Map;
import entity.StoryBook;
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
public class CombinedDAO implements CombinedDataAcessInterface {
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

    private void loadData(Map<String, User> map) {
        Map<String, User> tempUsers = userDAO.loadAll();


        for (String userName : tempUsers.keySet()) {
            User user = tempUsers.get(userName);
            ArrayList<StoryBook> userBooks = bookDAO.getUserBooks(userName);
            for (StoryBook book : userBooks) {
                ArrayList<Page> bookPages = pageDAO.getBookPages(book.getTitle());
                book.setPages(bookPages);
            }
            user.setStoryBooks(userBooks);
            map.put(userName, user);


        }

    }

    /**
     * Saves a user and their associated books and pages to the database.
     * @param user the User object to be saved.
     */
    @Override
    public void save(User user) {
        users.put(user.getUserName(), user);
        this.save();
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
        for (String userName : users.keySet()) {
            User user = users.get(userName);
            for (StoryBook book : user.getStoryBooks()) {
                if (book.getTitle().equals(title)) {
                    return book;
                }
            }
        }
    }



    /**
     * Retrieves a Page object based on a given page ID.
     * @param id the ID of the page to search for.
     * @return the Page object associated with the given ID.
     */
    @Override
    public Page getPage(Integer id) {

        // you could also implement a getPage for the pageDAO as the page doesn't have any extra info about other entities
        for (String userName : users.keySet()) {
            User user = users.get(userName);
            for (StoryBook book : user.getStoryBooks()) {
                for (Page page : book.getPages())
                    if (page.getPageID().equals(id)) {
                        return page;
                    }
            }
        }
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
    @Override
    public boolean existsBook(String identifier) {
        return bookDAO.existsBook(identifier);
    }


    /**
     * Checks whether a page exists in the database based on a page identifier.
     * @param identifier the page ID to check for existence.
     * @return true if the page exists, false otherwise.
     */
    @Override
    public boolean existsPage(Integer identifier) {
        return pageDAO.existsPage(identifier);
    }

    private void save() {
        Map<String, User> tempMap = new HashMap<>();
        loadData(tempMap);

        for (String username : users.keySet()) {
            User user = users.get(username);
            userDAO.saveUser(user);
            bookDAO.saveStoryBooks(user.getStoryBooks(), username);
            for (StoryBook book : user.getStoryBooks()) {
                pageDAO.savePages(book.getPages(), book.getTitle());
            }
        }

    }

}

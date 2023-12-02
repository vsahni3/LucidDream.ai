package data_access;

import entity.*;
import org.junit.Rule;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.rules.ExpectedException;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;


class CombinedDAOTest {
    @Rule
    public final ExpectedException exception = ExpectedException.none();

    private SQLiteJDBC connector;
    private CombinedDAO combinedDAO;
    UserFactory userFactory = new CommonUserFactory();
    StoryBookFactory storyBookFactory = new StoryBookFactory();
    PageFactory pageFactory = new PageFactory();
    public CombinedDAOTest() {
        SqlUserDataAccessObject userDAO;
        SqlPageDataAccessObject pageDAO;
        SqlBookDataAccessObject bookDAO;
        SQLiteJDBC connector;

        try {
            connector = new SQLiteJDBC("jdbc:sqlite:test.db");
            userDAO = new SqlUserDataAccessObject(connector, userFactory);
            bookDAO = new SqlBookDataAccessObject(connector, storyBookFactory);
            pageDAO = new SqlPageDataAccessObject(connector, pageFactory);
            combinedDAO = new CombinedDAO(userDAO, bookDAO, pageDAO);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        // Create the necessary tables for the test.
        connector.createUserTable();
        connector.createBookTable();
        connector.createPageTable();
    }



    @BeforeEach
    void setUp() {
        // Assuming you have methods to create instances of your factories.
        combinedDAO.deleteAll();
        System.out.println("deleted");
    }

    @Test
    public void testSQLiteJDBCConstructorException() {

        exception.expect(SQLException.class);
        new SQLiteJDBC("jdbc:sqlite:invalid_path.db");

    }

    @Test
    public void testTableCreationExceptions() throws SQLException, ClassNotFoundException {
        // Create an instance with a valid path
        SQLiteJDBC sqliteJDBC = new SQLiteJDBC("jdbc:sqlite:memory:");

        // Close the database connection to simulate the failure condition
        sqliteJDBC.getConnection().close();

        // Set up the expectation for an SQLException
        exception.expect(SQLException.class);

        // Call each table creation method. These should fail due to the closed connection.
        sqliteJDBC.createUserTable();
        sqliteJDBC.createBookTable();
        sqliteJDBC.createPageTable();
    }

    @Test
    void testPageCreationAndRetrieval() {
        // Create a new user, a book, and a page and save them to the database.
        User newUser = userFactory.create("testUser3", "testPass3");
        StoryBook newBook = storyBookFactory.create("testBook2", new ArrayList<Page>());
        Page newPage = pageFactory.create("testPage1", 1, new byte[0], 1);
        ArrayList<StoryBook> books = new ArrayList<>();
        ArrayList<Page> pages = new ArrayList<>();
        books.add(newBook);
        pages.add(newPage);
        newBook.setPages(pages);
        newUser.setStoryBooks(books);
        combinedDAO.save(newUser);


        // Retrieve the page from the database and validate it.
        Page retrievedPage = combinedDAO.getPage(1); // Assuming the page ID is 1.
        assertNotNull(retrievedPage);
        assertEquals(1, retrievedPage.getPageNumber());
        assertArrayEquals(new byte[]{}, retrievedPage.getImage());

    }

    @Test
    void testUpdates() {
        // Create a new user, a book, and a page and save them to the database.
        User newUser = userFactory.create("testUser3", "testPass3");
        StoryBook newBook = storyBookFactory.create("testBook2", new ArrayList<Page>());
        Page newPage = pageFactory.create("testPage1", 1, new byte[0], 1);
        ArrayList<StoryBook> books = new ArrayList<>();
        ArrayList<Page> pages = new ArrayList<>();
        books.add(newBook);
        pages.add(newPage);
        newBook.setPages(pages);
        newUser.setStoryBooks(books);
        combinedDAO.save(newUser);
        newPage.setTextContents("modified content");
        combinedDAO.save(newUser);


    }

    @Test
    void testUserCreationAndRetrieval() {
        // Create a new user and save it to the database.
        User newUser = userFactory.create("testUser1", "testPass1");
        combinedDAO.save(newUser);

        // Retrieve the user from the database and validate it.
        User retrievedUser = combinedDAO.getUser("testUser1");
        assertNotNull(retrievedUser);
        assertEquals("testUser1", retrievedUser.getUserName());


    }

    @Test
    void testBookCreationAndRetrieval() {
        // Create a new user and a book and save them to the database.
        User newUser = userFactory.create("testUser2", "testPass2");
        ArrayList<StoryBook> books = new ArrayList<>();
        StoryBook newBook = storyBookFactory.create("testBook1", new ArrayList<Page>());
        books.add(newBook);

        newUser.setStoryBooks(books);
        combinedDAO.save(newUser);

        // Retrieve the book from the database and validate it.
        StoryBook retrievedBook = combinedDAO.getBook("testBook1");
        assertNotNull(retrievedBook);
        assertEquals("testBook1", retrievedBook.getTitle());

    }



    @Test
    void testExistenceChecks() {
        // Create and save a new user, book, and page.
        User newUser = userFactory.create("testUser4", "testPass4");
        StoryBook newBook = storyBookFactory.create("testBook3", new ArrayList<Page>());
        Page newPage = pageFactory.create("testPage2", 1, new byte[0], -1);
        ArrayList<StoryBook> books = new ArrayList<>();
        ArrayList<Page> pages = new ArrayList<>();
        books.add(newBook);
        pages.add(newPage);
        newBook.setPages(pages);
        newUser.setStoryBooks(books);
        combinedDAO.save(newUser);


        // Check existence.
        assertTrue(combinedDAO.existsUser("testUser4"));
        assertTrue(combinedDAO.existsBook("testBook3"));
        assertTrue(combinedDAO.existsPage(1)); // Assuming the page ID is 1.



    }

}

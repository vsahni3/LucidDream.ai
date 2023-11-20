package data_access;
import java.sql.*;

/**
 * The SQLiteJDBC class provides functionality for managing a SQLite database.
 * It allows the creation of connections and tables within the database.
 */
public class SQLiteJDBC {
    private Connection c;

    /**
     * Constructor for SQLiteJDBC.
     * It initializes the JDBC driver and establishes a connection to the SQLite database.
     * On failure, it prints the error message and exits the application.
     */
    public SQLiteJDBC() {


        try {
            Class.forName("org.sqlite.JDBC");
            this.c = DriverManager.getConnection("jdbc:sqlite:test.db");
        } catch ( Exception e ) {
            System.err.println( e.getClass().getName() + ": " + e.getMessage() );
            System.exit(0);
        }
        System.out.println("Opened database successfully");
    }

    /**
     * Retrieves the current database connection.
     * @return The current Connection object.
     */
    public Connection getConnection() {
        return this.c;
    }

    /**
     * Creates a new table for users in the database if it does not already exist.
     * The table includes columns for username and password.
     */
    public void createUserTable() {
        Statement stmt = null;
        try {
            stmt = c.createStatement();
            String sql = "CREATE TABLE IF NOT EXISTS USER " +
                    "(userName TEXT PRIMARY KEY     NOT NULL," +
                    " password           TEXT    NOT NULL, )";
            stmt.executeUpdate(sql);
            stmt.close();
            System.out.println("Table created successfully");
        } catch (Exception e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
        }
    }

    /**
     * Creates a new table for books in the database if it does not already exist.
     * The table includes columns for the title and a foreign key reference to the User table.
     */
    public void createBookTable() {
        Statement stmt = null;
        try {
            stmt = c.createStatement();
            String sql = "CREATE TABLE IF NOT EXISTS BOOK " +
                    "(title TEXT PRIMARY KEY     NOT NULL," +
                    "FOREIGN KEY (userID) REFERENCES User(ID)";
            stmt.executeUpdate(sql);
            stmt.close();
            System.out.println("Table created successfully");
        } catch (Exception e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
        }
    }


    /**
     * Creates a new table for pages in the database if it does not already exist.
     * The table includes columns for page ID, contents, number, image,
     * and a foreign key reference to the Book table.
     */
    public void createPageTable() {
        Statement stmt = null;
        try {
            stmt = c.createStatement();
            String sql = "CREATE TABLE IF NOT EXISTS PAGE " +
                    "(pageID INTEGER PRIMARY KEY     NOT NULL," +
                    "pageContents           TEXT    NOT NULL, " +
                    "pageNumber     INTEGER NOT NULL, " +
                    "image     BLOB NOT NULL, " +
                    "FOREIGN KEY (bookID) REFERENCES BOOK(TITLE)";
            stmt.executeUpdate(sql);
            stmt.close();
            System.out.println("Table created successfully");
        } catch (Exception e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
        }
    }
}


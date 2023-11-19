package data_access;
import java.sql.*;

public class SQLiteJDBC {
    private Connection c;
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

    public Connection getConnection() {
        return this.c;
    }
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

    public void createPageTable() {
        Statement stmt = null;
        try {
            stmt = c.createStatement();
            String sql = "CREATE TABLE IF NOT EXISTS PAGE " +
                    "(ID INTEGER PRIMARY KEY     NOT NULL," +
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


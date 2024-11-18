import java.sql.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

/*
Damien B.
CEN 3024C
November 17
Class name: DatabaseHandler
Purpose: Handles all SQL queries to the database
 */
public class DatabaseHandler {
    public static DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    public static ArrayList<Book> books = new ArrayList<>();

    // Method to connect to the SQLite database
    private static Connection connect() {
        String url = "jdbc:sqlite:lmsdb.db";
        Connection conn = null;
        try {
            conn = DriverManager.getConnection(url);
            System.out.println("Connection to SQLite has been established.");
        } catch (SQLException e) {
            System.out.println("Failed to connect to the database: " + e.getMessage());
        }
        return conn;
    }

    /**
     * Inserts a new book into the database.
     *
     * @param name          The name of the book.
     * @param author        The author of the book.
     * @param isCheckedOut  Whether the book is checked out or not.
     * @param returnDate    The return date if the book is checked out (null if not).
     * @return void
     */
    public static void insertBook(String name, String author, boolean isCheckedOut, String returnDate) {
        String sql = "INSERT INTO Books(name, author, isCheckedOut, returnDate) VALUES(?,?,?,?)";

        try (Connection conn = connect(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, name);
            pstmt.setString(2, author);
            pstmt.setBoolean(3, isCheckedOut);
            pstmt.setString(4, returnDate);
            pstmt.executeUpdate();
            System.out.println("Book inserted successfully!");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    /**
     * Selects all books from the database and stores them in the books list.
     *
     * @return void
     */
    public static void selectAllBooks() {
        books.clear();
        String sql = "SELECT id, name, author, isCheckedOut, returnDate FROM Books";
        try (Connection conn = connect(); Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                books.add(new Book(rs.getInt("id"), rs.getString("name"),
                        rs.getString("author"),
                        rs.getBoolean("isCheckedOut"),
                        rs.getString("returnDate")));
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    /**
     * Deletes a book from the database by its ID.
     *
     * @param id  The ID of the book to be deleted.
     * @return void
     */
    public static void deleteBookByID(int id) {
        String sql = "DELETE FROM books WHERE id = ?";

        try (Connection conn = connect(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            int rowsAffected = pstmt.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Book with ID " + id + " has been removed.");
            } else {
                System.out.println("No book found with ID " + id + ".");
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    /**
     * Deletes a book from the database by its name.
     *
     * @param name  The name of the book to be deleted.
     * @return void
     */
    public static void deleteBookByName(String name) {
        String sql = "DELETE FROM Books WHERE name = ?";

        try (Connection conn = connect(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, name);
            int rowsAffected = pstmt.executeUpdate();

            if (rowsAffected > 0) {
                System.out.println("Book with name '" + name + "' has been removed.");
            } else {
                System.out.println("No book found with name '" + name + "'.");
            }
        } catch (SQLException e) {
            System.out.println("Error removing book: " + e.getMessage());
        }
    }

    /**
     * Checks in a book, setting its checked-out status to false and return date to null.
     *
     * @param name  The name of the book to be checked in.
     * @return void
     */
    public static void checkInBook(String name) {
        String sql = "UPDATE books SET isCheckedOut = ?, returnDate = ? WHERE name = ?";

        try (Connection conn = connect(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setBoolean(1, false);
            pstmt.setString(2, null);
            pstmt.setString(3, name);
            int rowsAffected = pstmt.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Book with title '" + name + "' has been checked in.");
            } else {
                System.out.println("No book found with title '" + name + "'.");
            }
        } catch (SQLException e) {
            System.out.println("Error removing book: " + e.getMessage());
        }
    }

    /**
     * Checks out a book, setting its checked-out status to true and return date to one week from today.
     *
     * @param name  The name of the book to be checked out.
     * @return void
     */
    public static void checkOutBook(String name) {
        String sql = "UPDATE books SET isCheckedOut = ?, returnDate = ? WHERE name = ?";
        String returnDate = LocalDate.now().plusWeeks(1).format(dateFormatter);
        try (Connection conn = connect(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setBoolean(1, true);
            pstmt.setString(2, returnDate);
            pstmt.setString(3, name);
            int rowsAffected = pstmt.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Book with title '" + name + "' has been checked out. Return date: " + returnDate);
            } else {
                System.out.println("No book found with title '" + name + "'.");
            }
        } catch (SQLException e) {
            System.out.println("Error removing book: " + e.getMessage());
        }
    }

    /**
     * Retrieves a book by its name and returns it as a Book object.
     *
     * @param name  The name of the book to be retrieved.
     * @return The Book object if found, otherwise null.
     */
    public static Book getBook(String name) {
        String sql = "SELECT * FROM Books WHERE name = ?";
        Book book = null;
        try (Connection conn = connect(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, name);
            ResultSet resultSet = pstmt.executeQuery();

            if (resultSet.next()) {
                int id = resultSet.getInt("id");
                String author = resultSet.getString("author");
                boolean isCheckedOut = resultSet.getBoolean("isCheckedOut");
                String returnDate = resultSet.getString("returnDate");
                book = new Book(id, name, author, isCheckedOut, returnDate);
            } else {
                System.out.println("No book found with name '" + name + "'.");
            }
        } catch (SQLException e) {
            System.out.println("Error removing book: " + e.getMessage());
        }
        return book;
    }

    /**
     * Retrieves a book by its ID and returns it as a Book object.
     *
     * @param id  The ID of the book to be retrieved.
     * @return The Book object if found, otherwise null.
     */
    public static Book getBook(int id) {
        String sql = "SELECT * FROM Books WHERE id = ?";
        Book book = null;
        try (Connection conn = connect(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            ResultSet resultSet = pstmt.executeQuery();

            if (resultSet.next()) {
                String bookName = resultSet.getString("name");
                String author = resultSet.getString("author");
                boolean isCheckedOut = resultSet.getBoolean("isCheckedOut");
                String returnDate = resultSet.getString("returnDate");
                book = new Book(id, bookName, author, isCheckedOut, returnDate);
            } else {
                System.out.println("No book found with ID '" + id + "'.");
            }
        } catch (SQLException e) {
            System.out.println("Error removing book: " + e.getMessage());
        }
        return book;
    }
}

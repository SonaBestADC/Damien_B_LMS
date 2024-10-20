import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/*
Damien B.
CEN 3024C
September 8th
Class name: Book
Purpose: A data type for storing information about each book; values include: ID, name, author.
 */
public class Book {
    public int id;
    public String name;
    public String author;
    public boolean isCheckedOut;
    public LocalDate returnDate;

    /**
     * Book:
     * The constructor for the Book class. Used to save information about each book.
     *
     * @param id           The ID value for each book
     * @param name         The name of each book
     * @param author       The author of each book
     * @param isCheckedOut Bool to see if book is checked out or not
     */
    public Book(int id, String name, String author, boolean isCheckedOut, LocalDate returnDate) {
        this.id = id;
        this.name = name;
        this.author = author;
        this.isCheckedOut = isCheckedOut;
        this.returnDate = returnDate;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }


    public String getAuthor() {
        return author;
    }

    public boolean getIsCheckedOut() {
        return isCheckedOut;
    }

    public void setIsCheckedOut(boolean checkedOut) {
        isCheckedOut = checkedOut;
    }

    public LocalDate getReturnDate() {
        return returnDate;
    }

    public void setReturnDate(LocalDate returnDate) {
        this.returnDate = returnDate;
    }

    /**
     * toString
     * Writes all values of book as a string, used in saving book to text file.
     *
     * @return String of all values of the book.
     */
    @Override
    public String toString() {
        return id + "," + name + "," + author + "," + isCheckedOut + "," + returnDate + "\n";
    }
}

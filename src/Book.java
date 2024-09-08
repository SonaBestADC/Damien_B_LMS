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

    /** Book:
     *  The constructor for the Book class. Used to save information about each book.
     * @param id The ID value for each book
     * @param name The name of each book
     * @param author The author of each book
     */
    public Book(int id, String name, String author) {
        this.id = id;
        this.name = name;
        this.author = author;
    }

    /** getID:
     *  Getter for the ID value.
     * @return ID of current book.
     */
    public int getId() {
        return id;
    }

    /** setID
     *  Setter for ID value
     * @param id value of ID being passed in.
     */
    public void setId(int id){
        this.id = id;
    }

    /** getName:
     *  Getter for the book name.
     * @return name of current book.
     */
    public String getName() {
        return name;
    }

    /** getAuthor
     *  Getter for the book author
     * @return name of the author of current book.
     */
    public String getAuthor() {
        return author;
    }


    /** toString
     *  Writes all values of book as a string, used in saving book to text file.
     * @return String of all values of the book.
     */
    @Override
    public String toString() {
        return id + "," + name + "," + author + "\n";
    }
}

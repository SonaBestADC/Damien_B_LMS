/*
Damien B.
CEN 3024C
September 8th
Class name: Main
Purpose: Acts as start point and runs logic for menu system and reading/writing to file.
 */
import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import javax.swing.*;
import javax.xml.crypto.Data;
import java.sql.*;

public class Main {
    public static ArrayList<Book> books = new ArrayList<>();
    public static Scanner scanner = new Scanner(System.in);
    public static DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");



    // Connect to the database

    /** main:
     *Creates a File Selector Panel and starts application
     */
    public static void main(String[] args) {
//        Populates Test Data
//        DatabaseHandler.insertBook("Pride and Prejudice", "Jane Austen", false, null);
//        DatabaseHandler.insertBook("The Great Gatsby", "F. Scott Fitzgerald", true, "2024-11-25");
//        DatabaseHandler.insertBook("Moby Dick", "Herman Melville", false, null);
//        DatabaseHandler.insertBook("War and Peace", "Leo Tolstoy", true, "2024-12-10");
//        DatabaseHandler.insertBook("1984", "George Orwell", false, null);
//        DatabaseHandler.insertBook("The Catcher in the Rye", "J.D. Salinger", true, "2024-11-30");
//        DatabaseHandler.insertBook("The Hobbit", "J.R.R. Tolkien", false, null);
//        DatabaseHandler.insertBook("Brave New World", "Aldous Huxley", true, "2024-12-05");
//        DatabaseHandler.insertBook("To Kill a Mockingbird", "Harper Lee", false, null);
//        DatabaseHandler.insertBook("Crime and Punishment", "Fyodor Dostoevsky", true, "2024-12-01");
//        DatabaseHandler.insertBook("The Odyssey", "Homer", false, null);
//        DatabaseHandler.insertBook("Catch-22", "Joseph Heller", true, "2024-11-20");
//        DatabaseHandler.insertBook("The Lord of the Rings", "J.R.R. Tolkien", false, null);
//        DatabaseHandler.insertBook("The Shining", "Stephen King", true, "2024-11-15");
//        DatabaseHandler.insertBook("Frankenstein", "Mary Shelley", false, null);
//        DatabaseHandler.insertBook("Dracula", "Bram Stoker", true, "2024-12-01");
//        DatabaseHandler.insertBook("The Picture of Dorian Gray", "Oscar Wilde", false, null);
//        DatabaseHandler.insertBook("Les Misérables", "Victor Hugo", true, "2024-11-28");
//        DatabaseHandler.insertBook("The Grapes of Wrath", "John Steinbeck", false, null);
//        DatabaseHandler.insertBook("The Eminence in Shadow Vol. 1", "Daisuke Aizawa", false, null);
//        DatabaseHandler.deleteBookByID(19);
//        DatabaseHandler.deleteBookByName("The Eminence in Shadow Vol. 1");
//        DatabaseHandler.checkInBook("Les Misérables");
//        DatabaseHandler.checkOutBook("Les Misérables");


        new LmsForm();

    }

}
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

public class Main {
    public static ArrayList<Book> books = new ArrayList<>();
    public static Scanner scanner = new Scanner(System.in);

    /** main:
     *This runs the readTextFile() to set the books array list equal to whatever is written in the text file.
     * The text file and array list will always try to be equal at the same time. Then it runs the menu() method where the console
     * based logic happens.
     * @param args
     */
    public static void main(String[] args) {
        readTextFile();
        menu();
    }

    /** readTextFile:
     * This reads the text.txt file then sets the books ArrayList to the contents of the text file.
     */
    private static void readTextFile() {
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader("text.txt"))) {
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                String[] values = line.split(",");
                books.add(new Book(Integer.parseInt(values[0]), values[1], values[2]));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /** menu:
     *  The menu method contains most of the console based logic. It's a switch statement that takes a char as an input
     *  and outputs one method depending on choice. 1 -> addBook(), 2 -> deleteBook(), 3 -> listBooks(), 4 -> returns.
     */
    private static void menu() {
        System.out.println("What do you want to do?");
        System.out.println("1. Add new Book");
        System.out.println("2. Remove a Book");
        System.out.println("3. List all Books");
        System.out.println("4. Quit");
        char choice = scanner.next().charAt(0);
        switch (choice) {
            case '1' -> addBook();
            case '2' -> deleteBook();
            case '3' -> listBooks();
            case '4' -> {
                System.out.println("Quitting.");
            }
            default -> {
                System.out.println("Invalid choice");
                menu();
            }
        }
    }

    /** listBooks:
     *  Lists out of the books inside the books Arraylist. String is formatted to allow for ample space between each column.
     */
    private static void listBooks() {
        System.out.printf("%-5s %-30s %-30s%n", "ID", "Name", "Author");
        for(Book book : books){
            System.out.printf("%-5d %-30s %-30s%n", book.getId(), book.getName(), book.getAuthor());
        }
        menu();
    }

    /** deleteBook:
     *  Deletes a book from the array list based off an ID value. ID values are index + 1 of arraylist so
     *  when removing from ArrayList it does index - 1.
     *  Once done it runs saveBooks() to save to text file.
     */
    private static void deleteBook() {
        System.out.println("Please write ID of book you wish to remove");
        int id = scanner.nextInt();
        books.remove(id - 1);
        saveBooks();
        resetIDs();
        menu();
    }

    /** resetIDs:
     *  Resets the ID values for text file and book arrayList. This is done because if a book is removed the IDs will be in
     *  numerical order, if this isn't ran the IDs will have holes in them.
     */
    private static void resetIDs() {
        for(int i = 0; i < books.size(); i++){
            books.get(i).setId(i + 1);
        }
    }

    /** addBook:
     *  Asks the user for the name and author of a book. Then gets the books.size() + 1 for the ID.
     *  Once done it runs saveBooks() to save to text file.
     */
    private static void addBook() {
        String name;
        String author;
        scanner.nextLine();
        System.out.println("Please enter the name of the book");
        name = scanner.nextLine();
        System.out.println("Please enter the name of the author");
        author = scanner.nextLine();
        books.add(new Book(books.size() + 1, name, author));
        saveBooks();
        menu();
    }

    /** saveBooks:
     *  Writes the contents of the books arrayList to the text.txt file.
     */
    private static void saveBooks() {
        try(BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter("text.txt"))){
            for(Book book : books){
                bufferedWriter.write(book.toString());
            }
        }catch (IOException e){
            e.printStackTrace();
        }
    }
}
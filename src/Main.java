import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

public class Main {
    public static ArrayList<Book> books = new ArrayList<>();
    public static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        readTextFile();
        menu();
    }

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
                return;
            }
            default -> {
                System.out.println("Invalid choice");
                menu();
            }
        }
    }

    private static void listBooks() {
        System.out.printf("%-5s %-30s %-30s%n", "ID", "Name", "Author");
        for(Book book : books){
            System.out.printf("%-5d %-30s %-30s%n", book.getId(), book.getName(), book.getAuthor());
        }
        menu();
    }

    private static void deleteBook() {
        System.out.println("Please write ID of book you wish to remove");
        int id = scanner.nextInt();
        books.remove(id - 1);
        saveBooks();
    }

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
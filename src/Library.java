import java.io.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class Library {
    public static ArrayList<Book> books = new ArrayList<>();
    public static DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    public static String file;

    public static void readTextFile(String file){
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(file))) {
            Library.file = file;
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                String[] values = line.split(",");
                LocalDate returnDate;
                try{
                    returnDate = LocalDate.parse(values[4], dateFormatter);
                }catch (Exception E){
                    returnDate = null;
                }
                books.add(new Book(Integer.parseInt(values[0]), values[1], values[2], Boolean.parseBoolean(values[3]), returnDate));
                System.out.println(books);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void addBook(String name, String author) {
        books.add(new Book(books.size() + 1, name, author, false, null));
        saveBooks();
    }
    public static void saveBooks() {
        try(BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(file))){
            for(Book book : books){
                bufferedWriter.write(book.toString());
            }
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    public static void deleteBookBarcode(int id) {
        books.remove(id - 1);
        resetIDs();
        saveBooks();
    }
    public static void deleteBookByName(String name) {
        for(Book book : books){
            if(book.getName().equalsIgnoreCase(name)){
                books.remove(book.getId() - 1);
                resetIDs();
                saveBooks();
                return;
            }
        }
    }

    public static boolean checkBookOut(String title){
        for(Book book : books){
            if(book.getName().equalsIgnoreCase(title)){
                book.setIsCheckedOut(true);
                book.setReturnDate(LocalDate.parse(LocalDate.now().plusWeeks(1).format(dateFormatter)));
                saveBooks();
                return true;
            }
        }
        return false;
    }
    public static boolean checkBookIn(String title){
        for(Book book : books){
            if(book.getName().equalsIgnoreCase(title)){
                book.setIsCheckedOut(false);
                book.setReturnDate(null);
                saveBooks();
                return false;
            }
        }
        return true;
    }

    public static Book getBook(String title) {
        for (Book book : books) {
            if (book.getName().equalsIgnoreCase(title)) {
                return book;
            }
        }
        return null;
    }
    public static Book getBook(int id) {
        for (Book book : books) {
            if (book.getId() == id) {
                return book;
            }
        }
        return null;
    }


    public static void resetIDs() {
        for(int i = 0; i < books.size(); i++){
            books.get(i).setId(i + 1);
        }
    }
}

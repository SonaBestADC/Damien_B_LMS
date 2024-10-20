import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;
class MainTest {

    @BeforeEach
    public void restart(){
        Main.books.clear();
    }

    @Test
    public void testAddBook(){
        String bookName = "The Eminence in Shadow";
        String author = "Daisuke Aizawa";

        Main.books.add(new Book(Main.books.size() + 1, bookName, author, false, null));

        assertEquals(1, Main.books.size());
        assertEquals(bookName, Main.books.get(0).getName());
        assertEquals(author, Main.books.get(0).getAuthor());
        assertFalse(Main.books.get(0).getIsCheckedOut());
    }

    @Test
    public void testRemoveBookById(){
        String bookName = "The Eminence in Shadow";
        String author = "Daisuke Aizawa";

        Main.books.add(new Book(Main.books.size() + 1, bookName, author, false, null));

        Main.deleteBookBarcode();
        Main.books.remove(0);

        assertTrue(Main.books.isEmpty());
    }

    @Test
    public void testRemoveBookByName(){
        String bookName = "The Eminence in Shadow";
        String author = "Daisuke Aizawa";

        Main.books.add(new Book(Main.books.size() + 1, bookName, author, false, null));

        Main.deleteBookByName();

        assertTrue(Main.books.isEmpty());
    }

    @Test
    public void testCheckOutBook(){
        String bookName = "The Eminence in Shadow";
        String author = "Daisuke Aizawa";

        Main.books.add(new Book(Main.books.size() + 1, bookName, author, false, null));

        Main.checkBook("Please enter the title of the book to check out.", true);

        assertNotNull(Main.books.get(0).getReturnDate());
        assertTrue(Main.books.get(0).getIsCheckedOut());
    }

    @Test
    public void testCheckInBook(){
        String bookName = "The Eminence in Shadow";
        String author = "Daisuke Aizawa";
        LocalDate returnDate = LocalDate.now().plusWeeks(1);

        Main.books.add(new Book(Main.books.size() + 1, bookName, author, true, returnDate));

        Main.checkBook("Please enter the title of the book to check in.", false);

        assertNull(Main.books.get(0).getReturnDate());
        assertFalse(Main.books.get(0).getIsCheckedOut());
    }

}
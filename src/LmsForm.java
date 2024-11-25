import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Objects;

/**
 * Class: LmsForm
 *
 * This class represents the main GUI frame for interacting with the library database.
 * It provides functionalities to view, edit, check out, and delete books in the library.
 *
 * Author: Damien B.
 * Course: CEN 3024C
 * Date: November 3rd
 */
public class LmsForm extends JFrame {
    private DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    private JPanel LmsPanel;
    private JTextField bookIdTextField;
    private JTextField bookNameTextField;
    private JLabel bookById;
    private JLabel bookByName;
    private JLabel isCheckedOutLabel;
    private JLabel isCheckedOutMarker;
    private JLabel selectedBook;
    private JLabel selectedBookMarker;
    private JLabel returnDateLabel;
    private JLabel checkBookLabel;
    private JButton checkBookBtn;
    private JLabel libaryDisplayLabel;
    private JTable bookTabel;
    private JButton deleteBookIdBtn;
    private JButton deleteBookNameBtn;
    private JTextField checkBookIdText;
    private JLabel bookIdCheckOutLabel;
    private JTextField CheckBookNameText;
    private JLabel bookNameCheckOutLabel;
    private JLabel returnDateMarker;
    private JLabel isCheckMarker;
    private JList<Object> bookList;

    /**
     * Constructor: LmsForm
     *
     * Initializes the LMS form with a specified file for book data. Sets up the JFrame, reads the book data,
     * generates the table, and adds listeners for various UI components.
     */
    public LmsForm() {
        setTitle("LMS");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setContentPane(LmsPanel);
        pack();
        DatabaseHandler.selectAllBooks();

        generateTable();

        bookTabel.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                if (e.getValueIsAdjusting()) return;

                int selectedRow = bookTabel.getSelectedRow();
                if (selectedRow == -1) {
                    return;
                }
                int id = (int) bookTabel.getValueAt(selectedRow, 0);
                String name = (String) bookTabel.getValueAt(selectedRow, 1);
                String author = (String) bookTabel.getValueAt(selectedRow, 2);
                boolean isCheckedOut = (boolean) bookTabel.getValueAt(selectedRow, 3);
                String returnDate = (String) bookTabel.getValueAt(selectedRow, 4);

                Book selectedBook = new Book(id, name, author, isCheckedOut, returnDate);
                setFields(selectedBook);
            }
        });

        deleteBookIdBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int id = Integer.parseInt(bookIdTextField.getText());
                DatabaseHandler.deleteBookByID(id);
                bookTabel.clearSelection();
                fieldReset();
            }
        });

        deleteBookNameBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String name = bookNameTextField.getText();
                DatabaseHandler.deleteBookByName(name);
                bookTabel.clearSelection();
                fieldReset();
            }
        });

        checkBookBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!Objects.equals(CheckBookNameText.getText(), "")) {
                    String name = CheckBookNameText.getText();
                    Book book = DatabaseHandler.getBook(name);
                    if (book.getIsCheckedOut()) {
                        DatabaseHandler.checkInBook(name);
                    } else {
                        DatabaseHandler.checkOutBook(name);
                    }
                } else {
                    int id = Integer.parseInt(checkBookIdText.getText());
                    Book book = DatabaseHandler.getBook(id);
                    if (book.getIsCheckedOut()) {
                        DatabaseHandler.checkInBook(book.getName());
                    } else {
                        DatabaseHandler.checkOutBook(book.getName());
                    }
                }

                bookTabel.clearSelection();
                fieldReset();
            }
        });

        setLocationRelativeTo(null);
        setVisible(true);
    }

    /**
     * Method: generateTable
     *
     * Creates a table to display the books with their details, including ID, name, author, checked out status,
     * and return date. Populates the table with data from the Library's book list.
     */
    private void generateTable() {
        String[] columnNames = {"ID", "Name", "Author", "Checked Out", "Return Date"};
        Object[][] data = new Object[DatabaseHandler.books.size()][5];
        for (int i = 0; i < DatabaseHandler.books.size(); i++) {
            Book book = DatabaseHandler.books.get(i);
            data[i][0] = book.getId();
            data[i][1] = book.getName();
            data[i][2] = book.getAuthor();
            data[i][3] = book.getIsCheckedOut();
            data[i][4] = book.getReturnDate() != null ? book.getReturnDate().toString() : "N/A";
        }
        DefaultTableModel model = new DefaultTableModel(data, columnNames);
        bookTabel.setModel(model);
        bookTabel.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
    }

    /**
     * Method: setFields
     *
     * Updates the form's fields with the details of the selected book, including the book's name, checked out status,
     * return date, and associated IDs.
     *
     * @param book the book object whose details will be displayed in the form.
     */
    private void setFields(Book book) {
        selectedBookMarker.setText(book.getName());

        if (book.getIsCheckedOut()) {
            isCheckMarker.setText("True");
        } else {
            isCheckMarker.setText("False");
        }
        if (book.getReturnDate() == null) {
            returnDateMarker.setText("N/A");
        } else {
            returnDateMarker.setText(book.getReturnDate());
        }

        bookIdTextField.setText(String.valueOf(book.getId()));
        checkBookIdText.setText(String.valueOf(book.getId()));
        bookNameTextField.setText(book.getName());
        CheckBookNameText.setText(book.getName());
    }

    /**
     * Method: fieldReset
     *
     * Resets all input fields and the displayed book table to its default state, clearing selections and markers.
     */
    private void fieldReset() {
        DatabaseHandler.selectAllBooks();
        generateTable();
        selectedBookMarker.setText("");
        isCheckMarker.setText("");
        returnDateMarker.setText("");
        bookIdTextField.setText("");
        checkBookIdText.setText("");
        bookNameTextField.setText("");
        CheckBookNameText.setText("");
    }
}

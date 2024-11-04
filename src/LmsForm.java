import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Objects;

/*
Damien B.
CEN 3024C
November 3rd
Class name: FileSelectorPanel
Purpose: Main Frame where user interacts with GUI to edit library database
 */
public class LmsForm extends JFrame {
    private String file;
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
     * Initializes the LMS form with a specified file for book data. Sets up the JFrame, reads the book data,
     * generates the table, and adds listeners for various UI components.
     *
     * Arguments:
     * - String file: the path to the file containing book data.
     *
     * Return Value: None
     */
    public LmsForm(String file) {
        this.file = file;
        setTitle("LMS");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setContentPane(LmsPanel);
        pack();
        Library.readTextFile(file);

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

                LocalDate parsedReturnDate = null;
                if (!"null".equals(returnDate) && !"N/A".equals(returnDate)) {
                    parsedReturnDate = LocalDate.parse(returnDate, formatter);
                }

                Book selectedBook = new Book(id, name, author, isCheckedOut, parsedReturnDate);
                setFields(selectedBook);
            }
        });

        deleteBookIdBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int id = Integer.parseInt(bookIdTextField.getText());
                Library.deleteBookBarcode(id);
                bookTabel.clearSelection();
                fieldReset();
            }
        });

        deleteBookNameBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String name = bookNameTextField.getText();
                Library.deleteBookByName(name);
                bookTabel.clearSelection();
                fieldReset();
            }
        });

        checkBookBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(!Objects.equals(CheckBookNameText.getText(), "")){
                    String name = CheckBookNameText.getText();
                    Book book = Library.getBook(name);
                    if(book.getIsCheckedOut()){
                        Library.checkBookIn(name);
                    } else {
                        Library.checkBookOut(name);
                    }
                } else {
                    int id = Integer.parseInt(checkBookIdText.getText());
                    Book book = Library.getBook(id);
                    if(book.getIsCheckedOut()){
                        Library.checkBookIn(book.getName());
                    } else {
                        Library.checkBookOut(book.getName());
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
     * Creates a table to display the books with their details, including ID, name, author, checked out status,
     * and return date. Populates the table with data from the Library's book list.
     *
     * Arguments: None
     * Return Value: None
     */
    private void generateTable() {
        String[] columnNames = {"ID", "Name", "Author", "Checked Out", "Return Date"};
        Object[][] data = new Object[Library.books.size()][5];
        for (int i = 0; i < Library.books.size(); i++) {
            Book book = Library.books.get(i);
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
     * Updates the form's fields with the details of the selected book, including the book's name, checked out status,
     * return date, and associated IDs.
     *
     * Arguments:
     * - Book book: the book object whose details will be displayed in the form.
     *
     * Return Value: None
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
            returnDateMarker.setText(book.getReturnDate().format(formatter));
        }

        bookIdTextField.setText(String.valueOf(book.getId()));
        checkBookIdText.setText(String.valueOf(book.getId()));
        bookNameTextField.setText(book.getName());
        CheckBookNameText.setText(book.getName());
    }

    /**
     * Method: fieldReset
     * Resets all input fields and the displayed book table to its default state, clearing selections and markers.
     *
     * Arguments: None
     * Return Value: None
     */
    private void fieldReset() {
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

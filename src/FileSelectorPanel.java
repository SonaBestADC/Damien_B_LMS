import javax.swing.*;
import java.awt.*;
import java.io.File;
/*
Damien B.
CEN 3024C
November 3rd
Class name: FileSelectorPanel
Purpose: Allows the user to select a file to read
 */

public class FileSelectorPanel extends JFrame {
    public String file;

    /**
     * Constructor: FileSelectorPanel
     * Initializes the JFrame and sets the layout. Calls the outputFile method
     * to allow the user to select a file and then creates a new LmsForm with the selected file.
     *
     * Arguments: None
     * Return Value: None
     */
    public FileSelectorPanel() {
        setLayout(new FlowLayout(FlowLayout.CENTER));
        outputFile();
        new LmsForm(file);
    }

    /**
     * Method: outputFile
     * Opens a file chooser dialog for the user to select a .txt file. Validates that the
     * selected file has a .txt extension and stores the file path. If the user cancels
     * the operation, it exits the loop gracefully.
     *
     * Arguments: None
     * Return Value: None
     */
    private void outputFile() {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
        boolean validFileSelected = false;

        while (!validFileSelected) {
            int result = fileChooser.showDialog(this, "Select .txt File");

            if (result == JFileChooser.APPROVE_OPTION) {
                File selectedFile = fileChooser.getSelectedFile();
                if (selectedFile.getName().toLowerCase().endsWith(".txt")) {
                    file = selectedFile.getAbsolutePath();
                    validFileSelected = true;
                } else {
                    JOptionPane.showMessageDialog(this, "Please select a .txt file.", "Invalid File", JOptionPane.ERROR_MESSAGE);
                }
            } else {
                System.out.println("Operation canceled by the user.");
                break;
            }
        }
    }
}

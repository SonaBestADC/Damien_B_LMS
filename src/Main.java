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

public class Main {
    public static ArrayList<Book> books = new ArrayList<>();
    public static Scanner scanner = new Scanner(System.in);
    public static DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");


    /** main:
     *Creates a File Selector Panel and starts application
     */
    public static void main(String[] args) {
        new FileSelectorPanel();

    }

}
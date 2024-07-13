package com.mycompany.mavenproject1;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Records {

    private JFrame frame;
    private JTextField studentNumberField;
    private JTextField lastNameField;
    private JTextField firstNameField;
    private JTextField usernameField;
    private JPasswordField passwordField; // Added password field
    private JTable dataTable;

    private final String url = "jdbc:mysql://localhost:3306/database ni reyshane";
    private final String user = "root";
    private final String dbPassword = ""; // Rename to avoid confusion with input password

    private DefaultTableModel tableModel;

    public Records() {
        initialize();
        populateTable();
    }

    private void initialize() {
        frame = new JFrame();
        frame.setTitle("Table of Records");
        frame.setSize(600,600);
        frame.setBounds(100, 100, 800, 400);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().setLayout(null);
        frame.setLocationRelativeTo(null);
        frame.getContentPane().setBackground(new Color(247, 224, 117));


        JLabel studentNumberLabel = new JLabel("Student Number:");
        studentNumberLabel.setBounds(50, 20, 120, 20);
        frame.getContentPane().add(studentNumberLabel);

        studentNumberField = new JTextField();
        studentNumberField.setBounds(180, 20, 160, 20);
        frame.getContentPane().add(studentNumberField);
        studentNumberField.setColumns(10);

        JLabel lastNameLabel = new JLabel("Last Name:");
        lastNameLabel.setBounds(50, 50, 120, 20);
        frame.getContentPane().add(lastNameLabel);

        lastNameField = new JTextField();
        lastNameField.setBounds(180, 50, 160, 20);
        frame.getContentPane().add(lastNameField);
        lastNameField.setColumns(10);

        JLabel firstNameLabel = new JLabel("First Name:");
        firstNameLabel.setBounds(50, 80, 120, 20);
        frame.getContentPane().add(firstNameLabel);

        firstNameField = new JTextField();
        firstNameField.setBounds(180, 80, 160, 20);
        frame.getContentPane().add(firstNameField);
        firstNameField.setColumns(10);

        JLabel usernameLabel = new JLabel("Username:");
        usernameLabel.setBounds(50, 110, 120, 20);
        frame.getContentPane().add(usernameLabel);

        usernameField = new JTextField();
        usernameField.setBounds(180, 110, 160, 20);
        frame.getContentPane().add(usernameField);
        usernameField.setColumns(10);

        JLabel passwordLabel = new JLabel("Password:"); // Added password label
        passwordLabel.setBounds(50, 140, 120, 20);
        frame.getContentPane().add(passwordLabel);

        passwordField = new JPasswordField(); // Added password field
        passwordField.setBounds(180, 140, 160, 20);
        frame.getContentPane().add(passwordField);

        JButton registerButton = new JButton("REGISTER");
        registerButton.setBounds(50, 180, 100, 30);
        frame.getContentPane().add(registerButton);

        JButton deleteButton = new JButton("DELETE");
        deleteButton.setBounds(170, 180, 100, 30);
        frame.getContentPane().add(deleteButton);

        tableModel = new DefaultTableModel();
        dataTable = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(dataTable);
        scrollPane.setBounds(50, 220, 700, 150);
        frame.getContentPane().add(scrollPane);

        String[] columns = {"Student Number", "Last Name", "First Name", "Username"};
        tableModel.setColumnIdentifiers(columns);

        registerButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String studentNumber = studentNumberField.getText();
                String lastName = lastNameField.getText();
                String firstName = firstNameField.getText();
                String username = usernameField.getText();
                char[] inputPassword = passwordField.getPassword(); // Changed variable name to inputPassword
                registerStudent(studentNumber, lastName, firstName, username, inputPassword);
            }
        });

        deleteButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                deleteStudent();
            }
        });
    }

    private void populateTable() {
        String sql = "SELECT * FROM `table of records`";

        try (
                Connection connection = DriverManager.getConnection(url, user, dbPassword);
                PreparedStatement preparedStatement = connection.prepareStatement(sql);
                ResultSet resultSet = preparedStatement.executeQuery()
        ) {
            tableModel.setRowCount(0);

            while (resultSet.next()) {
                String studentNumber = resultSet.getString("Student Number");
                String lastName = resultSet.getString("Last Name");
                String firstName = resultSet.getString("First Name");
                String username = resultSet.getString("Username");

                Object[] row = {studentNumber, lastName, firstName, username};
                tableModel.addRow(row);
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(frame, "Error populating table:\n" + ex.getMessage(),
                    "Error", JOptionPane.ERROR_MESSAGE);
            ex.printStackTrace();
        }
    }

    private void registerStudent(String studentNumber, String lastName, String firstName, String username, char[] inputPassword) {
        String sql = "INSERT INTO `table of records` (`Student Number`, `Last Name`, `First Name`, `Username`, `Password`) VALUES (?, ?, ?, ?, ?)";

        try (
                Connection connection = DriverManager.getConnection(url, user, dbPassword);
                PreparedStatement preparedStatement = connection.prepareStatement(sql)
        ) {
            preparedStatement.setString(1, studentNumber);
            preparedStatement.setString(2, lastName);
            preparedStatement.setString(3, firstName);
            preparedStatement.setString(4, username);
            preparedStatement.setString(5, new String(inputPassword));

            int rowsInserted = preparedStatement.executeUpdate();
            if (rowsInserted > 0) {
                JOptionPane.showMessageDialog(frame, "A new student has been registered successfully.");
                populateTable();
                clearFields();
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(frame, "Error registering the student:\n" + ex.getMessage(),
                    "Error", JOptionPane.ERROR_MESSAGE);
            ex.printStackTrace();
        }
    }

    private void deleteStudent() {
        int selectedRowIndex = dataTable.getSelectedRow();

        if (selectedRowIndex == -1) {
            JOptionPane.showMessageDialog(frame, "Please select a row to delete.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        String username = (String) tableModel.getValueAt(selectedRowIndex, 3); // Assuming username is in the 4th column (index 3)
        String sql = "DELETE FROM `table of records` WHERE `Username` = ?";

        try (
                Connection connection = DriverManager.getConnection(url, user, dbPassword);
                PreparedStatement preparedStatement = connection.prepareStatement(sql)
        ) {
            preparedStatement.setString(1, username);
            int rowsDeleted = preparedStatement.executeUpdate();
            if (rowsDeleted > 0) {
                JOptionPane.showMessageDialog(frame, "Record deleted successfully.");
                tableModel.removeRow(selectedRowIndex); // Remove row from table model
                clearFields();
            } else {
                JOptionPane.showMessageDialog(frame, "No record found for deletion.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(frame, "Error deleting record:\n" + ex.getMessage(),
                    "Error", JOptionPane.ERROR_MESSAGE);
            ex.printStackTrace();
        }
    }

    private void clearFields() {
        studentNumberField.setText("");
        lastNameField.setText("");
        firstNameField.setText("");
        usernameField.setText("");
        passwordField.setText(""); // Clear password field
    }

    public void setVisible(boolean visible) {
        frame.setVisible(visible);
    }

    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    Records re = new Records();
                    re.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }
}

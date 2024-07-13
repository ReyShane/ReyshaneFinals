
package com.mycompany.mavenproject1;

import java.awt.*;
import javax.swing.*;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Arrays; 

/**
 *
 * @author admin
 */
public class Create_Account extends JFrame implements ActionListener{
    private final JLabel lblSignUp, lblLastName, lblFirstName, lblUserName, lblStudentNum, lblPasswordfield, lblConfirmPassword;
    private final JTextField txtStudentNum, txtLastName, txtFirstName, txtUserName ;
    private final JButton btnReset, btnSubmit;
    private final JPasswordField pfPasswordfield, pfConfirmPassword;
    
    private static final String URL = "jdbc:mysql://localhost:3306/database ni reyshane";
    private static final String USER = "root";
    private static final String PASSWORD = "";   
    
    Create_Account(){
        setTitle("Create Account");
        setSize(600,600);
        setLocationRelativeTo(null);
        setLayout(null);
        setResizable(false);
        setVisible(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        getContentPane().setBackground(new Color(247, 224, 117));
        
        lblSignUp = new JLabel("CREATE ACCOUNT", SwingConstants.CENTER);
        lblSignUp.setFont(new Font("Helvetica", Font.BOLD, 30));
        lblSignUp.setBounds(55, 30, 500, 50);
        lblSignUp.setVisible(true);
        add(lblSignUp);
        
        lblStudentNum = new JLabel("Student No#");
        lblStudentNum.setBounds(50, 100, 100, 30);
        lblStudentNum.setFont(new Font("Helvetica", Font.BOLD, 12));
        add(lblStudentNum);
        
        lblLastName = new JLabel("Last Name");
        lblLastName.setBounds(50,150, 100, 30);
        lblLastName.setFont(new Font("Helvetica", Font.BOLD, 12));
        lblLastName.setVisible(true);
        add(lblLastName);
        
        lblFirstName = new JLabel("First Name");
        lblFirstName.setBounds(50, 200, 100, 30);
        lblFirstName.setFont(new Font("Helvetica", Font.BOLD, 12));
        add(lblFirstName);
        
        lblUserName = new JLabel("Username");
        lblUserName.setBounds(50,250,100,30);
        lblUserName.setFont(new Font("Helvetica", Font.BOLD, 12));
        add(lblUserName);
       
        
        lblPasswordfield = new JLabel("Create Password:");
        lblPasswordfield.setBounds(50,300, 200, 30);
        lblPasswordfield.setFont(new Font("Helvetica", Font.BOLD, 12));
        add(lblPasswordfield);
        
        lblConfirmPassword = new JLabel("Confirm Password:");
        lblConfirmPassword.setBounds(50, 350, 200, 30);
        lblConfirmPassword.setFont(new Font("Helvetica", Font.BOLD, 12));
        add(lblConfirmPassword);
        
        txtStudentNum = new JTextField();
        txtStudentNum.setBounds(120, 100, 300, 30);
        txtStudentNum.setFont(new Font("Helvetica", Font.PLAIN, 12));
        add(txtStudentNum);
        
        txtLastName = new JTextField();
        txtLastName.setBounds(120, 150, 300, 30);
        txtLastName.setFont(new Font("Helvetica", Font.PLAIN, 12));
        add(txtLastName);
        
        txtFirstName = new JTextField();
        txtFirstName.setBounds(120, 200, 300, 30);
        txtFirstName.setFont(new Font("Helvetica", Font.PLAIN,12));
        add(txtFirstName);
        
        txtUserName = new JTextField();
        txtUserName.setBounds(120, 250, 300, 30);
        txtUserName.setFont(new Font("Helvetica", Font.PLAIN,12));
        add(txtUserName);
        
        pfPasswordfield = new JPasswordField();
        pfPasswordfield.setBounds(190,300,300,30);
        pfPasswordfield.setFont(new Font("Helvetica", Font.PLAIN,15));
        add(pfPasswordfield);
        
        pfConfirmPassword = new JPasswordField();
        pfConfirmPassword.setBounds(190,350,300,30);
        pfConfirmPassword.setFont(new Font("Helvetica", Font.PLAIN,15));
        add(pfConfirmPassword);
        
      
                
        btnReset = new JButton("RESET");
        btnReset.setBounds(200, 400, 100, 30);
        btnReset.setFont(new Font("Helvetica", Font.BOLD,15));
        add(btnReset);
        btnReset.addActionListener(this);
        
        btnSubmit = new JButton("SUBMIT");
        btnSubmit.setBounds(300, 400, 100, 30);
        btnSubmit.setFont(new Font("Helvetica", Font.BOLD,15));
        add(btnSubmit);
        btnSubmit.addActionListener(this);
      
    }
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == btnReset) {
            resetFields();
        } else if (e.getSource() == btnSubmit) {
            submitInfo();
            dispose();
        }
    }

    private void resetFields() {
        txtStudentNum.setText("");
        txtLastName.setText("");
        txtFirstName.setText("");
        txtUserName.setText("");
        pfPasswordfield.setText("");
        pfConfirmPassword.setText("");
    }

    private void submitInfo() {
    String query = "INSERT INTO `table of records` (`Student Number`, `Last Name`, `First Name`, `Username`, `Password`) VALUES (?, ?, ?, ?, ?)";
    try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
         PreparedStatement pstmt = connection.prepareStatement(query)) {

        String StudentNumber = txtStudentNum.getText();
        String LastName = txtLastName.getText();
        String FirstName = txtFirstName.getText();
        String Username = txtUserName.getText();
        char[] Password = pfPasswordfield.getPassword();

        if (!Arrays.equals(Password, pfConfirmPassword.getPassword())) {
            JOptionPane.showMessageDialog(this, "Passwords do not match!", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        pstmt.setString(1, StudentNumber);
        pstmt.setString(2, LastName);
        pstmt.setString(3, FirstName);
        pstmt.setString(4, Username);
        pstmt.setString(5, new String(Password)); 

        int rowsInserted = pstmt.executeUpdate();
        if (rowsInserted > 0) {
            JOptionPane.showMessageDialog(this, "Registered Successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
            dispose(); 
            new LogInPage().setVisible(true); 
        } else {
            JOptionPane.showMessageDialog(this, "Error creating account. Please try again.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    } catch (SQLException ex) {
        JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
    }
    }
    public static void main(String[]args){
        Create_Account cr = new Create_Account();
        cr.setVisible(true);
    }
}       
            
            
package com.mycompany.mavenproject1;


import java.awt.Color;
import java.awt.Font;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class LogInPage extends JFrame implements ActionListener {
    private final JLabel lblLogin, lblUsername, lblPassword;
    private final JTextField txtUsername;
    private final JPasswordField txtPassword;
    private final JButton btnSubmit;
    private final JButton btnBack;

    private static final String URL = "jdbc:mysql://localhost:3306/database ni reyshane";
    private static final String USER = "root";
    private static final String PASSWORD = "";

    public LogInPage() {
        setTitle("Log In");
        setSize(600,600);
        setLocationRelativeTo(null);
        setLayout(null);
        setResizable(false);
        setVisible(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        getContentPane().setBackground(new Color(247, 224, 117));


        JPanel panel = new JPanel();
        getContentPane().add(panel);
        
        lblLogin = new JLabel("LOG IN", SwingConstants.CENTER);
        lblLogin.setFont(new Font("Helvetica", Font.BOLD, 30));
        lblLogin.setBounds(50, 75, 500, 50);
        lblLogin.setVisible(true);
        add(lblLogin);

        lblUsername = new JLabel("USERNAME:");
        lblUsername.setBounds(75, 200, 100, 30);
        lblUsername.setFont(new Font("Helvetica", Font.BOLD, 12));
        add(lblUsername);
        
        txtUsername = new JTextField();
        txtUsername.setBounds(150, 200, 300, 30);
        txtUsername.setFont(new Font("Helvetica", Font.PLAIN, 12));
        add(txtUsername);
        
        lblPassword = new JLabel("PASSWORD:");
        lblPassword.setBounds(75,250, 100, 30);
        lblPassword.setFont(new Font("Helvetica", Font.BOLD, 12));
        lblPassword.setVisible(true);
        add(lblPassword);
        
        txtPassword = new JPasswordField();
        txtPassword.setBounds(150, 250, 300, 30);
        txtPassword.setFont(new Font("Helvetica", Font.PLAIN, 12));
        add(txtPassword);
        
        btnSubmit = new JButton("SUBMIT");
        btnSubmit.setBounds(175, 350, 100, 30);
        btnSubmit.setFont(new Font("Helvetica", Font.BOLD,15));
        add(btnSubmit);
        btnSubmit.addActionListener(this);
        
        btnBack = new JButton("BACK");
        btnBack.setBounds(300, 350, 100, 30);
        btnBack.setFont(new Font("Helvetica", Font.BOLD,15));
        add(btnBack);
        btnBack.addActionListener(this);

    }

    @Override
    public void actionPerformed(ActionEvent e) {
    if (e.getSource() == btnSubmit) {
        String username = txtUsername.getText().trim();
        String password = new String(txtPassword.getPassword()).trim();

        try {
            if (validateUser(username, password)) {
                JOptionPane.showMessageDialog(this, "Login successful", "Success", JOptionPane.INFORMATION_MESSAGE);
                dispose();
                new Records().setVisible(true);
            } else {
                JOptionPane.showMessageDialog(this, "Invalid credentials", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } catch (SQLException ex) {
            ex.printStackTrace(); 
            JOptionPane.showMessageDialog(this, "Login failed", "Error", JOptionPane.ERROR_MESSAGE);
        }
    } else if (e.getSource() == btnBack) {
        dispose();
        Registration re = new Registration();
        re.setVisible(true);
    }
}

    private boolean validateUser(String username, String password) throws SQLException {
    String query = "SELECT * FROM `table of records` WHERE `Username` = ? AND `Password` = ?";
    try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
         PreparedStatement pstmt = conn.prepareStatement(query)) {
        pstmt.setString(1, username);
        pstmt.setString(2, password);
        ResultSet rs = pstmt.executeQuery();
        return rs.next();
    }
}


    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            LogInPage loginPage = new LogInPage();
            loginPage.setVisible(true);
        });
    }
}

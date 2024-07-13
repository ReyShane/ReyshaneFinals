/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.mavenproject1;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 *
 * @author admin
 */
public class Registration extends JFrame implements ActionListener{
   
    private final JLabel lblRegister;
    private final JButton btnCreateAcc, btnLogIn;
    

    
    Registration(){
        setTitle("Registration");
        setSize(600,600);
        setLocationRelativeTo(null);
        setLayout(null);
        setResizable(false);
        setVisible(true);
        getContentPane().setBackground(new Color(247, 224, 117));
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        lblRegister = new JLabel("REGISTER",SwingConstants.CENTER);
        lblRegister.setBounds(145,100,300,40);
        lblRegister.setFont(new Font("Roboto",Font.BOLD,30));
        add(lblRegister);
        
        btnCreateAcc = new JButton("SIGN UP");
        btnCreateAcc.setBounds(145,250,300,40);
        btnCreateAcc.setFont(new Font("Roboto",Font.BOLD,20));
        add(btnCreateAcc);
        btnCreateAcc.addActionListener(this);
        
        btnLogIn = new JButton("LOG IN");
        btnLogIn.setBounds(145, 300, 300, 40);
        btnLogIn.setFont(new Font("Roboto",Font.BOLD,20));
        add(btnLogIn);
        btnLogIn.addActionListener(this);
      
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == btnCreateAcc) {
            new Create_Account().setVisible(true);
            dispose();            
        }else if (e.getSource() == btnLogIn){
            new LogInPage().setVisible(true);
    }
    }
     
       
    public static void main(String[]args){
        Registration re = new Registration();
        re.setVisible(true);
     
        
    }

    }


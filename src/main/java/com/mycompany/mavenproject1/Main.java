
package com.mycompany.mavenproject1;


import java.awt.EventQueue;


/**
 *
 * @author admin
 */
public class Main {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable(){
            @Override
            public void run(){
                Registration re = new Registration();
                re.setVisible(true);
            }
        });

    }
    
}

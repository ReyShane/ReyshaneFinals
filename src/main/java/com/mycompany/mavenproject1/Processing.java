package com.mycompany.mavenproject1;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Processing extends JFrame {
    private final ProgressBar progressBar;

    public Processing() {
        setTitle("Progress");
        setSize(400, 150);
        setResizable(false);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());
        getContentPane().setBackground(new Color(247, 224, 117));


        progressBar = new ProgressBar(0, 100);
        progressBar.setStringPainted(true);
        add(progressBar, BorderLayout.CENTER);

        startProgress();
    }

    private void startProgress() {
        progressBar.setValue(0);
        Timer timer = new Timer(50, new ActionListener() {
            int progress = 0;

            @Override
            public void actionPerformed(ActionEvent e) {
                progress++;
                progressBar.setValue(progress);

                if (progress < 50) {
                    progressBar.setCustomText("Please wait a moment...");
                } else if (progress < 90) {
                    progressBar.setCustomText("Almost done!");
                } else if (progress == 100) {
                    progressBar.setCustomText("Finish");
                    ((Timer) e.getSource()).stop();
                    dispose();
                    new LogInPage().setVisible(true);
                }
            }
        });
        timer.start();
    }

    class ProgressBar extends JProgressBar {
        private String customText;

        public ProgressBar(int min, int max) {
            super(min, max);
            this.customText = "";
        }

        public void setCustomText(String text) {
            this.customText = text;
            repaint(); 
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            FontMetrics fm = g.getFontMetrics();
            Rectangle bounds = getBounds();
            int stringWidth = fm.stringWidth(customText);
            int x = (bounds.width - stringWidth) / 2;
            int y = (bounds.height - fm.getHeight()) / 2 + fm.getAscent();
            g.drawString(customText, x, y);
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                Processing frame = new Processing();
                frame.setVisible(true);
            }
        });
    }
}

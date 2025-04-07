package main.UI;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Image;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

public class LoseGameFrame extends JFrame {
    Image rokueLikeImage;
    public LoseGameFrame() {
        super("YOU LOST");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1450, 864);
        setResizable(false);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());
        this.getContentPane().setBackground(new Color(60, 45, 55));

        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        mainPanel.setOpaque(false);

        JLabel textLabel = new JLabel("You lost, try again!", SwingConstants.CENTER);
        textLabel.setFont(new Font("Serif", Font.BOLD, 36));
        textLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        textLabel.setForeground(Color.WHITE);

        JLabel rokueLikeLabel;
        try {
            rokueLikeImage = ImageIO.read(new File("res/map/rokue-like.jpeg"))
                    .getScaledInstance(600, 400, Image.SCALE_SMOOTH);
            rokueLikeLabel = new JLabel(new ImageIcon(rokueLikeImage));
        } catch (IOException e) {
            System.err.println("Error: Image file not found!");
            rokueLikeLabel = new JLabel("Image not available.");
        }
        rokueLikeLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        JPanel buttonPanel = new JPanel();
        buttonPanel.setOpaque(false);

        JButton playAgainButton = new JButton("Play Again");
        playAgainButton.setFont(new Font("Arial", Font.BOLD, 16));
        playAgainButton.setForeground(new Color(105, 111, 150));
        playAgainButton.setBackground(new Color(80, 150, 80));
        playAgainButton.setFocusPainted(false);
        playAgainButton.setPreferredSize(new Dimension(150, 60));
        playAgainButton.addActionListener(e -> {
            this.dispose();
            new MainMenuFrame(); // Replace with the actual frame to restart the game
        });

        JButton exitButton = new JButton("Exit");
        exitButton.setFont(new Font("Arial", Font.BOLD, 16));
        exitButton.setForeground(new Color(105, 111, 150));
        exitButton.setBackground(new Color(150, 50, 50));
        exitButton.setFocusPainted(false);
        exitButton.setPreferredSize(new Dimension(150, 60));
        exitButton.addActionListener(e -> System.exit(0));

        buttonPanel.add(playAgainButton);
        buttonPanel.add(Box.createHorizontalStrut(20));
        buttonPanel.add(exitButton);

        mainPanel.add(Box.createVerticalStrut(100));
        mainPanel.add(textLabel);
        mainPanel.add(Box.createVerticalStrut(30));
        mainPanel.add(rokueLikeLabel);
        mainPanel.add(Box.createVerticalStrut(50));
        mainPanel.add(buttonPanel);

        this.add(mainPanel, BorderLayout.CENTER);
        this.setVisible(true);
    }

}

package main.UI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

public class MainMenuFrame extends JFrame implements ActionListener {
    final int screenWidth = 1200;
    final int screenHeight = 864;
    JPanel mainMenuPanel;
    JPanel centerPanel;
    JPanel bottomPanel;
    JPanel imagePanel;
    JLabel imageLabel;
    JButton startButton;
    JButton loadGameButton;
    JButton howToPlayButton;
    JButton exitButton;
    public MainMenuFrame () {
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setResizable(false);
        this.setTitle("RoKue - Main Menu");
        this.setSize(screenWidth, screenHeight);
        this.setLayout(new BorderLayout());

        // Main.Main panel with dark background
        mainMenuPanel = new JPanel(new BorderLayout());
        mainMenuPanel.setBackground(new Color(30, 30, 40));

        // CENTER PANEL: Image and "Start Game" button
        centerPanel = new JPanel();
        centerPanel.setOpaque(false);
        centerPanel.setLayout(new BoxLayout(centerPanel, BoxLayout.Y_AXIS));

        imagePanel = new JPanel();
        imagePanel.setOpaque(false);
        imagePanel.setLayout(new BoxLayout(imagePanel, BoxLayout.Y_AXIS));

        imageLabel = new JLabel();
        ImageIcon imageIcon = new ImageIcon(getClass().getResource("/map/rokue-like.jpeg"));
        Image image = imageIcon.getImage();
        Image resizedImage = image.getScaledInstance(700, 550, Image.SCALE_SMOOTH);
        ImageIcon resizedIcon = new ImageIcon(resizedImage);

        imageLabel.setIcon(resizedIcon);
        imageLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Start Game button
        startButton = createStyledButton("Start New Game", 300, 80);
        startButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        startButton.addActionListener(this);

        loadGameButton = createStyledButton("Load Game", 300, 80);
        loadGameButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        loadGameButton.addActionListener(this);

        exitButton = createStyledButton("Exit", 300, 80);
        exitButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        exitButton.addActionListener(this);

        imagePanel.add(Box.createVerticalStrut(20));
        imagePanel.add(imageLabel);
        centerPanel.add(imagePanel);
        centerPanel.add(Box.createVerticalStrut(20));
        centerPanel.add(startButton);
        centerPanel.add(Box.createVerticalStrut(20));
        centerPanel.add(loadGameButton);
        centerPanel.add(Box.createVerticalStrut(20));
        centerPanel.add(exitButton);

        // BOTTOM PANEL: "How to Play" button
        bottomPanel = new JPanel();
        bottomPanel.setOpaque(false);
        bottomPanel.setLayout(new FlowLayout(FlowLayout.RIGHT, 20, 20));

        howToPlayButton = createStyledButton("Help", 200, 60);
        howToPlayButton.addActionListener(this);
        bottomPanel.add(howToPlayButton);

        // Add panels to main menu
        mainMenuPanel.add(centerPanel, BorderLayout.CENTER);
        mainMenuPanel.add(bottomPanel, BorderLayout.SOUTH);

        this.add(mainMenuPanel);
        this.setLocationRelativeTo(null);
        this.setVisible(true);
    }
    @Override
    public void actionPerformed(ActionEvent actionEvent) {
        if (actionEvent.getSource() == startButton) {
            String saveFileName = JOptionPane.showInputDialog(
                    this,
                    "Enter the name for the save file:",
                    "Save Game",
                    JOptionPane.PLAIN_MESSAGE
            );
            boolean saveFileExists = searchForSaveFile(saveFileName);
            if (!saveFileExists) {
                if (saveFileName != null && !saveFileName.trim().isEmpty()) {
                    // User entered a valid save file name
                    this.dispose();
                    new BuildHallFrame(0, saveFileName);
                }
            } else {
                JOptionPane.showMessageDialog(
                        this,
                        "A save file with the same name already exists. Please enter a different name.",
                        "Error",
                        JOptionPane.ERROR_MESSAGE
                );
            }
        } else if (actionEvent.getSource() == loadGameButton) {
            this.dispose();
            new LoadGameFrame(this);
        } else if (actionEvent.getSource() == howToPlayButton) {
            new HelpFrame(this);
        } else if (actionEvent.getSource() == exitButton) {
            System.exit(0);
        }
    }
    private static JButton createStyledButton(String text, int width, int height) {
        JButton button = new JButton(text);
        button.setFont(new Font("Courier New", Font.BOLD, 24));
        button.setForeground(Color.WHITE);
        button.setBackground(new Color(65, 65, 90));
        button.setBorder(BorderFactory.createLineBorder(new Color(45, 45, 60), 4));
        button.setFocusPainted(false);
        button.setOpaque(true);
        button.setPreferredSize(new Dimension(width, height));
        return button;
    }

    private boolean searchForSaveFile(String saveFileName) {
        File saveFile = new File("res/saveFiles/" + saveFileName + ".dat");
        return saveFile.exists();
    }
}
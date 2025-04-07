package main.UI;

import main.Domain.Data.SaveLoad;
import main.Domain.Game;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.io.File;

public class LoadGameFrame extends JFrame implements ActionListener {
    final int screenWidth = 1200;
    final int screenHeight = 864;
    JPanel loadGamePanel;
    JPanel topPanel;
    JPanel centerPanel;
    JPanel bottomPanel;
    JLabel titleLabel;
    JButton backButton;
    ArrayList<JButton> saveButtons;
    ArrayList<JButton> deleteButtons;
    JScrollPane scrollPane;
    MainMenuFrame parentFrame;

    public LoadGameFrame(MainMenuFrame parentFrame) {
        this.parentFrame = parentFrame;
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setResizable(false);
        this.setTitle("RoKue - Load Game");
        this.setSize(screenWidth, screenHeight);
        this.setLayout(new BorderLayout());

        // Main.Main panel with dark background
        loadGamePanel = new JPanel(new BorderLayout());
        loadGamePanel.setBackground(new Color(30, 30, 40));

        // TOP PANEL: label
        topPanel = new JPanel();
        topPanel.setOpaque(false);

        titleLabel = new JLabel("Load Game");
        titleLabel.setFont(new Font("Courier New", Font.BOLD, 48));
        titleLabel.setForeground(Color.WHITE);
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        titleLabel.setLayout(new BoxLayout(titleLabel, BoxLayout.Y_AXIS));

        topPanel.add(Box.createVerticalStrut(50));
        topPanel.add(titleLabel);

        // CENTER PANEL: Title
        centerPanel = new JPanel();
        centerPanel.setOpaque(false);
        centerPanel.setLayout(new BoxLayout(centerPanel, BoxLayout.Y_AXIS));

        centerPanel.add(Box.createVerticalStrut(50));

        // Load save file names and create buttons
        saveButtons = new ArrayList<>();
        deleteButtons = new ArrayList<>();
        String saveDirectory = "res/saveFiles/";
        File dir = new File(saveDirectory);
        if (dir.exists() && dir.isDirectory()) {
            if (dir.listFiles().length == 0) {
                JLabel noSaveLabel = new JLabel("No save files found.");
                noSaveLabel.setFont(new Font("Courier New", Font.PLAIN, 24));
                noSaveLabel.setForeground(Color.LIGHT_GRAY);
                noSaveLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
                centerPanel.add(noSaveLabel);
            } else {
                for (File file : dir.listFiles()) {
                    JPanel buttonPanel = new JPanel();
                    buttonPanel.setOpaque(false);
                    JButton saveButton = createStyledButton(file.getName(), 300, 50);
                    saveButton.setAlignmentX(Component.CENTER_ALIGNMENT);
                    saveButton.addActionListener(this);
                    JButton deleteButton = createStyledButton("Delete save", 200, 50);
                    deleteButton.setAlignmentX(Component.CENTER_ALIGNMENT);
                    deleteButton.addActionListener(this);
                    saveButtons.add(saveButton);
                    deleteButtons.add(deleteButton);
                    buttonPanel.add(saveButton);
                    buttonPanel.add(deleteButton);
                    centerPanel.add(buttonPanel);
                    centerPanel.add(Box.createVerticalStrut(10));
                }
            }

        }

        scrollPane = new JScrollPane(centerPanel);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPane.setOpaque(false);
        scrollPane.getViewport().setOpaque(false);
        scrollPane.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        // BOTTOM PANEL: Back button
        bottomPanel = new JPanel();
        bottomPanel.setOpaque(false);
        bottomPanel.setLayout(new FlowLayout(FlowLayout.RIGHT, 20, 20));

        backButton = createStyledButton("Back", 200, 60);
        backButton.addActionListener(this);
        bottomPanel.add(backButton);

        // Add panels to main frame
        loadGamePanel.add(topPanel, BorderLayout.NORTH);
        loadGamePanel.add(scrollPane, BorderLayout.CENTER);
        loadGamePanel.add(bottomPanel, BorderLayout.SOUTH);

        this.add(loadGamePanel);
        this.setLocationRelativeTo(null);
        this.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent actionEvent) {
        if (actionEvent.getSource() == backButton) {
            this.dispose();
            new MainMenuFrame(); // Go back to Main.Main Menu
        } else {
            // Handle save file button clicks
            for (int i = 0; i < saveButtons.size(); i++) {
                JButton saveButton = saveButtons.get(i);
                if (actionEvent.getSource() == saveButton) {
                    String saveFileName = saveButton.getText();
                    // Show a confirm dialog with Yes/No options
                    int choice = JOptionPane.showConfirmDialog(
                            this,
                            "Do you want to load the save file: " + saveFileName + "?",
                            "Load Game",
                            JOptionPane.YES_NO_OPTION,
                            JOptionPane.QUESTION_MESSAGE
                    );

                    if (choice == JOptionPane.YES_OPTION) {
                        this.dispose();
                        parentFrame.dispose();
                        Game loadedGame = SaveLoad.loadGame(saveFileName);
                        new PlayModeFrame(0, saveFileName.substring(0, saveFileName.length()-4), loadedGame);
                    }
                }
                // Handle delete file button clicks
                JButton deleteButton = deleteButtons.get(i);
                if (actionEvent.getSource() == deleteButton) {
                    String saveFileName = saveButtons.get(i).getText();
                    // Show a confirm dialog for deletion
                    int choice = JOptionPane.showConfirmDialog(
                            this,
                            "Are you sure you want to delete the save file: " + saveFileName + "?",
                            "Delete Save File",
                            JOptionPane.YES_NO_OPTION,
                            JOptionPane.WARNING_MESSAGE
                    );

                    if (choice == JOptionPane.YES_OPTION) {
                        // Attempt to delete the file
                        String filePath = "res/saveFiles/" + saveFileName;
                        File file = new File(filePath);
                        if (file.exists() && file.delete()) {
                            // Refresh the Main.Main.Domain.UI
                            this.dispose();
                            new LoadGameFrame(parentFrame);
                        }
                    }
                }
            }
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
}

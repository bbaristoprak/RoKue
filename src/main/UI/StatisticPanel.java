package main.UI;

import main.Domain.Game;
import main.Domain.GameListener;

import javax.swing.JPanel;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.BorderLayout;
import java.awt.Image;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.BoxLayout;
import javax.swing.Box;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import java.awt.Component;
import java.awt.Font;

public class StatisticPanel extends JPanel implements GameListener {
    public JButton buttonQuit;
    public JButton buttonPause;
    public JButton buttonResume;

    JLabel topText;
    JLabel timeText;
    JLabel countDown;
    JLabel inventoryText;

    JLabel heart1Label;
    JLabel heart2Label;
    JLabel heart3Label;
    JLabel heart4Label;

    JLabel cloakOfProtectionLabel;
    JLabel revealGemLabel;
    JLabel luringGemLabel;

    JLabel numberCloakOfProtection;
    JLabel numberReveal;
    JLabel numberLuringGem;

    Image heartImage;
    Image cloakOfProtectionImage;
    Image revealImage;
    Image luringGemImage;
    ImageIcon heartIcon;
    Game game;

    public StatisticPanel(Game game){
        this.game = game;
        this.setLayout(new BorderLayout());
        this.setPreferredSize(new Dimension(300,864));
        this.setBackground(new Color(60, 45, 55));

        try {
            cloakOfProtectionImage = ImageIO.read(getClass().getResource("/enchantment/cloak-of-protection.png")).getScaledInstance(40, 40, java.awt.Image.SCALE_SMOOTH);
            revealImage = ImageIO.read(getClass().getResource("/enchantment/reveal.png")).getScaledInstance(40, 40, java.awt.Image.SCALE_SMOOTH);
            heartImage = ImageIO.read(getClass().getResource("/map/heart.png")).getScaledInstance(50, 50, java.awt.Image.SCALE_SMOOTH);
            luringGemImage = ImageIO.read(getClass().getResource("/enchantment/luring-gem.png")).getScaledInstance(40, 40, java.awt.Image.SCALE_SMOOTH);
        } catch (IOException e) {
            e.getStackTrace();
        }

        JPanel topContainer = new JPanel();
        topContainer.setLayout(new BoxLayout(topContainer, BoxLayout.Y_AXIS));
        topContainer.setOpaque(false);

        topText = new JLabel("Hall of Water", SwingConstants.CENTER);
        topText.setAlignmentX(Component.CENTER_ALIGNMENT);
        topText.setForeground(Color.WHITE);
        topText.setFont(new Font("Arial", Font.BOLD, 18));

        buttonQuit = new JButton("X");

        buttonQuit.setFont(new Font("Arial", Font.BOLD, 14));
        buttonQuit.setForeground(new Color(164, 167, 192));
        buttonQuit.setPreferredSize(new Dimension(30, 30));
        buttonQuit.setVisible(true);


        buttonPause = new JButton("II");

        buttonPause.setFont(new Font("Arial", Font.BOLD, 14));
        buttonPause.setForeground(new Color(164, 167, 192));
        buttonPause.setPreferredSize(new Dimension(30, 30));
        buttonPause.setVisible(true);

        buttonResume = new JButton("►");

        buttonResume.setFont(new Font("Arial", Font.BOLD, 14));
        buttonResume.setForeground(new Color(164, 167, 192));
        buttonResume.setPreferredSize(new Dimension(30, 30));
        buttonResume.setVisible(true);

        JPanel topPanel = new JPanel();
        topPanel.setLayout(new BoxLayout(topPanel, BoxLayout.X_AXIS));
        topPanel.setOpaque(false);

        topPanel.add(buttonQuit);
        topPanel.add(Box.createHorizontalStrut(10));
        topPanel.add(buttonPause);
        topPanel.add(Box.createHorizontalStrut(10));
        topPanel.add(buttonResume);

        topContainer.add(Box.createVerticalStrut(250));
        topContainer.add(topText);
        topContainer.add(Box.createVerticalStrut(20));
        topContainer.add(topPanel);


        JPanel middleContainer = new JPanel();
        middleContainer.setLayout(new BoxLayout(middleContainer, BoxLayout.Y_AXIS));
        middleContainer.setOpaque(false);

        timeText = new JLabel("TIME", SwingConstants.CENTER);
        timeText.setFont(new Font("Arial", Font.BOLD, 14));
        timeText.setAlignmentX(Component.CENTER_ALIGNMENT);
        timeText.setForeground(Color.WHITE);
        timeText.setFont(new Font("Arial", Font.BOLD, 18));

        countDown = new JLabel(String.valueOf(game.remainingTime), SwingConstants.CENTER);
        countDown.setAlignmentX(Component.CENTER_ALIGNMENT);
        countDown.setForeground(Color.WHITE);
        countDown.setFont(new Font("Arial", Font.BOLD, 18));

        JPanel heartPanel = new JPanel();
        heartPanel.setLayout(new BoxLayout(heartPanel, BoxLayout.X_AXIS));
        heartPanel.setOpaque(false);

        heartIcon = new ImageIcon(heartImage);

        heart1Label = new JLabel(heartIcon);
        heart2Label = new JLabel(heartIcon);
        heart3Label = new JLabel(heartIcon);
        heart4Label = new JLabel(heartIcon);

        heartPanel.add(heart1Label);
        heartPanel.add(heart2Label);
        heartPanel.add(heart3Label);
        heartPanel.add(heart4Label);
        setHeartIcons(game.player.lives);

        inventoryText = new JLabel("INVENTORY", SwingConstants.CENTER);
        inventoryText.setAlignmentX(Component.CENTER_ALIGNMENT);
        inventoryText.setForeground(Color.WHITE);
        inventoryText.setFont(new Font("Arial", Font.BOLD, 18));

        JPanel inventory1Panel = new JPanel();

        inventory1Panel.setLayout(new BoxLayout(inventory1Panel, BoxLayout.X_AXIS));
        inventory1Panel.setOpaque(false);
        ImageIcon cloakOfProtectionIcon = new ImageIcon(cloakOfProtectionImage);
        cloakOfProtectionLabel = new JLabel(cloakOfProtectionIcon);


        ImageIcon revealIcon = new ImageIcon(revealImage);
        revealGemLabel = new JLabel(revealIcon);

        ImageIcon luringGemIcon = new ImageIcon(luringGemImage);
        luringGemLabel = new JLabel(luringGemIcon);

        inventory1Panel.add(cloakOfProtectionLabel);
        inventory1Panel.add(revealGemLabel);
        inventory1Panel.add(luringGemLabel);

        JPanel inventory2Panel = new JPanel();
        inventory2Panel.setLayout(new BoxLayout(inventory2Panel, BoxLayout.X_AXIS));
        inventory2Panel.setOpaque(false);

        numberCloakOfProtection = new JLabel(String.valueOf(game.player.bag.numCloakOfProtection), SwingConstants.CENTER);
        numberCloakOfProtection.setAlignmentX(Component.CENTER_ALIGNMENT);
        numberCloakOfProtection.setForeground(Color.WHITE);
        numberCloakOfProtection.setFont(new Font("Arial", Font.BOLD, 18));
        numberCloakOfProtection.setAlignmentY(Component.TOP_ALIGNMENT);

        numberReveal = new JLabel(String.valueOf(game.player.bag.numReveal), SwingConstants.CENTER);
        numberReveal.setAlignmentX(Component.CENTER_ALIGNMENT);
        numberReveal.setForeground(Color.WHITE);
        numberReveal.setFont(new Font("Arial", Font.BOLD, 18));
        numberReveal.setAlignmentY(Component.TOP_ALIGNMENT);

        numberLuringGem = new JLabel(String.valueOf(game.player.bag.numLuringGem), SwingConstants.CENTER);
        numberLuringGem.setAlignmentX(Component.CENTER_ALIGNMENT);
        numberLuringGem.setForeground(Color.WHITE);
        numberLuringGem.setFont(new Font("Arial", Font.BOLD, 18));
        numberLuringGem.setAlignmentY(Component.TOP_ALIGNMENT);


        inventory2Panel.add(numberCloakOfProtection);
        inventory2Panel.add(Box.createHorizontalStrut(33));
        inventory2Panel.add(numberReveal);
        inventory2Panel.add(Box.createHorizontalStrut(37));
        inventory2Panel.add(numberLuringGem);


        middleContainer.add(Box.createVerticalStrut(30));
        middleContainer.add(timeText);
        middleContainer.add(Box.createVerticalStrut(10));
        middleContainer.add(countDown);
        middleContainer.add(heartPanel);
        middleContainer.add(Box.createVerticalStrut(30));
        middleContainer.add(inventoryText);
        middleContainer.add(Box.createVerticalStrut(20));
        middleContainer.add(inventory1Panel);
        middleContainer.add(Box.createVerticalStrut(10));
        middleContainer.add(inventory2Panel);


        this.add(topContainer, BorderLayout.NORTH);
        this.add(middleContainer, BorderLayout.CENTER);
        game.addGameListener(this);
    }

    @Override
    public void onTimePercentageGroupUpdate(int num) {

    }

    @Override
    public void onRuneFound() {}

    @Override
    public void runeRelocated() {
        JLabel messageLabel = new JLabel("Rune Relocated!", SwingConstants.CENTER);
        messageLabel.setForeground(Color.YELLOW);
        messageLabel.setFont(new Font("Arial", Font.BOLD, 18));
        messageLabel.setOpaque(false);
        this.add(messageLabel, BorderLayout.SOUTH);
        this.revalidate();
        this.repaint();

        // Create a timer to remove the message after 1.5 second
        new javax.swing.Timer(1500, e -> {
            this.remove(messageLabel);
            this.revalidate();
            this.repaint();
        }).start();
    }
    @Override
    public void timeChanged(int newTime) {
        countDown.setText(String.valueOf(newTime));
    }

    @Override
    public void livesChanged(int newLives) {
        setHeartIcons(newLives);
    }

    @Override
    public void enchantmentAdded(String enchantmentName) {
        switch (enchantmentName) {
            case "Cloak Of Protection":
                int currentCloakOfProtection = Integer.parseInt(numberCloakOfProtection.getText());
                numberCloakOfProtection.setText(String.valueOf(currentCloakOfProtection + 1));
                break;
            case "Reveal Gem":
                int currentReveal = Integer.parseInt(numberReveal.getText());
                numberReveal.setText(String.valueOf(currentReveal + 1));
                break;
            case "Luring Gem":
                int currentLuringGem = Integer.parseInt(numberLuringGem.getText());
                numberLuringGem.setText(String.valueOf(currentLuringGem + 1));
                break;
        }
        JLabel messageLabel = new JLabel(enchantmentName + " added to bag!", SwingConstants.CENTER);
        messageLabel.setForeground(Color.YELLOW);
        messageLabel.setFont(new Font("Arial", Font.BOLD, 18));
        messageLabel.setOpaque(false);
        this.add(messageLabel, BorderLayout.SOUTH);
        this.revalidate();
        this.repaint();

        // Create a timer to remove the message after 1.5 second
        new javax.swing.Timer(1500, e -> {
            this.remove(messageLabel);
            this.revalidate();
            this.repaint();
        }).start();
    }

    @Override
    public void enchantmentRemoved(String enchantmentName) {
        switch (enchantmentName) {
            case "Cloak Of Protection":
                int currentCloakOfProtection = Integer.parseInt(numberCloakOfProtection.getText());
                numberCloakOfProtection.setText(String.valueOf(currentCloakOfProtection - 1));
                break;
            case "Reveal Gem":
                int currentReveal = Integer.parseInt(numberReveal.getText());
                numberReveal.setText(String.valueOf(currentReveal - 1));
                break;
            case "Luring Gem":
                int currentLuringGem = Integer.parseInt(numberLuringGem.getText());
                numberLuringGem.setText(String.valueOf(currentLuringGem - 1));
                break;
        }
        this.revalidate();
        this.repaint();
    }

    public void setHeartIcons(int lives) {
        switch (lives) {
            case 4:
                heart4Label.setIcon(heartIcon);
                heart3Label.setIcon(heartIcon);
                heart2Label.setIcon(heartIcon);
                heart1Label.setIcon(heartIcon);
                break;
            case 3:
                heart4Label.setIcon(null);
                heart3Label.setIcon(heartIcon);
                heart2Label.setIcon(heartIcon);
                heart1Label.setIcon(heartIcon);
                break;
            case 2:
                heart4Label.setIcon(null);
                heart3Label.setIcon(null);
                heart2Label.setIcon(heartIcon);
                heart1Label.setIcon(heartIcon);
                break;
            case 1:
                heart4Label.setIcon(null);
                heart3Label.setIcon(null);
                heart2Label.setIcon(null);
                heart1Label.setIcon(heartIcon);
                break;
            case 0:
                heart4Label.setIcon(null);
                heart3Label.setIcon(null);
                heart2Label.setIcon(null);
                heart1Label.setIcon(null);
                break;
        }
    }

}

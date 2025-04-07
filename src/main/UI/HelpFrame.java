package main.UI;

import javax.swing.*;
import java.awt.*;

public class HelpFrame extends JFrame {
    private final JFrame parentFrame;

    public HelpFrame(JFrame parentFrame) {
        this.parentFrame = parentFrame;

        // Set up frame properties
        this.setTitle("Help - RoKue");
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.setResizable(false);
        this.setSize(600, 400);
        this.setLayout(new BorderLayout());

        // Main.Main panel with dark background
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(new Color(30, 30, 40)); // Same background as MainMenuFrame

        // Title label
        JLabel titleLabel = new JLabel("How to Play", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Courier New", Font.BOLD, 26));
        titleLabel.setForeground(Color.WHITE);
        titleLabel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Help text
        JTextArea helpText = new JTextArea();
        helpText.setText("""
                Welcome to RoKue!
                
                **Game Overview:**
                - RoKue is an adventurous dungeon-exploration game where you play as a hero trying to find mystical runes.
                - Progress through the dungeon's halls (Earth, Air, Water, Fire) by finding runes and unlocking the exit doors.
                - Time is your biggest challenge—find the runes quickly before the timer runs out.

                **How to Play:**
                - Use the arrow keys to move your hero around the grid-based dungeon.
                - Click on objects near your hero to search for the rune hidden underneath.
                - Avoid dangerous monsters as you explore.
                - Collect enchantments to help you escape monsters or extend your time.
                - Monitor your remaining lives, time, and the contents of your bag on the screen.

                **Monsters:**
                - **Archer Monster:**
                  - Appears in random locations in the hall every 8 seconds.
                  - Shoots arrows if the hero is within 4 squares, causing the hero to lose a life.
                  - Protected from its arrows when wearing a *Cloak of Protection*.
                - **Fighter Monster:**
                  - Roams randomly and stabs the hero if adjacent.
                  - Can be distracted with a *Luring Gem* by throwing it in a direction.
                  - Not affected by the *Cloak of Protection*.
                - **Wizard Monster:**
                  - Teleports the rune to a random location every 5 seconds.
                  - Remains stationary and does not attack the hero.

                **Enchantments:**
                - Enchantments appear every 12 seconds and disappear if not collected within 6 seconds.
                - **Extra Time:**
                  - Adds 5 seconds to your remaining time upon collection.
                - **Reveal:**
                  - Shows a 4x4 highlighted region containing the rune when activated (press 'R').
                  - Highlight lasts for 10 seconds.
                - **Cloak of Protection:**
                  - Renders the hero invisible to Archer Monsters for 20 seconds (press 'P' to activate).
                - **Luring Gem:**
                  - Distracts Fighter Monsters by throwing it in a direction (press 'A', 'W', 'S', or 'D' after 'B').
                - **Extra Life:**
                  - Increases your remaining lives by one upon collection.

                **Game Modes:**
                - **Build Mode:**
                  - Design each hall by placing objects on the grid.
                  - Minimum required objects per hall: 
                    - Earth: 6
                    - Air: 9
                    - Water: 13
                    - Fire: 17
                  - Time limit in each hall depends on the number of objects placed.
                - **Play Mode:**
                  - Explore the designed halls, find the rune, and avoid monsters.

                **Game Controls:**
                - **Arrow Keys**: Move the hero.
                - **Mouse Left Click**: Search objects and collect enchantments.
                - **R**: Use Reveal enchantment.
                - **P**: Use Cloak of Protection enchantment.
                - **B + A/W/S/D**: Use Luring Gem enchantment in a specific direction.
                - **Pause/Resume Button**: Pause or resume the game.
                - **Exit Button**: Exit to the main menu.

                **Game Tips:**
                - Search for enchantments often—they are crucial for survival.
                - Use the Reveal enchantment strategically in later halls to save time.
                - Plan your moves to avoid monsters and minimize risks.
                - Keep an eye on your bag and use stored enchantments wisely.

                Good luck, hero! The dungeon awaits your courage and skill!
                """);

        helpText.setFont(new Font("Courier New", Font.PLAIN, 16));
        helpText.setForeground(Color.WHITE);
        helpText.setBackground(new Color(30, 30, 40));
        helpText.setEditable(false);
        helpText.setLineWrap(true);
        helpText.setWrapStyleWord(true);
        helpText.setMargin(new Insets(10, 10, 10, 10));

        JScrollPane scrollPane = new JScrollPane(helpText);
        scrollPane.setBorder(BorderFactory.createEmptyBorder());

        // Back button
        JButton backButton = createStyledButton("Back", 200, 60);
        animateButton(backButton);
        backButton.addActionListener(e -> {
            this.dispose();
            parentFrame.setVisible(true); // Show MainMenuFrame
        });

        JPanel bottomPanel = new JPanel();
        bottomPanel.setOpaque(false);
        bottomPanel.setLayout(new FlowLayout(FlowLayout.LEFT, 20, 20));
        bottomPanel.add(backButton);

        // Add components to main panel
        mainPanel.add(titleLabel, BorderLayout.NORTH);
        mainPanel.add(scrollPane, BorderLayout.CENTER);
        mainPanel.add(bottomPanel, BorderLayout.SOUTH);

        // Add main panel to frame
        this.add(mainPanel);

        this.setLocationRelativeTo(null); // Center on screen
        this.setVisible(true);
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

    private static void animateButton(JButton button) {
        button.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseEntered(java.awt.event.MouseEvent e) {
                button.setBackground(new Color(85, 85, 120));
                button.setBorder(BorderFactory.createLineBorder(new Color(65, 65, 90), 4));
            }

            @Override
            public void mouseExited(java.awt.event.MouseEvent e) {
                button.setBackground(new Color(65, 65, 90));
                button.setBorder(BorderFactory.createLineBorder(new Color(45, 45, 60), 4));
            }

            @Override
            public void mousePressed(java.awt.event.MouseEvent e) {
                button.setBackground(new Color(45, 45, 70));
            }

            @Override
            public void mouseReleased(java.awt.event.MouseEvent e) {
                button.setBackground(new Color(85, 85, 120));
            }
        });
    }
}

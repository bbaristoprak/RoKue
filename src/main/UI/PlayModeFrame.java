package main.UI;

import main.Domain.Data.SaveLoad;
import main.Domain.Enchantment.Enchantment;
import main.Domain.Enchantment.LuringGem;
import main.Domain.Game;
import main.Domain.GameListener;
import main.Domain.HallObject.SuperObject;
import main.Domain.KeyHandler;
import main.Domain.Monster.Monster;
import main.Domain.PlayerObj.Player;
import main.Domain.Tile.TileHandler;
import main.Domain.WallHandler;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.IOException;
import java.lang.Object;
import java.util.Objects;
import java.util.Random;

public class PlayModeFrame extends JFrame implements ActionListener, GameListener {
    final int originalTileSize = 16; // 16 to 16 tile
    final int scale = 3;
    public final int tileSize = originalTileSize * scale;
    public final int maxScreenColumn = 25;
    public final int maxScreenRow = 18;
    public final int screenWidth = tileSize * maxScreenColumn; // 1200
    public final int screenHeight = tileSize * maxScreenRow; // 864
    public int hallCount;
    Image backgroundImage;
    TileHandler tileHandler;
    JPanel leftPanel;
    StatisticPanel rightPanel;
    Game game;
    String saveFileName;
    PlayModeFrame self;

    public PlayModeFrame(int hallCount, String saveFileName, Game savedGame) {
        self = this;
        this.saveFileName = saveFileName;
        this.game = savedGame;
        if (game == null){
            this.hallCount = hallCount;
            game = new Game(hallCount, false);
            game.setObjects(game.tileHandler.loadFromMapTile());
            game.spawnRune(game.objects);
            game.setTime();
            KeyHandler keyHandler = new KeyHandler(game);
            game.player = new Player(game, keyHandler, false);
            this.addKeyListener(keyHandler);
        } else {
            this.hallCount = game.hallCount;
            this.addKeyListener(game.player.keyHandler);
        }
        game.addGameListener(this);
        tileHandler = game.tileHandler;
        try {
            backgroundImage = ImageIO.read(Objects.requireNonNull(getClass().getResource("/map/background.jpeg")));
        } catch (IOException e) {
            e.printStackTrace();
        }

        this.setLayout(new BorderLayout());
        this.setTitle("RoKue - Play Mode");
        this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        this.setResizable(false);
        this.setSize(1450, 864);
        this.setLocationRelativeTo(null);
        this.setVisible(true);
        this.setFocusable(true);
        this.requestFocusInWindow();
        this.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                game.gameState = 0;
                int confirmed1 = JOptionPane.showConfirmDialog(
                        self,
                        "Are you sure you want to quit the game?",
                        "Exit Game Confirmation",
                        JOptionPane.YES_NO_OPTION
                );

                if (confirmed1 == JOptionPane.YES_OPTION) {
                    int confirmed2 = JOptionPane.showConfirmDialog(
                            self,
                            "Do you want to save the game?",
                            "Save Game Confirmation",
                            JOptionPane.YES_NO_OPTION
                    );
                    if (confirmed2 == JOptionPane.YES_OPTION) {
                        SaveLoad.saveGame(game, saveFileName);
                    }
                    System.exit(0);
                } else {
                    game.gameState = 1;
                }
            }
        });


        leftPanel = new JPanel(){
            public void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2 = (Graphics2D) g;

                if (backgroundImage != null) {
                    g2.drawImage(backgroundImage, 0, 0, screenWidth, screenHeight, null);
                }

                game.player.draw(g2);
                for (Monster obj : game.monsterList) {
                    if (obj != null) {
                        obj.draw(g2);
                    }
                }
                tileHandler.drawTile(g2);

                for (SuperObject obj : game.objects) {
                    if (obj != null) {
                        obj.draw(g2, tileSize);
                    }
                }
                for (Enchantment enchantment : game.enchantmentList){
                    if (enchantment != null){
                        enchantment.draw(g2);
                        System.out.println("drawn" + enchantment.toString());

                    }
                }
                if (game.revealActive) {
                    highlight(g2);
                }

                if (game.gemThrown) {
                    showThrownGem(g2);
                }

                if (game.gameState == 0 && game.player.lives != 0 && game.remainingTime > 0) {
                    g2.setFont(new Font("Arial", Font.BOLD, 64));
                    g2.setColor(new Color(255, 255, 255, 180));
                    g2.drawString("PAUSED", screenWidth / 2 - 150, screenHeight / 2);
                }
                g2.dispose();
            }
        };
        leftPanel.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                int mouseX = e.getX();
                int mouseY = e.getY();
                int tileX = mouseX / tileSize;
                int tileY = mouseY / tileSize;
                System.out.println(tileX + " " + tileY);
                game.clickEnchantment(tileX, tileY);
                game.searchForRune(tileX, tileY);
                leftPanel.repaint();
            }
        });

        leftPanel.setLayout(null);
        leftPanel.setPreferredSize(new Dimension(1200, 864));
        this.add(leftPanel, BorderLayout.CENTER);
        game.setPanel(leftPanel);

        rightPanel = new StatisticPanel(game);
        rightPanel.buttonQuit.addActionListener(this);
        rightPanel.buttonPause.addActionListener(this);
        rightPanel.buttonResume.addActionListener(this);
        this.add(rightPanel, BorderLayout.EAST);

        game.startGameThread();
    }

    public static String getHallFile(int hallCount) {
        return switch (hallCount) {
            case 0 -> "res/map/earth_hall.txt";
            case 1 -> "res/map/air_hall.txt";
            case 2 -> "res/map/water_hall.txt";
            case 3 -> "res/map/fire_hall.txt";
            default -> throw new IllegalArgumentException("Invalid Hall Number");
        };
    }

    @Override
    public void onTimePercentageGroupUpdate(int num) {

    }

    @Override
    public void onRuneFound() {
        game.gameState = 0;
        Icon runeIm = new ImageIcon("res/map/key1.png");
        final JOptionPane optionPane = new JOptionPane(
                "",
                JOptionPane.INFORMATION_MESSAGE,
                JOptionPane.DEFAULT_OPTION,
                runeIm);
        if (hallCount<3) {
            optionPane.setMessage("Congratulations! You have found the rune! Proceed to the next hall?");
        } else {
            optionPane.setMessage("Congratulations! You have found all of the runes! Go back to the main menu?");
        }
        final JDialog dialog = optionPane.createDialog(this, "RUNE FOUND");
        dialog.setVisible(true);

        Object selectedValue = optionPane.getValue();

        if (selectedValue != null && selectedValue.equals(JOptionPane.OK_OPTION)) {
            nextHall();
        } else {
            nextHall();
        }
    }

    @Override
    public void runeRelocated() {

    }

    @Override
    public void timeChanged(int newTime) {
        if (newTime < 0) {
            game.gameState = 0;
            Icon runeIm = new ImageIcon("res/map/key1.png");
            final JOptionPane optionPane = new JOptionPane(
                    "You lost! Click ok to return main menu.",
                    JOptionPane.INFORMATION_MESSAGE,
                    JOptionPane.DEFAULT_OPTION,
                    runeIm);

            final JDialog dialog = optionPane.createDialog(this, "YOU LOST!");
            dialog.setVisible(true);

            Object selectedValue = optionPane.getValue();

            if (selectedValue != null && selectedValue.equals(JOptionPane.OK_OPTION)) {
                this.dispose();
                WallHandler.getInstance().resetAllMaps();
                new MainMenuFrame();
            } else {
                this.dispose();
                WallHandler.getInstance().resetAllMaps();
                new MainMenuFrame();
            }
        }
    }

    @Override
    public void livesChanged(int newLives) {
        if (newLives <= 0) {
            game.gameState = 0;
            Icon runeIm = new ImageIcon("res/map/key1.png");
            final JOptionPane optionPane = new JOptionPane(
                    "You lost! Click ok to return main menu.",
                    JOptionPane.INFORMATION_MESSAGE,
                    JOptionPane.DEFAULT_OPTION,
                    runeIm);

            final JDialog dialog = optionPane.createDialog(this, "YOU LOST!");
            dialog.setVisible(true);

            Object selectedValue = optionPane.getValue();

            if (selectedValue != null && selectedValue.equals(JOptionPane.OK_OPTION)) {
                this.dispose();
                WallHandler.getInstance().resetAllMaps();
                new MainMenuFrame();
            } else {
                this.dispose();
                WallHandler.getInstance().resetAllMaps();
                new MainMenuFrame();
            }
        }
    }

    @Override
    public void enchantmentAdded(String enchantmentName) {

    }

    @Override
    public void enchantmentRemoved(String enchantmentName) {

    }

    public void nextHall() {
        if (hallCount < 3) {
            this.dispose();
            game.gameState = 0;
            new PlayModeFrame(hallCount+1, saveFileName, null);
        } else {
            this.dispose();
            new MainMenuFrame();
        }
    }

    private void highlight(Graphics g) {
        int rune_X = game.objectWithRune.getX();
        int rune_Y = game.objectWithRune.getY();
        g.setColor(Color.YELLOW);
        int highlightSize = 4 * tileSize;

        g.drawRect((rune_X + game.randomX) * tileSize,
                (rune_Y + game.randomY) * tileSize,
                highlightSize,
                highlightSize);
    }

    private void showThrownGem(Graphics2D g2) {
        if (game.gemX < 0 || game.gemX >= 1200 ||
                game.gemY < 0 || game.gemY >= 864) {
            game.gemThrown = false;
            return;
        }
        Image image;
        try {
            image = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/enchantment/luring-gem.png")));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        g2.drawImage(image, game.gemX, game.gemY, game.tileSize, game.tileSize, null);
        // Debug output
        System.out.println("Luring Gem thrown to: " + game.gemX + ", " + game.gemY);
        switch (game.gemThrownLoc) {
            case "W":
                game.gemY -= 10;
                break;
            case "A":
                game.gemX -= 10;
                break;
            case "S":
                game.gemY += 10;
                break;
            case "D":
                game.gemX += 10;
                break;
            default:
                System.out.println("Invalid gemThrownLoc: " + game.gemThrownLoc);
                game.gemThrown = false;
        }
    }


    @Override
    public void actionPerformed(ActionEvent actionEvent) {
        if (actionEvent.getSource() == rightPanel.buttonQuit) {
            game.gameState = 0;
            int confirmed2 = JOptionPane.showConfirmDialog(
                    self,
                    "Do you want to save the game?",
                    "Save Game Confirmation",
                    JOptionPane.YES_NO_OPTION
            );
            if (confirmed2 == JOptionPane.YES_OPTION) {
                SaveLoad.saveGame(game, saveFileName);
                WallHandler.getInstance().resetAllMaps();
                this.dispose();
                new MainMenuFrame();
            } else if (confirmed2 == JOptionPane.NO_OPTION) {
                this.dispose();
                WallHandler.getInstance().resetAllMaps();
                new MainMenuFrame();
            } else {
                game.gameState = 1;
            }

        } else if (actionEvent.getSource() == rightPanel.buttonPause) {
            game.gameState = 0;
        } else if (actionEvent.getSource() == rightPanel.buttonResume) {
            game.gameState = 1;
            this.requestFocusInWindow();
        }
    }
}
package main.UI;

import main.Domain.BuildHall;
import main.Domain.HallObject.SuperObject;
import main.Domain.PlayerObj.Player;
import main.Domain.Tile.TileHandler;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Arrays;
import java.util.Objects;


public class BuildHallFrame extends JFrame implements ActionListener {

    final int originalTileSize = 16; // 16 to 16 tile
    final int scale = 3;
    public final int tileSize = originalTileSize * scale;
    public final int maxScreenColumn = 25;
    public final int maxScreenRow = 18;
    public final int screenWidth = tileSize * maxScreenColumn; // 1344
    public final int screenHeight = tileSize * maxScreenRow; // 864
    TileHandler tileHandler = new TileHandler();
    BuildHall buildHall;
    BufferedImage backgroundImage;
    JPanel mapPanel;
    JButton finishButton;
    JButton nextButton;
    JButton randomButton;
    int hallCount;
    public final int[] obj_rest = {6,9,13,17};
    public final String[] pathList = {"res/map/earth_hall.txt" ,"res/map/air_hall.txt", "res/map/water_hall.txt", "res/map/fire_hall.txt"};
    String saveFileName;

    public BuildHallFrame(int hallCount, String saveFileName) {
        this.hallCount = hallCount;
        this.saveFileName = saveFileName;
        String name = "";
        switch (hallCount) {
            case 0 -> name = "Earth";
            case 1 -> name = "Air";
            case 2 -> name = "Water";
            case 3 -> name = "Fire";
        }
        this.setTitle("RoKue - Build Mode: " + name + "Hall");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setResizable(false);
        this.setLayout(new BorderLayout());
        tileHandler.loadMapIntoTileNum(pathList[hallCount]);


        mapPanel = new JPanel(){
            public void paintComponent(Graphics graphics) {
                super.paintComponent(graphics);
                Graphics2D graphics2D = (Graphics2D) graphics;
                if (backgroundImage != null) {
                    graphics2D.drawImage(backgroundImage, 0, 0, screenWidth, screenHeight, null);
                }
                tileHandler.drawTile(graphics2D);
                drawObjects(graphics2D);
                graphics2D.dispose();
            }
        };
        mapPanel.setLayout(null);
        mapPanel.setPreferredSize(new Dimension(1200, 864));
        mapPanel.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                int mouseX = e.getX();
                int mouseY = e.getY();
                int tileX = mouseX / tileSize;
                int tileY = mouseY / tileSize;
                if (((1<tileX) && (tileX<23)) && (2<tileY) && (tileY<15)) {
                    System.out.println("Mouse clicked at tile: " + tileX + ", " + tileY);

                    buildHall.handleMouseClick(tileX, tileY);
                    mapPanel.repaint();
                    }
            }
        });
        this.add(mapPanel, BorderLayout.CENTER);

        this.buildHall = new BuildHall(this.maxScreenColumn, this.maxScreenRow, this.tileSize, this.tileHandler, mapPanel, hallCount);

        JPanel menu = new JPanel(new GridLayout(10, 1)); // 8 obje + Finish Build
        String[] objImages = {
                "/map/chest.png", "/map/box_small.png", "/map/box_big.png",
                "/map/ladder.png", "/map/skull.png", "/map/torch_extinguished.png",
                "/map/potion.png", "/map/colon.png"
        };

        for (int i = 0; i < objImages.length; i++) {
            int objType = i;
            try {
                ImageIcon originalIcon = new ImageIcon(Objects.requireNonNull(Player.class.getResource(objImages[i])));
                int originalWidth = originalIcon.getIconWidth();
                int originalHeight = originalIcon.getIconHeight();

                int scaledWidth, scaledHeight;
                if (originalWidth > originalHeight) {
                    scaledWidth = 64; // Target width
                    scaledHeight = (originalHeight * 64) / originalWidth;
                } else {
                    scaledHeight = 64; // Target height
                    scaledWidth = (originalWidth * 64) / originalHeight;
                }
                Image scaledImage = originalIcon.getImage().getScaledInstance(scaledWidth, scaledHeight, Image.SCALE_SMOOTH);
                //ImageIcon scaledIcon = new ImageIcon(scaledImage);

                JButton button = getJButton(scaledWidth, scaledHeight, scaledImage);
                button.addActionListener(e -> this.buildHall.selectObject(objType));
                menu.add(button);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }



        if (hallCount == 3) {
            finishButton = new JButton("Finish Build");
            finishButton.addActionListener(this);
            menu.add(finishButton);
        } else {
            nextButton = new JButton("Next Hall");
            nextButton.addActionListener(this);
            menu.add(nextButton);
        }
        randomButton = new JButton("Random Builder");
        randomButton.addActionListener(this);
        menu.add(randomButton);
        try {
            backgroundImage = ImageIO.read(Objects.requireNonNull(getClass().getResource("/map/background.jpeg")));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        this.add(menu, BorderLayout.EAST);
        this.pack();
        this.setLocationRelativeTo(null);
        this.setVisible(true);
    }

    private static JButton getJButton(int scaledWidth, int scaledHeight, Image scaledImage) {
        JButton button = new JButton() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                int x = (getWidth() - scaledWidth) / 2; // Center horizontally
                int y = (getHeight() - scaledHeight) / 2; // Center vertically
                g.drawImage(scaledImage, x, y, null);
            }
        };
        button.setFocusPainted(false);
        button.setContentAreaFilled(false);
        button.setBorderPainted(false);
        return button;
    }

    @Override
    public void actionPerformed(ActionEvent actionEvent) {
        ImageIcon icon = new ImageIcon(Objects.requireNonNull(getClass().getResource("/player/player_right.png")));
        if (actionEvent.getSource() == finishButton) {
            if (buildHall.validateHall(hallCount)){
                this.dispose();
                new PlayModeFrame(0, saveFileName, null);
            }
            else {
                JOptionPane.showMessageDialog(BuildHallFrame.this, "This hall requires at least 17 objects! \n Place " + buildHall.requiredCount + " more objects!", "Error", JOptionPane.ERROR_MESSAGE, icon);
            }
        } else if (actionEvent.getSource() == nextButton) {
            if (buildHall.validateHall(hallCount)) {
                this.dispose();
                new BuildHallFrame(hallCount + 1, saveFileName);
            }
            else {
                JOptionPane.showMessageDialog(BuildHallFrame.this, "This hall requires at least " + obj_rest[hallCount] + " objects! \n Place " + buildHall.requiredCount + " more objects!", "Error", JOptionPane.ERROR_MESSAGE, icon);
            }
        } else if (actionEvent.getSource() == randomButton) {
            buildHall.randomBuildHall();
            mapPanel.repaint();
        }
    }


    public void drawObjects(Graphics2D g2) {
        SuperObject[] copyArray = Arrays.copyOf(buildHall.objects, buildHall.objects.length);
        SuperObject[] nonNullArray = Arrays.stream(copyArray)
                .filter(Objects::nonNull)
                .toArray(SuperObject[]::new);
        Arrays.sort(nonNullArray);
        for (SuperObject obj : nonNullArray) {
            if (obj != null) {
                obj.draw(g2, buildHall.tileSize);
            }
        }
    }
}

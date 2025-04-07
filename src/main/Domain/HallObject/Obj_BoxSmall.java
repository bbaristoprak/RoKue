package main.Domain.HallObject;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.IOException;
import java.util.Objects;

public class Obj_BoxSmall extends SuperObject{
    public Obj_BoxSmall() {
        name= "BoxSmall";
        try{
            image = ImageIO.read((Objects.requireNonNull(getClass().getResourceAsStream("/map/box_small.png"))));
        }catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void draw(Graphics2D g2, int tileSize) {
        if (image != null) {
            g2.drawImage(image, x * tileSize, (y-1) * tileSize, tileSize, tileSize*2, null);
        }
    }
}
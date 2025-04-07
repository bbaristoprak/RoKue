package main.Domain.HallObject;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.IOException;
import java.util.Objects;

public class Obj_Colon extends SuperObject{
    public Obj_Colon() {
        name= "Colon";
        try{
            image = ImageIO.read((Objects.requireNonNull(getClass().getResourceAsStream("/map/colon.png"))));
        }catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void draw(Graphics2D g2, int tileSize) {
        if (image != null) {
            g2.drawImage(image, x * tileSize, (y-1) * tileSize, tileSize, tileSize*2, null);
        }
    }
}
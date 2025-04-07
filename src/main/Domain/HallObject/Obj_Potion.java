package main.Domain.HallObject;

import javax.imageio.ImageIO;
import java.io.IOException;
import java.util.Objects;

public class Obj_Potion extends SuperObject{
    public Obj_Potion() {
        name= "Potion";
        try{
            image = ImageIO.read((Objects.requireNonNull(getClass().getResourceAsStream("/map/potion.png"))));
        }catch (IOException e) {
            e.printStackTrace();
        }
    }
}
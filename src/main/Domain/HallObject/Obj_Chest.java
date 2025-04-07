package main.Domain.HallObject;

import javax.imageio.ImageIO;
import java.io.IOException;
import java.util.Objects;

public class Obj_Chest extends SuperObject{
    public Obj_Chest() {
        name= "Chest";
        try{
            image = ImageIO.read((Objects.requireNonNull(getClass().getResourceAsStream("/map/chest.png"))));
        }catch (IOException e) {
            e.printStackTrace();
        }
    }
}
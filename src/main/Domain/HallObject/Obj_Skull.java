package main.Domain.HallObject;

import javax.imageio.ImageIO;
import java.io.IOException;
import java.util.Objects;

public class Obj_Skull extends SuperObject{
    public Obj_Skull() {
        name= "Skull";
        try{
            image = ImageIO.read((Objects.requireNonNull(getClass().getResourceAsStream("/map/skull.png"))));
        }catch (IOException e) {
            e.printStackTrace();
        }
    }
}
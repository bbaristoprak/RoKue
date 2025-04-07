package main.Domain.HallObject;

import javax.imageio.ImageIO;
import java.io.IOException;
import java.util.Objects;

public class Obj_Ladder extends SuperObject{
    public Obj_Ladder() {
        name= "Ladder";
        try{
            image = ImageIO.read((Objects.requireNonNull(getClass().getResourceAsStream("/map/ladder.png"))));
        }catch (IOException e) {
            e.printStackTrace();
        }
    }
}
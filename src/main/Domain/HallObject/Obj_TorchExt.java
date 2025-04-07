package main.Domain.HallObject;

import javax.imageio.ImageIO;
import java.io.IOException;
import java.util.Objects;

public class Obj_TorchExt extends SuperObject{
    public Obj_TorchExt() {
        name= "TorchExt";
        try{
            image = ImageIO.read((Objects.requireNonNull(getClass().getResourceAsStream("/map/torch_extinguished.png"))));
        }catch (IOException e) {
            e.printStackTrace();
        }
    }
}

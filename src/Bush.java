import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;

public class Bush {
    int x,y;
    BufferedImage BushImage;
    Bush(int x,int y){
        this.x=x;
        this.y=y;
        try {
            BushImage = ImageIO.read(new File("bushes_1.png"));
        } catch (Exception e) {

        }
    }
    public void paint(Graphics g){
        g.drawImage(BushImage,x,y,null);
    }
}

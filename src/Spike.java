import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;

public class Spike {
    int x,y;
    BufferedImage SpikeImage;

    Spike(int x,int y){
       this.x=x;
       this.y=y;
       try{
           SpikeImage= ImageIO.read(new File("Spike.png"));
       }catch (Exception e){
           System.out.println("spike Image Error");
       }
    }

    public void paint(Graphics g){
        g.drawImage(SpikeImage,x,y,null);
        Graphics2D g2 = (Graphics2D)g;
        g2.draw(Collision());
    }

    public Rectangle Collision (){
        return new Rectangle(x,y,SpikeImage.getWidth(),SpikeImage.getHeight());
    }
}

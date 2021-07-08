import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;

public class BrickN {
    int x,y;
    BufferedImage nBrickImage;
    BrickN(int x, int y){
        this.x=x;
        this.y=y;
        try {
            nBrickImage = ImageIO.read(new File("brick_n.png"));
        }catch(Exception e){
            System.out.println("brickN image not found");
        }
    }
    public void paint(Graphics g){
        g.drawImage(nBrickImage,x,y,null);
        Graphics2D g2=(Graphics2D) g;
        //g2.draw(CollisionMain());
        //g2.draw(BottomCollider());
    }
    public Rectangle CollisionMain(){
        return new Rectangle(x,y,nBrickImage.getWidth(),nBrickImage.getHeight());
    }
    public Rectangle BottomCollider(){
        return new Rectangle(x+5,y+42,nBrickImage.getWidth()-10,nBrickImage.getHeight()/5);
    }
}

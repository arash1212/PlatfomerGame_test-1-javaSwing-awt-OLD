import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.ArrayList;

public class Enemy_1 {
    float x=100,y=100;
    float xA=1,yA=2f;
    int health=1;
    BufferedImage EnemyImage;
    ArrayList<Block> blocks;
    Enemy_1(ArrayList<Block> blockList,float x ,float y){
        this.x=x;
        this.y=y;
        this.blocks=blockList;
        try {
            EnemyImage = ImageIO.read(new File("Enemy-1.png"));
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    public void Update(){
        x=x+xA;
        Move();
        y=y+yA;
    }

    public void paint(Graphics g){
        g.drawImage(EnemyImage,(int)x,(int)y,null);
        Graphics2D g2 = (Graphics2D)g;
        //g2.draw(RightCollision());
        //g2.draw(LeftCollision());
        //g2.draw(TopCollision());
        //g2.draw(BottomCollision());

    }
    public Rectangle RightCollision(){
        return new Rectangle((int)x+30,(int)y,EnemyImage.getWidth()/6,EnemyImage.getHeight()-5);
    }
    public Rectangle LeftCollision(){
        return new Rectangle((int)x+5,(int)y,EnemyImage.getWidth()/6,EnemyImage.getHeight()-5);
    }
    public Rectangle TopCollision(){
        return new Rectangle((int)x+5,(int)y,EnemyImage.getWidth()-15,EnemyImage.getHeight()/6);
    }
    public Rectangle BottomCollision(){
        return new Rectangle((int)x+5,(int)y+30,EnemyImage.getWidth()-15,EnemyImage.getHeight()/5);
    }

    public void Move() {
        for(Block b : blocks) {
            if (LeftCollision().intersects(b.collision())) {
               // System.out.println("hit left");
                xA=1;
            }else if (RightCollision().intersects(b.collision())){
                //System.out.println("hit right");
                xA=-1;
            }
            if(BottomCollision().intersects(b.collision())){
                yA=-1;
                return;
            }else{
                yA=2f;
            }
        }
    }
}

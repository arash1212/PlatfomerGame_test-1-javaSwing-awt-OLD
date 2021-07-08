import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;

public class EnemyFish {
    int x, y;
    int ya = 2, yFirst;
    BufferedImage FishUp;
    BufferedImage FishDown;
    boolean isMovingUp = true;
    boolean isMovingDown = false;
    boolean isReachedDown = true;
    boolean isReachedUp;
    float Timer = 0;
    float Time=(float)((Math.random())*3);

    EnemyFish(int x, int y) {
        this.x = x;
        this.y = y;
        yFirst = this.y;

        try {
            FishUp = ImageIO.read(new File("FishUp.png"));
            FishDown = ImageIO.read(new File("FishDown.png"));
        } catch (Exception e) {
            System.out.println("fish sprite file error");
        }
    }

    public void Update() {
        if (y <= yFirst - 300) {
            Timer = 0;
            isReachedUp = true;
            isReachedDown = false;
        } else if (y == yFirst) {
            isReachedUp = false;
            isReachedDown = true;
        }

        if (y > yFirst - 300) {
            if (Timer >= Time) {
                if (isMovingDown = true) {
                    ya = 2;
                    y = y - ya;
                    isMovingUp = true;
                    isMovingDown = false;
                }
            } else {
                Timer += 0.01f;
                //System.out.println(Timer);
            }
        }
        if (y <= yFirst + 300) {
            if (isReachedUp) {
                isMovingDown = true;
                isMovingUp = false;
                if (y < yFirst) {
                    ya = 3;
                    y = y + ya;
                }
                //System.out.println(Timer);
            }
        } else {
        }
    }

    public void paint(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;
        if (isMovingUp)
            g.drawImage(FishUp, x, y, null);
        else if (isMovingDown) {
            g.drawImage(FishDown, x, y, null);
        }

        //g2.draw(Collision());
    }

    public Rectangle Collision() {
        return new Rectangle(x, y, FishUp.getWidth(), FishUp.getHeight());
    }
}

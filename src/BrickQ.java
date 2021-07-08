import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;

public class BrickQ {
    int x, y, y2, firstY;
    boolean isGetHit;
    int CoinCount = 4;

    BufferedImage BrickQImage;
    BufferedImage QBlock_noCoinImage;

    BrickQ(int x, int y) {
        this.CoinCount=4;
        this.x = x;
        this.y = y;
        firstY = this.y;
        y2 = y - 25;
        try {
            BrickQImage = ImageIO.read(new File("QBlock.png"));
            QBlock_noCoinImage = ImageIO.read(new File("QBlock_noCoin.png"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void Update() {
        if (isGetHit) {
            if (CoinCount > 0) {
                if (y >= y2) {
                    y = y - 2;
                }
            }
        } else if (!isGetHit) {
            if (y <= firstY) {
                y = y + 2;
            }
        }
        if (y <= y2) {
            isGetHit = false;
        }

    }

    public void paint(Graphics g) {
        if (this.CoinCount > 0) {
            g.drawImage(BrickQImage, x, y, null);
        } else if (this.CoinCount <= 0) {
            g.drawImage(QBlock_noCoinImage, x, y, null);
        }
        Graphics2D g2 = (Graphics2D) g;
        //g2.draw(CollisionMain());
        //g2.draw(BottomCollision());
    }

    public Rectangle CollisionMain() {
        return new Rectangle(x, y, BrickQImage.getWidth(), BrickQImage.getHeight());
    }

    public Rectangle BottomCollision() {
        return new Rectangle(x + 5, y + 40, BrickQImage.getWidth() / 2, BrickQImage.getHeight() / 6);
    }
}
import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;

public class Block {
    BufferedImage blockImage;

    int x, y = 320;

    public Block(int x, int y) {
        this.x = x;
        this.y = y;

        try {
            blockImage = ImageIO.read(new File("Block.png"));
        } catch (Exception e) {

        }
    }

    public void paint(Graphics g) {

        g.drawImage(blockImage, x, y, null);
    }

    public Rectangle collision() {
        return new Rectangle(x, y, blockImage.getWidth(), blockImage.getHeight());
    }


}


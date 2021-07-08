import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;

public class Player {
    private int x = 50, witdh = 30, height = 60;
    private float y;
    private float xa = 0, ya = 0, yb = 0;
    float Gravity = 0.1f;
    boolean isGrounded;
    boolean isJumping;
    int JumpForce = 0;
    boolean isMovingLeft;
    boolean isMovingRight = true;
    boolean isGravitySet;
    //
    BufferedImage playerImageRight;
    BufferedImage playerImageLeft;
    BufferedImage playerImageRight_Jump;
    BufferedImage playerImageLeft_Jump;
    //

    Player() {
        try {
            playerImageRight = ImageIO.read(new File("playerRight_2.png"));
            playerImageLeft = ImageIO.read(new File("playerLeft_1.png"));
            playerImageRight_Jump = ImageIO.read(new File("playerRight_Jump_1.png"));
            playerImageLeft_Jump = ImageIO.read(new File("playerLeft_Jump_1.png"));
        } catch (Exception e) {
            System.out.println("image not found(player)");
        }
    }

    public int getX() {
        return x;
    }

    public float getY() {
        return y;
    }

    public float getYa() {
        return ya;
    }

    public void setYa(int ya) {
        this.ya = ya;
    }

    public float getXa() {
        return xa;
    }

    public void setXa(int xa) {
        this.xa = xa;
    }

    public float getYb() {
        return yb;
    }

    public void setYb(float yb) {
        this.yb = yb;
    }

    public void Update() {
        x = x + (int) xa;
        y = y + yb;
        y = y + ya;
        if (!isGrounded) {

            if (ya < 5) {
                ya = ya + Gravity;
                isGravitySet = false;
            }
        } else {
            if (!isGravitySet) {
                ya = 0;
                isGravitySet = true;
            }
            //ya = 0;
        }
    }

    public void paint(Graphics g) {
        //g.fillRect(x, y, witdh, height);
        if (isMovingRight) {
            if (isGrounded)
                g.drawImage(playerImageRight, x, (int) y, null);
            else
                g.drawImage(playerImageRight_Jump, x, (int) y, null);
        }
        if (isMovingLeft)
            if (isGrounded)
                g.drawImage(playerImageLeft, x, (int) y, null);
            else
                g.drawImage(playerImageLeft_Jump, x, (int) y, null);

        g.setColor((Color.red));
        Graphics2D g2 = (Graphics2D) g;
        //g2.draw(collisionBottom());
        //g2.draw(collisionTop());
        //g2.draw(collisionRight());
        //g2.draw(collisionLeft());
    }

    public void setY(int y) {
        this.y = y;
    }

    //
    public boolean isCollidingBottom(Rectangle other) {
        if (collisionBottom().intersects(other)) {
            return true;
        } else {
            return false;
        }
    }

    public boolean isCollidingTop(Rectangle other) {
        if (collisionTop().intersects(other)) {
            return true;
        } else {
            return false;
        }
    }

    public boolean isCollidingRight(Rectangle other) {
        if (collisionRight().intersects(other)) {
            return true;
        } else {
            return false;
        }
    }

    public boolean isCollidingLeft(Rectangle other) {
        if (collisionLeft().intersects(other)) {
            return true;
        } else {
            return false;
        }
    }

    //
    public Rectangle collisionBottom() {
        return new Rectangle(x + 10, (int) y + 50, playerImageRight.getWidth() - 20, playerImageRight.getHeight() / 6);
    }

    public Rectangle collisionTop() {
        return new Rectangle(x + 7, (int) y, playerImageRight.getWidth() - 20, playerImageRight.getHeight() / 3);
    }

    public Rectangle collisionRight() {
        return new Rectangle(x + 20, (int) y + 13, playerImageRight.getWidth() / 8, playerImageRight.getHeight() - 25);
    }

    public Rectangle collisionLeft() {
        return new Rectangle(x + 5, (int) y + 13, playerImageRight.getWidth() / 8, playerImageRight.getHeight() - 25);
    }
}

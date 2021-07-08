import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.ArrayList;
import java.util.Collections;

public class GameManager extends JPanel implements Runnable, KeyListener {
    Thread thread;
    Player player = new Player();
    ArrayList<Block> blocks = new ArrayList<>();
    boolean isGrounded;
    boolean canMoveRight = true;
    boolean canMoveLeft = true;
    boolean canMoveUp = true;
    boolean Jump;
    BufferedImage LevelImage;
    Camera camera;
    float Gravity = 0.05f;
    Block block = new Block(10, 10);
    BufferedImage backGroundImage;
    BufferedImage CoinImage;

    ArrayList<Enemy_1> EnemyList = new ArrayList<>();
    ArrayList<Bush> bushes = new ArrayList<>();
    ArrayList<BrickN> brickNS = new ArrayList<>();
    ArrayList<BrickQ> brickQS = new ArrayList<>();
    ArrayList<EnemyFish> EnemyFishList = new ArrayList<>();
    ArrayList<Spike> Spikes = new ArrayList<>();
    //
    boolean isMovingRight;
    boolean isMovingLeft;
    int CoinCount = 0;

    GameManager() {
        playSound("music2.wav");
    }

    public void Update() {
        if (backGroundImage == null) {
            try {
                backGroundImage = ImageIO.read(new File("background3.jpg"));
                CoinImage = ImageIO.read(new File("Coin.png"));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        Collections.synchronizedList(EnemyList);
        if (EnemyList.size() > 0)
            HitEnemyDie();
        player.isJumping = Jump;
        if (camera == null)
            camera = new Camera(player);

        if (LevelImage == null) {
            try {

            } catch (Exception e) {

            }
        }
        //
        camera.Update();
        isGrounded();
        canMoveRight();
        canMoveLeft();
        canMoveUp();
        //

        if (player.getY() > getHeight()) {
            try {
                playSound("ySerious.wav");
                thread.sleep(2000);

            } catch (Exception e) {
                e.printStackTrace();
            }
            System.exit(0);
        }

        player.Update();


    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);

        g.drawImage(backGroundImage, 0, 0, this);
        g.drawImage(CoinImage, 20, 20, null);
        g.setFont(new Font("TimesRoman", Font.PLAIN, 50));
        g.drawString(String.valueOf(CoinCount), 110, 74);
        Graphics2D g2 = (Graphics2D) g;
        g2.translate(camera.x, camera.y);
        setBackground(Color.white);
        for (Bush b : bushes) {
            b.paint(g);
        }
        paintMap(g);
        player.paint(g);


        for (Enemy_1 e : EnemyList) {
            if (e.health > 0) {
                e.paint(g);
                e.Update();
            }
        }
        for (BrickN b : brickNS) {
            b.paint(g);
        }
        for (BrickQ q : brickQS) {
            q.paint(g);
            q.Update();
        }
        for (EnemyFish q : EnemyFishList) {
            q.paint(g);
            q.Update();
        }
        for (Spike q : Spikes) {
            q.paint(g);
        }


        g2.translate(camera.x, camera.y);

    }

    @Override
    public void addNotify() {
        super.addNotify();
        thread = new Thread(this);
        thread.start();
    }

    @Override
    public void run() {
        while (true) {
            repaint();
            Update();
            try {
                thread.sleep(10);
            } catch (Exception e) {
                System.out.println("run error");
            }
        }
    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_A) {
            player.isMovingLeft = true;
            player.isMovingRight = false;
            if (canMoveLeft) {
                isMovingRight = false;
                isMovingLeft = true;
                player.setXa(-3);
            }

        } else if (e.getKeyCode() == KeyEvent.VK_D) {
            player.isMovingLeft = false;
            player.isMovingRight = true;
            if (canMoveRight) {
                player.setXa(3);
                isMovingLeft = false;
                isMovingRight = true;
            }

        }
        if (e.getKeyCode() == KeyEvent.VK_SPACE) {
            if (isGrounded) {
                Jump = true;
                playSound("Jump.wav");
                player.JumpForce = -5;
                player.setYa(player.JumpForce);
                // System.out.println("jump");

                Jump = false;

                // player.setYb(0);

            }
        }
        if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
            System.exit(0);
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_A) {
            isMovingLeft = false;
        } else if (e.getKeyCode() == KeyEvent.VK_D) {
            isMovingRight = false;
        }

    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    //
    public void isGrounded() {
        for (int i = 0; i < blocks.size(); i++) {
            if (player.isCollidingBottom(blocks.get(i).collision())) {
                isGrounded = true;
                player.isGrounded = true;
                //player.Gravity = 0;
                return;
            } else {
                isGrounded = false;
                player.isGrounded = false;
                // player.Gravity = 1;
            }
        }
        for (int i = 0; i < brickNS.size(); i++) {
            if (player.isCollidingBottom(brickNS.get(i).CollisionMain())) {
                isGrounded = true;
                player.isGrounded = true;
                //player.Gravity = 0;
                return;
            } else {
                isGrounded = false;
                player.isGrounded = false;
                // player.Gravity = 1;
            }
        }
        for (int i = 0; i < brickQS.size(); i++) {
            if (player.isCollidingBottom(brickQS.get(i).CollisionMain())) {
                isGrounded = true;
                player.isGrounded = true;
                //player.Gravity = 0;
                return;
            } else {
                isGrounded = false;
                player.isGrounded = false;
                // player.Gravity = 1;
            }
        }
    }

    public void canMoveRight() {
        for (Block b : blocks) {
            if (player.isCollidingRight(b.collision())) {
                //System.out.println("hit right");
                canMoveRight = false;
                if (player.getXa() > 0) {
                    player.setXa(0);
                }
                // System.out.println("hit right");
                return;
            } else {
                canMoveRight = true;
                if (isMovingRight) {
                    player.setXa(3);
                } else {
                    player.setXa(0);
                }
            }
        }
        for (BrickN m : brickNS) {
            if (player.isCollidingRight(m.CollisionMain())) {
                //System.out.println("hit right");
                canMoveRight = false;
                if (player.getXa() > 0) {
                    player.setXa(0);
                }
                // System.out.println("hit right");
                return;
            } else {
                canMoveRight = true;
                if (isMovingRight) {
                    player.setXa(3);
                } else {
                    player.setXa(0);
                }
            }
        }
        for (BrickQ m : brickQS) {
            if (player.isCollidingRight(m.CollisionMain())) {
                //System.out.println("hit right");
                canMoveRight = false;
                if (player.getXa() > 0) {
                    player.setXa(0);
                }
                // System.out.println("hit right");
                return;
            } else {
                canMoveRight = true;
                if (isMovingRight) {
                    player.setXa(3);
                } else {
                    player.setXa(0);
                }
            }
        }

    }

    public void canMoveLeft() {
        for (Block b : blocks) {
            if (player.isCollidingLeft(b.collision())) {
                //   System.out.println("hit left");
                canMoveLeft = false;
                if (player.getXa() < 0)
                    player.setXa(0);
                // System.out.println("hit left");
                return;
            } else {
                canMoveLeft = true;
                if (isMovingLeft) {
                    player.setXa(-3);
                }
            }
        }
        for (BrickN n : brickNS) {
            if (player.isCollidingLeft(n.CollisionMain())) {
                //   System.out.println("hit left");
                canMoveLeft = false;
                if (player.getXa() < 0)
                    player.setXa(0);
                // System.out.println("hit left");
                return;
            } else {
                canMoveLeft = true;
                if (isMovingLeft) {
                    player.setXa(-3);
                }
            }
        }
        //
        for (BrickQ n : brickQS) {
            if (player.isCollidingLeft(n.CollisionMain())) {
                //   System.out.println("hit left");
                canMoveLeft = false;
                if (player.getXa() < 0)
                    player.setXa(0);
                // System.out.println("hit left");
                return;
            } else {
                canMoveLeft = true;
                if (isMovingLeft) {
                    player.setXa(-3);
                }
            }
        }
    }

    public void HitEnemyDie() {
        for (Enemy_1 e : EnemyList) {
            if (player.isCollidingRight(e.RightCollision()) || player.isCollidingRight(e.LeftCollision()) || player.isCollidingLeft(e.LeftCollision()) || player.isCollidingLeft(e.LeftCollision()) || player.isCollidingTop(e.RightCollision()) || player.isCollidingTop(e.LeftCollision())) {
                if (e.health > 0) {
                    try {
                        playSound("Bruh.wav");
                        thread.sleep(3000);
                    } catch (Exception f) {
                        f.printStackTrace();
                    }
                    System.exit(0);
                }
            } else if (player.isCollidingBottom(e.TopCollision())) {
                // System.out.println("Enemy ded");
                if (e.health > 0) {
                    player.JumpForce = -5;
                    player.setYa(player.JumpForce);
                    e.health = 0;
                    playSound("Whatthe.wav");
                }

                //EnemyList.remove(e);
            }
        }
        for (EnemyFish e : EnemyFishList) {
            if (player.isCollidingRight(e.Collision()) || player.isCollidingRight(e.Collision()) || player.isCollidingLeft(e.Collision()) || player.isCollidingLeft(e.Collision()) || player.isCollidingTop(e.Collision()) || player.isCollidingTop(e.Collision())) {
                try {
                    playSound("Bruh.wav");
                    thread.sleep(3000);
                } catch (Exception f) {
                    f.printStackTrace();
                }
                System.exit(0);
            }
        }
        for (Spike e : Spikes) {
            if (player.isCollidingRight(e.Collision()) || player.isCollidingRight(e.Collision()) || player.isCollidingLeft(e.Collision()) || player.isCollidingLeft(e.Collision()) || player.isCollidingTop(e.Collision()) || player.isCollidingTop(e.Collision())) {
                try {
                    playSound("Bruh.wav");
                    thread.sleep(3000);
                } catch (Exception f) {
                    f.printStackTrace();
                }
                System.exit(0);
            }
        }
    }


    public void canMoveUp() {
        for (Block b : blocks) {
            if (player.isCollidingTop(b.collision()) || player.getY() > getHeight()) {
                canMoveUp = false;
                player.setYa(2);
                //   System.out.println("hit top");
                return;
            }
        }
        //
        for (BrickN n : brickNS) {
            if (player.isCollidingTop(n.CollisionMain())) {
                player.setY(2);
                return;
            }
            if (player.isCollidingTop(n.BottomCollider())) {
               // System.out.println("hit brick top");
                player.JumpForce = 150;
                playSound("brickSound.wav");
                brickNS.remove(n);
                player.setYa(2);
                return;
            }
        }
        for (BrickQ n : brickQS) {
            if (player.isCollidingTop(n.CollisionMain())) {
                player.setY(2);
                return;
            }
            if (player.isCollidingTop(n.BottomCollision())) {
                player.setYa(2);
                if(n.CoinCount>0) {
                    //System.out.println("hit coin brick");
                    n.CoinCount--;
                    playSound("CoinSound.wav");
                    n.isGetHit = true;
                }
                return;
            }
        }
    }
    //

    public void paintMap(Graphics g) {


        for (int i = 0; i < blocks.size(); i++) {
            blocks.get(i).paint(g);
        }
        return;
    }

    public void LoadImageLevel(BufferedImage image) throws Exception {

        int w = image.getHeight();
        int h = image.getHeight();

        for (int i = 0; i < h; i++) {
            for (int j = 0; j < w; j++) {
                int pixel = image.getRGB(i, j);
                int red = (pixel >> 16) & 0xff;
                int green = (pixel >> 8) & 0xff;
                int blue = (pixel) & 0xff;
                if (red == 255 && green == 255 && blue == 255) {
                    blocks.add(new Block(i * 32, j * 42));
                }
                if (red == 255 && green == 0 && blue == 0) {
                    bushes.add(new Bush(i * 32, j * 39));
                }
                if (red == 0 && green == 0 && blue == 255) {
                    EnemyList.add(new Enemy_1(blocks, i * 32, j * 39));
                }
                if (red == 0 && green == 255 && blue == 0) {
                    brickNS.add(new BrickN(i * 32, j * 42));
                }
                if (red == 128 && green == 128 && blue == 128) {
                    brickQS.add(new BrickQ(i * 32, j * 42));
                }
                if (red == 255 && green == 106 && blue == 0) {
                    EnemyFishList.add(new EnemyFish(i * 32, j * 42));
                }
                if (red == 127 && green == 51 && blue == 0) {
                    Spikes.add(new Spike(i * 32, j * 41));
                }
            }
        }
        System.out.println(blocks.size());
        return;
    }

    public void Start() {
        try {
            System.out.println("start");
            ;
            LevelImage = ImageIO.read(new File("Level_1.png"));
            LoadImageLevel(LevelImage);
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("load level error");
        }
    }

    public void playSound(String bip) {
        try {
            Media hit = new Media(new File(bip).toURI().toString());
            MediaPlayer mediaPlayer = new MediaPlayer(hit);
            mediaPlayer.play();
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("sound error");
        }
    }

}

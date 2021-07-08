public class Camera {
    int x, y;
    Player player;

    Camera(Player player) {
        this.player = player;
    }

    public void Update(){
        this.x=-(player.getX()-180);
    }
}

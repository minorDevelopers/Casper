package hack.the.bubble.casper.entities;

public class Player extends BaseEntity {
    private static Player instance;

    public static Player instance() {
        if (instance == null) {
            instance = new Player();
        }
        return instance;
    }

    public Player() {
        this.imageId = "player-ghost";
        this.pixWidth = 50;
        this.setPosX(10);
        this.setPosY(10);
    }

    @Override
    public void onCollide() {

    }

    @Override
    public void update() {

    }
}

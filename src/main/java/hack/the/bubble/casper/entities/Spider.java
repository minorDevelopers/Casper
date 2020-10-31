package hack.the.bubble.casper.entities;

import hack.the.bubble.casper.Coordinate;
import hack.the.bubble.casper.DrawBuffer;
import java.util.Random;

public class Spider extends BaseEntity{
    private int spawnTime;
    private int lifeTime;

    static Random rd = new Random();

    public Spider(DrawBuffer mainInstance) {
        super(mainInstance, "spider", 50);
        this.lifeTime = rd.nextInt(20) + 10;
        this.spawnTime = (int)System.currentTimeMillis();
        this.setEntityType("Spider");
        // I know its the same as Bush, sue me - yes Ryan I knicked it from you
        int row = rd.nextInt(3); // 0, 1, 2

        this.setPosY(row * rd.nextInt(1080));
        int x;
        if (row == 1) {
            x = rd.nextInt(1920 * 2); // First two columns
            if (x > 1920) { // If its second column push it into the third
                x += 1920;
            }
        } else {
            x = rd.nextInt(1920 * 3); // First three columns
        }
        this.setPosX(x);
    }

    @Override
    public void onCollide(BaseEntity e) {

    }

    @Override
    public void update() {
        if (((int)System.currentTimeMillis() - spawnTime) > lifeTime * 1000)
            this.isVisible = false;
    }

    @Override
    public void onClicked(Player player) {
        float distFrom = this.getDistanceFrom(player);
        if (distFrom < 200) {
            player.setScore(player.getScore() + 1);
            this.isVisible = false;
        }
    }
}

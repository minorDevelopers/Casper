package hack.the.bubble.casper.entities;

import hack.the.bubble.casper.DrawBuffer;
import hack.the.bubble.casper.ResourceManager;
import processing.core.PApplet;

import java.awt.*;

public class Player extends BaseEntity {
    private static Player instance;

    private static final int PLAYER_MOVE_SPEED = 5;
    private int score;

    public Player(DrawBuffer mainInstance, String imageId) {
        super(mainInstance, imageId, 130);
        this.moveSpeed = PLAYER_MOVE_SPEED;
        this.score = 0;
        this.setEntityType("player");
    }

    @Override
    public void onCollide(BaseEntity e) {
        if(e.getEntityType() == "candy" && e.isVisible()) this.score++;
    }

    @Override
    public void update() {

    }

    public static int getPlayerMoveSpeed() {
        return PLAYER_MOVE_SPEED;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }
}

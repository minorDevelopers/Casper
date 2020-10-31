package hack.the.bubble.casper.entities;

import hack.the.bubble.casper.DrawBuffer;
import hack.the.bubble.casper.ResourceManager;
import processing.core.PApplet;

import java.awt.*;

public class Player extends BaseEntity {
    private static Player instance;

    private static final int PLAYER_MOVE_SPEED = 5;

    public Player(DrawBuffer mainInstance, String imageId) {
        super(mainInstance, imageId, 130);
        this.moveSpeed = PLAYER_MOVE_SPEED;
    }

    @Override
    public void onCollide() {

    }

    @Override
    public void update() {

    }

    public static int getPlayerMoveSpeed() {
        return PLAYER_MOVE_SPEED;
    }
}

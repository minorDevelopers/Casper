package hack.the.bubble.casper.entities;

import hack.the.bubble.casper.ResourceManager;
import processing.core.PApplet;

import java.awt.*;

public class Player extends BaseEntity {
    private static Player instance;

    private static final int PLAYER_MOVE_SPEED = 5;

    public Player(PApplet mainInstance, String imageId) {
        super(mainInstance);
        this.imageId = imageId;
        this.pixWidth = 50;
        this.pixHeight = ResourceManager.getInstance().getScaledHeight(this.imageId, this.pixWidth);
        this.setPosX(10);
        this.setPosY(10);
        this.moveSpeed = PLAYER_MOVE_SPEED;
    }

    @Override
    public void onCollide() {

    }

    @Override
    public void update() {

    }
}

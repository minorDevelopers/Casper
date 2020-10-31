package hack.the.bubble.casper.entities;

import processing.core.PApplet;

public class Player extends BaseEntity {
    private static Player instance;

    private static final int PLAYER_MOVE_SPEED = 5;

    public Player(PApplet mainInstance) {
        super(mainInstance);
        this.imageId = "player-male";
        this.pixWidth = 50;
        this.setPosX(10);
        this.setPosY(10);
    }

    public void move(String direction) {
        switch(direction) {
            case "up": this.setPosY(this.getPosY() - PLAYER_MOVE_SPEED); break;
            case "down": this.setPosY(this.getPosY() + PLAYER_MOVE_SPEED); break;
            case "left": this.setPosX(this.getPosX() - PLAYER_MOVE_SPEED); break;
            case "right": this.setPosX(this.getPosX() + PLAYER_MOVE_SPEED); break;
            default: break;
        }
    }

    @Override
    public void onCollide() {

    }

    @Override
    public void update() {

    }
}

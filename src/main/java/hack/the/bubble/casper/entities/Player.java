package hack.the.bubble.casper.entities;

import hack.the.bubble.casper.DrawBuffer;
import hack.the.bubble.casper.ResourceManager;
import processing.core.PApplet;

import java.awt.*;
import java.util.Random;

public class Player extends BaseEntity {
    private static final int PLAYER_MOVE_SPEED = 5;
    private static final int PLAYER_COVID_COOLDOWN_RATE = 0;

    private int score;
    private double transmissionRate;
    private int covidCooldown;

    public Player(DrawBuffer mainInstance, String imageId) {
        super(mainInstance, imageId, 130);
        this.moveSpeed = PLAYER_MOVE_SPEED;
        this.score = 0;
        this.setEntityType("player");
        this.transmissionRate = 0.7;
        this.covidCooldown = 0;
    }

    @Override
    public void onCollide(BaseEntity e) {
        if(e.getEntityType() == "candy" && e.isVisible()) this.score++;
        //if(!this.hasCovid()) this.setCovid(e.hasCovid());
    }

    @Override
    public void update() {
        if(this.covidCooldown > PLAYER_COVID_COOLDOWN_RATE) this.covidCooldown -= PLAYER_COVID_COOLDOWN_RATE;
    }

    @Override
    public void onClicked() {
        // Nothing happens when you click on the player
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

    public int getCovidCooldown() {
        return covidCooldown;
    }

    public void setCovidCooldown(int covidCooldown) {
        this.covidCooldown = covidCooldown;
    }
}

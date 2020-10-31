package hack.the.bubble.casper;

import hack.the.bubble.casper.entities.Player;
import hack.the.bubble.casper.interaction.KeyManager;
import processing.core.PApplet;
import processing.event.KeyEvent;

import java.io.IOException;

public class Casper extends PApplet {

    private KeyManager manager = new KeyManager();
    private Player player;

    /*
        Player stuff, move to separate class?
     */
    private int playerX = 40, playerY = 40;
    private final int PLAYER_MOVE_SPEED = 10;

    @Override
    public void settings() {
        this.player = new Player();
        size(720, 480);
    }

    @Override
    protected void handleKeyEvent(KeyEvent event) {
        manager.handleKey(event);
    }

    @Override
    public void draw() {
        background(0xff0ff);
        text("Hello, World!", width / 2, height / 2);
        //image(ResourceManager.getInstance().getImage("player-ghost"), playerX, playerY, 50, ResourceManager.getInstance().getScaledHeight("player-ghost", 50));
        player.draw(this);


        if (manager.isPressed('w')) {
            player.setPosY(player.getPosY() + 10);
        }
        if (manager.isPressed('a')) {
            playerX -= PLAYER_MOVE_SPEED;
        }
        if (manager.isPressed('s')) {
            playerY += PLAYER_MOVE_SPEED;
        }
        if (manager.isPressed('d')) {
            playerX += PLAYER_MOVE_SPEED;
        }
    }

    public static void main(String[] args) {
        try {
            ResourceManager.getInstance().registerSprite("player-frankenstein", Casper.class.getResource("/Frankenstein.png"));
            ResourceManager.getInstance().registerSprite("player-ghost", Casper.class.getResource("/Ghost.png"));
            ResourceManager.getInstance().registerSprite("player-pumpkin-man", Casper.class.getResource("/PumpkinMan.png"));
            ResourceManager.getInstance().registerSprite("player-vampire", Casper.class.getResource("/Vampire.png"));
            ResourceManager.getInstance().registerSprite("player-male", Casper.class.getResource("/Player_Male.png"));
            ResourceManager.getInstance().registerSprite("player-female", Casper.class.getResource("/Player_Female.png"));
            ResourceManager.getInstance().registerSpriteSheet("floor-indoor", Casper.class.getResource("/IndoorFloors.png"), 1024, 1024, 0, 0);
        } catch (IOException e) {
            e.printStackTrace();
        }
        PApplet.main(new String[]{"hack.the.bubble.casper.Casper"});

    }

}

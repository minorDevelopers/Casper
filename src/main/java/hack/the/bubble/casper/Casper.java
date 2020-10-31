package hack.the.bubble.casper;

import hack.the.bubble.casper.entities.Player;
import hack.the.bubble.casper.interaction.KeyManager;
import processing.core.PApplet;
import processing.event.KeyEvent;

import java.io.IOException;

public class Casper extends PApplet {

    private KeyManager manager = new KeyManager();
    private Player player;


    @Override
    public void settings() {
        this.player = new Player(this);
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
        player.draw();


        if (manager.isPressed('w')) {
            player.move("up");
        }
        if (manager.isPressed('a')) {
            player.move("left");
        }
        if (manager.isPressed('s')) {
            player.move("down");
        }
        if (manager.isPressed('d')) {
            player.move("right");
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

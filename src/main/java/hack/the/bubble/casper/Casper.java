package hack.the.bubble.casper;

import hack.the.bubble.casper.entities.BaseEntity;
import hack.the.bubble.casper.entities.Candy;
import hack.the.bubble.casper.entities.NPC;
import hack.the.bubble.casper.entities.Player;
import hack.the.bubble.casper.interaction.KeyManager;
import processing.core.PApplet;
import processing.event.KeyEvent;
import processing.event.MouseEvent;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;

public class Casper extends PApplet {

    private KeyManager manager = new KeyManager();
    private Player player;
    private Player player2;
    private Collection<BaseEntity> entities = new ArrayList<>();
    private DrawBuffer drawBuffer;

    @Override
    public void settings() {
        size(1900, 1000);

        drawBuffer = new DrawBuffer(this, this.width, this.height);

        this.player = new Player(drawBuffer, "player-male");
        this.player2 = new Player(drawBuffer, "player-female");

        this.player.setPosX(width / 2);
        this.player.setPosY(height / 2);
        this.player2.setPosX(width / 2);
        this.player2.setPosY(height / 2);

        entities.add(player);
        entities.add(player2);

        for (int i = 0; i < 40; i++) {
            entities.add(new Candy(drawBuffer));
        }
        for (int i = 0; i < 10; i++) {
            entities.add(new NPC(drawBuffer, 5, 10));
        }
    }

    @Override
    protected void handleKeyEvent(KeyEvent event) {
        manager.handleKey(event);
    }

    @Override
    protected void handleMouseEvent(MouseEvent event) {
        super.handleMouseEvent(event);
    }

    @Override
    public void draw() {
        int bc = 0xff0ff;
        background(bc);

        //this.entities.forEach(BaseEntity::update);
        //this.entities.forEach(BaseEntity::draw);

        this.entities.forEach(e -> {
            e.update();
            e.draw();
        });

        if (manager.isPressed('w')) {
            player.move("up");
            drawBuffer.offsetY(Player.getPlayerMoveSpeed());
        } else if (manager.isPressed('s')) {
            player.move("down");
            drawBuffer.offsetY(-Player.getPlayerMoveSpeed());
        }
        if (manager.isPressed('a')) {
            player.move("left");
            drawBuffer.offsetX(Player.getPlayerMoveSpeed());
        } else if (manager.isPressed('d')) {
            player.move("right");
            drawBuffer.offsetX(-Player.getPlayerMoveSpeed());
        }

        if (manager.isPressed(java.awt.event.KeyEvent.VK_UP)) {
            drawBuffer.offsetY(-10);
        }
        if (manager.isPressed(java.awt.event.KeyEvent.VK_DOWN)) {
            drawBuffer.offsetY(10);
        }
        if (manager.isPressed(java.awt.event.KeyEvent.VK_LEFT)) {
            drawBuffer.offsetX(-10);
        }
        if (manager.isPressed(java.awt.event.KeyEvent.VK_RIGHT)) {
            drawBuffer.offsetX(10);
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
            ResourceManager.getInstance().registerSprite("candy.corn", Casper.class.getResource("/candy/CandyCorn.png"));
            ResourceManager.getInstance().registerSprite("candy.lolly", Casper.class.getResource("/candy/Lollipop.png"));
            ResourceManager.getInstance().registerSprite("candy.wrapped", Casper.class.getResource("/Candy/WrappedCandy.png"));
            ResourceManager.getInstance().registerSpriteSheet("floor-indoor", Casper.class.getResource("/IndoorFloor.png"), 1024, 1024, 0, 0);
        } catch (IOException e) {
            e.printStackTrace();
        }
        PApplet.main(new String[]{"hack.the.bubble.casper.Casper"});

    }

}

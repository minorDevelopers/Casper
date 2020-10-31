package hack.the.bubble.casper;

import hack.the.bubble.casper.entities.*;
import hack.the.bubble.casper.entities.candyable.Bush;
import hack.the.bubble.casper.entities.candyable.Pumpkin;
import hack.the.bubble.casper.entities.candyable.Tree;
import hack.the.bubble.casper.interaction.KeyManager;
import processing.core.PApplet;
import processing.event.KeyEvent;
import processing.event.MouseEvent;

import java.awt.Rectangle;
import java.io.IOException;
import java.util.*;

public class Casper extends PApplet {

    private KeyManager manager = new KeyManager();
    private Player player;
    private Collection<BaseEntity> entities = new ArrayList<>();
    private DrawBuffer drawBuffer;
    private Random rand = new Random();

    private static int worldHeight = 1080 * 3;
    private static int worldWidth = 1920 * 3;
    private static final int CAMERA_MOVE_SPEED = 20;
    private static double COVID_DISTANCE = 200.0;
    private static int MAX_COVID_COOLDOWN = 100; // number of updates/Player.COVID_COOLDOWN_RATE

    @Override
    public void settings() {
        size(1900, 1000);

        drawBuffer = new DrawBuffer(this, this.width, this.height);

        this.player = new Player(drawBuffer, "player-male");

        this.player.setPosX(width / 2);
        this.player.setPosY(height / 2);

        entities.add(player);

        entities.add(new Wall(drawBuffer, 400, 5));

        for (int i = 0; i < 40; i++) {
            entities.add(new Candy(drawBuffer));
        }
        for (int i = 0; i < 40; i++) {
            entities.add(new NPC(drawBuffer, 15, 45));
        }
        for (int i = 0; i < 30; i++) {
            entities.add(new Bush(drawBuffer, Bush.generateValidBushCoordinate()));
            entities.add(new Tree(drawBuffer, Tree.generateValidTreeCoordinate()));
            entities.add(new Pumpkin(drawBuffer, Pumpkin.generateValidPumpkinCoordinate()));
        }
        for (int i = 0; i < 10; i++) {
            entities.add(new Spider(drawBuffer));
        }
    }

    @Override
    protected void handleKeyEvent(KeyEvent event) {
        manager.handleKey(event);
    }

    @Override
    protected void handleMouseEvent(MouseEvent event) {
        super.handleMouseEvent(event);
//        System.out.println(event.getButton());
//        System.out.println(event.getCount());
//        System.out.println(event.getAction());
//        System.out.println();
        if (event.getAction() == MouseEvent.PRESS && event.getButton() == 37) {
            Coordinate coordinate = drawBuffer.convertScreenToGameCoordinates(event.getX(), event.getY());
            entities.stream()
                    .filter((entity) -> entity.intersects(coordinate.getX(), coordinate.getY()))
                    .forEach(BaseEntity::onClicked);
        }
    }

    public boolean willCollide(Rectangle hitbox) {
        return entities.stream()
                .filter((e) -> e.getEntityType().contains("solid"))
                .map((e) -> e.intersects(hitbox))
                .reduce((left, right) -> left || right)
                .orElse(false);
    }

    @Override
    public void draw() {
        int backgroundColour = 0x7ec850;
        float fontSize = 20f;
        background(backgroundColour);
        textSize(fontSize);
        fill(230, 230, 230);


        rect(0,0,5,5);

        // Entity Update
        this.entities.forEach(e -> {
            e.update();
            e.draw();

            if(e.getEntityType()=="NPC" && e.getDistanceFrom(this.player) < COVID_DISTANCE){

                if(e.hasCovid() && !this.player.hasCovid() && this.player.getCovidCooldown() <= 0) {
                    this.player.setCovidCooldown(MAX_COVID_COOLDOWN);
                    this.player.setCovid( rand.nextDouble() < player.getTransmissionRate()*e.getTransmissionRate() );
                }
            }

            if (e.intersects(this.player)) {
                player.onCollide(e);
                e.onCollide(this.player);
            }
        });

        // Entity Removal
        Iterator<BaseEntity> iter = entities.iterator();
        int countNPC = 0;
        int countSpider = 0;
        while (iter.hasNext()) {
            BaseEntity e = iter.next();
            if (e.getEntityType() == "NPC" && e.isVisible() == false) {
                iter.remove();
                countNPC++;
            }
            if (e.getEntityType() == "Spider" && e.isVisible() == false) {
                iter.remove();
                countSpider++;
            }
        }
        for (int i = 0; i < countNPC; i++) {
            entities.add(new NPC(drawBuffer, 15, 45));
        }
        for (int i = 0; i < countSpider; i++) {
            entities.add(new Spider(drawBuffer));
        }


        text("Score: " + Integer.toString(player.getScore()), 10, (int)fontSize);
        text("hasCovid: " + Boolean.toString(player.hasCovid()), 10, (int)fontSize*2);
        text("covidCooldown: " + Integer.toString(player.getCovidCooldown()), 10, (int)fontSize*3);

        if (manager.isPressed('w')) {
            if (!willCollide(player.simulateMove("up"))) {
                player.move("up");
                drawBuffer.offsetY(Player.getPlayerMoveSpeed());
            }
        } else if (manager.isPressed('s')) {
            if (!willCollide(player.simulateMove("down"))) {
                player.move("down");
                drawBuffer.offsetY(-Player.getPlayerMoveSpeed());
            }
        }
        if (manager.isPressed('a')) {
            if (!willCollide(player.simulateMove("left"))) {
                player.move("left");
                drawBuffer.offsetX(Player.getPlayerMoveSpeed());
            }
        } else if (manager.isPressed('d')) {
            if (!willCollide(player.simulateMove("right"))) {
                player.move("right");
                drawBuffer.offsetX(-Player.getPlayerMoveSpeed());
            }
        }

        if (manager.isPressed(java.awt.event.KeyEvent.VK_UP)) {
            drawBuffer.offsetY(CAMERA_MOVE_SPEED);
        }
        if (manager.isPressed(java.awt.event.KeyEvent.VK_DOWN)) {
            drawBuffer.offsetY(-CAMERA_MOVE_SPEED);
        }
        if (manager.isPressed(java.awt.event.KeyEvent.VK_LEFT)) {
            drawBuffer.offsetX(CAMERA_MOVE_SPEED);
        }
        if (manager.isPressed(java.awt.event.KeyEvent.VK_RIGHT)) {
            drawBuffer.offsetX(-CAMERA_MOVE_SPEED);
        }

        noFill();
        drawBuffer.rect(1920 * 0, 1080 * 0, 1920, 1080);
        drawBuffer.rect(1920 * 1, 1080 * 0, 1920, 1080);
        drawBuffer.rect(1920 * 2, 1080 * 0, 1920, 1080);
        drawBuffer.rect(1920 * 0, 1080 * 1, 1920, 1080);
        drawBuffer.rect(1920 * 1, 1080 * 1, 1920, 1080);
        drawBuffer.rect(1920 * 2, 1080 * 1, 1920, 1080);
        drawBuffer.rect(1920 * 0, 1080 * 2, 1920, 1080);
        drawBuffer.rect(1920 * 1, 1080 * 2, 1920, 1080);
        drawBuffer.rect(1920 * 2, 1080 * 2, 1920, 1080);

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

            ResourceManager.getInstance().registerSprite("outside.bush1", Casper.class.getResource("/outside/bush1.png"));
            ResourceManager.getInstance().registerSprite("outside.bush2", Casper.class.getResource("/outside/bush2.png"));
            ResourceManager.getInstance().registerSprite("outside.pumpkin1", Casper.class.getResource("/outside/Pumpkin1.png"));
            ResourceManager.getInstance().registerSprite("outside.pumpkin2", Casper.class.getResource("/outside/Pumpkin2.png"));
            ResourceManager.getInstance().registerSprite("outside.tree", Casper.class.getResource("/outside/Tree.png"));


            ResourceManager.getInstance().registerSprite("spider", Casper.class.getResource("/Spider.png"));

            ResourceManager.getInstance().registerSpriteSheet("floor-indoor", Casper.class.getResource("/IndoorFloor.png"), 1024, 1024, 0, 0);
        } catch (IOException e) {
            e.printStackTrace();
        }
        PApplet.main(new String[]{"hack.the.bubble.casper.Casper"});

    }

}

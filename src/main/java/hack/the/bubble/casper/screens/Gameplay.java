package hack.the.bubble.casper.screens;

import hack.the.bubble.casper.Casper;
import hack.the.bubble.casper.Coordinate;
import hack.the.bubble.casper.GameplayConfig;
import hack.the.bubble.casper.LevelStats;
import hack.the.bubble.casper.ResourceManager;
import hack.the.bubble.casper.entities.BaseEntity;
import hack.the.bubble.casper.entities.Candy;
import hack.the.bubble.casper.entities.NPC;
import hack.the.bubble.casper.entities.Player;
import hack.the.bubble.casper.entities.Spider;
import hack.the.bubble.casper.entities.candyable.Bush;
import hack.the.bubble.casper.entities.candyable.Pumpkin;
import hack.the.bubble.casper.entities.candyable.Tree;
import processing.core.PImage;

import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.Random;

public class Gameplay extends Screen {

    private Player player;
    private Collection<BaseEntity> entities = new ArrayList<>();
    private Collection<BaseEntity> newEntities = new ArrayList<>();
    private static double COVID_DISTANCE = 100.0;
    private static int MAX_COVID_COOLDOWN = 100; // number of updates/Player.COVID_COOLDOWN_RATE
    private Random rand = new Random();
    private PImage scaledFloor;
    private static final int TILE_SIZE = 120;
    private int candyLimit;
    private int npcCount, candyCount, spiderCount, foliageCount;
    private boolean shouldQuit = false;
    private int level;
    private GameplayConfig conf;

    public Gameplay(Casper casper) {
        super(casper);
        //this.candyLimit = rand.nextInt(30)+10;
        this.candyLimit = 100;
        this.candyCount = rand.nextInt(30)+20;
        this.npcCount = rand.nextInt(20)+10;
        this.spiderCount = rand.nextInt(30)+10;
        this.foliageCount = rand.nextInt(30)+20;
    }

    @Override
    public void setup(Object payload) {
        GameplayConfig conf = (GameplayConfig) payload;
        this.conf = conf;
        this.candyLimit = conf.getCandyLimit();
        this.npcCount = conf.getNpcCount();
        this.spiderCount = conf.getSpiderCount();
        this.foliageCount = conf.getFoliageCount();
        this.level = conf.getLevel();

        while(this.candyCount < this.candyLimit){
            this.candyCount += rand.nextInt(30);
        }

        scaledFloor = ResourceManager.getInstance().getImage("floor-grass").copy();
        scaledFloor.resize(TILE_SIZE, TILE_SIZE);

        this.player = new Player(getCasper().getDrawBuffer(), "player-male");

        this.player.setPosX((int) (1920 * 1.5));
        this.player.setPosY((int) (1080 * 1.5));
        this.getCasper().getDrawBuffer().offset(-1920, -1230);

        entities.add(player);

        //entities.add(new Wall(getCasper().getDrawBuffer(), 400, 5));

        for (int i = 0; i < candyCount; i++) {
            entities.add(new Candy(getCasper().getDrawBuffer()));
        }
        for (int i = 0; i < npcCount; i++) {
            entities.add(new NPC(getCasper().getDrawBuffer(), 15, 45));
        }
        for (int i = 0; i < foliageCount; i++) {
            entities.add(new Bush(getCasper().getDrawBuffer(), Bush.generateValidBushCoordinate()));
            entities.add(new Tree(getCasper().getDrawBuffer(), Tree.generateValidTreeCoordinate()));
            entities.add(new Pumpkin(getCasper().getDrawBuffer(), Pumpkin.generateValidPumpkinCoordinate()));
        }
        for (int i = 0; i < spiderCount; i++) {
            entities.add(new Spider(getCasper().getDrawBuffer()));
        }

    }

    @Override
    public void clicked(int x, int y) {
        Coordinate coordinate = getCasper().getDrawBuffer().convertScreenToGameCoordinates(x, y);
        entities.stream()
                .filter((entity) -> entity.intersects(coordinate.getX(), coordinate.getY()))
                .forEach(e-> e.onClicked(this.player, this.newEntities));
    }

    public boolean willCollide(Rectangle hitbox) {
        return entities.stream()
                .filter((e) -> e.getEntityType().contains("solid"))
                .map((e) -> e.intersects(hitbox))
                .reduce((left, right) -> left || right)
                .orElse(false);
    }

    public void drawFloor() {
        for (int x = 0; x < (1920 * 3) / TILE_SIZE; x++) {
            for (int y = 0; y < (1920 * 3) / TILE_SIZE; y++) {
                getCasper().getDrawBuffer().image(scaledFloor, x * TILE_SIZE, y * TILE_SIZE);
            }
        }
    }

    @Override
    public void render() {
        drawFloor();

        float fontSize = 20f;
        shouldQuit = false;
        getCasper().getDrawBuffer().textSize((int) fontSize);

        getCasper().getDrawBuffer().rect(0, 0, 5, 5);

        // Entity Update
        this.entities.forEach(e -> {
            e.update();
            e.draw();

            shouldQuit = player.getScore() >= this.candyLimit;

            if (e.getEntityType().equals("NPC") && e.getDistanceFrom(this.player) < COVID_DISTANCE) {

                if (e.hasCovid() && !this.player.hasCovid() && this.player.getCovidCooldown() <= 0) {
                    this.player.setCovidCooldown(MAX_COVID_COOLDOWN);
                    this.player.setCovid(rand.nextDouble() < player.getTransmissionRate() * e.getTransmissionRate());
                }
            }

            if (e.intersects(this.player)) {
                player.onCollide(e);
                e.onCollide(this.player);
            }
        });

        entities.addAll(this.newEntities);

        // Entity Removal
        Iterator<BaseEntity> iter = entities.iterator();
        int countNPC = 0;
        int countSpider = 0;
        while (iter.hasNext()) {
            BaseEntity e = iter.next();
            if (e.getEntityType().equals("NPC") && !e.isVisible()) {
                iter.remove();
                countNPC++;
            }
            if (e.getEntityType().equals("Spider") && !e.isVisible()) {
                iter.remove();
                countSpider++;
            }
        }
        for (int i = 0; i < countNPC; i++) {
            entities.add(new NPC(getCasper().getDrawBuffer(), 15, 45));
        }
        for (int i = 0; i < countSpider; i++) {
            entities.add(new Spider(getCasper().getDrawBuffer()));
        }



        getCasper().getDrawBuffer().hudText(String.format("Score: %d/%d", player.getScore(), this.candyLimit), 10, (int)fontSize);
        //getCasper().getDrawBuffer().hudText("hasCovid: " + Boolean.toString(player.hasCovid()), 10, (int)fontSize*2);
        //getCasper().getDrawBuffer().hudText("covidCooldown: " + Integer.toString(player.getCovidCooldown()), 10, (int)fontSize*3);

        if(shouldQuit) {
            getCasper().updateScreen(new LevelScreen(getCasper()), new LevelStats(candyCount, level + 1, player.hasCovid(), conf));
//            getCasper().updateScreen( new MenuScreen(getCasper()), null );
        }

        if (getCasper().getManager().isPressed('w')) {
            if (!willCollide(player.simulateMove("up"))) {
                player.move("up");
                getCasper().getDrawBuffer().offsetY(Player.getPlayerMoveSpeed());
            }
        } else if (getCasper().getManager().isPressed('s')) {
            if (!willCollide(player.simulateMove("down"))) {
                player.move("down");
                getCasper().getDrawBuffer().offsetY(-Player.getPlayerMoveSpeed());
            }
        }
        if (getCasper().getManager().isPressed('a')) {
            if (!willCollide(player.simulateMove("left"))) {
                player.move("left");
                getCasper().getDrawBuffer().offsetX(Player.getPlayerMoveSpeed());
            }
        } else if (getCasper().getManager().isPressed('d')) {
            if (!willCollide(player.simulateMove("right"))) {
                player.move("right");
                getCasper().getDrawBuffer().offsetX(-Player.getPlayerMoveSpeed());
            }
        }
        //getCasper().updateScreen(new LevelScreen(getCasper()), new LevelStats(0,1,false));
    }

}

package hack.the.bubble.casper;

import hack.the.bubble.casper.entities.*;
import hack.the.bubble.casper.entities.candyable.Bush;
import hack.the.bubble.casper.entities.candyable.Pumpkin;
import hack.the.bubble.casper.entities.candyable.Tree;
import hack.the.bubble.casper.interaction.KeyManager;
import hack.the.bubble.casper.screens.Gameplay;
import hack.the.bubble.casper.screens.MenuScreen;
import hack.the.bubble.casper.screens.Screen;
import processing.core.PApplet;
import processing.event.KeyEvent;
import processing.event.MouseEvent;

import java.io.IOException;
import java.util.Iterator;

public class Casper extends PApplet {

    private KeyManager manager = new KeyManager();
    private DrawBuffer drawBuffer;
    private Screen activeScreen;

    private static int worldHeight = 1080 * 3;
    private static int worldWidth = 1920 * 3;
    private static final int CAMERA_MOVE_SPEED = 20;
    private static double COVID_DISTANCE = 200.0;
    private static int MAX_COVID_COOLDOWN = 100; // number of updates/Player.COVID_COOLDOWN_RATE

    @Override
    public void settings() {
        size(1900, 1000);
        drawBuffer = new DrawBuffer(this, this.width, this.height);
        updateScreen(new MenuScreen(this), null);
        //updateScreen(new Gameplay(this));
    }

    @Override
    protected void handleKeyEvent(KeyEvent event) {
        manager.handleKey(event);
    }

    @Override
    protected void handleMouseEvent(MouseEvent event) {
        super.handleMouseEvent(event);
        if (event.getAction() == MouseEvent.PRESS && event.getButton() == 37) {
            if (activeScreen != null) activeScreen.clicked(event.getX(), event.getY());
        }
    }

    @Override
    public void draw() {
        int backgroundColour = 0x7ec850;
        background(backgroundColour);
        fill(230, 230, 230);
        rect(0, 0, 5, 5);

        if (activeScreen != null) {
            activeScreen.render();
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

    public void updateScreen(Screen screen, Object payload) {
        if (activeScreen != null)
            activeScreen.dispose();

        this.drawBuffer.offset(0, 0);
        this.drawBuffer.setScale(1);

        screen.setup(payload);
        this.activeScreen = screen;
    }

    public KeyManager getManager() {
        return manager;
    }

    public DrawBuffer getDrawBuffer() {
        return drawBuffer;
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

            ResourceManager.getInstance().registerSprite("menuScreen", Casper.class.getResource("/LoadScreens/LoadScreen.png"));
            ResourceManager.getInstance().registerSprite("BetweenLevelScreen", Casper.class.getResource("/LoadScreens/EmptyScreen.png"));


            ResourceManager.getInstance().registerSpriteSheet("floor-indoor", Casper.class.getResource("/IndoorFloor.png"), 1024, 1024, 0, 0);
            ResourceManager.getInstance().registerSprite("floor-grass", Casper.class.getResource("/Grass.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        PApplet.main(new String[]{"hack.the.bubble.casper.Casper"});

    }


}

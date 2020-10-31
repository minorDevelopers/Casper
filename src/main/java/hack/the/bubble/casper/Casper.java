package hack.the.bubble.casper;

import processing.core.PApplet;
import processing.core.PImage;

import java.awt.image.BufferedImage;
import java.io.IOException;

public class Casper extends PApplet {

    @Override
    public void settings() {
        size(720, 480);
    }

    @Override
    public void draw() {
        background(0xff0ff);
        text("Hello, World!", width / 2, height / 2);
        image(ResourceManager.getInstance().getImage("player-ghost"), 40, 40, 400, ResourceManager.getInstance().getScaledHeight("player-ghost", 400));
    }

    public static void main(String[] args) {
        try {
            ResourceManager.getInstance().registerSprite("player-frankenstein", Casper.class.getResource("/Frankenstein.png"));
            ResourceManager.getInstance().registerSprite("player-ghost", Casper.class.getResource("/Ghost.png"));
            ResourceManager.getInstance().registerSprite("player-pumpkin-man", Casper.class.getResource("/PumpkinMan.png"));
            ResourceManager.getInstance().registerSprite("player-vampire", Casper.class.getResource("/Vampire.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        PApplet.main(new String[]{"hack.the.bubble.casper.Casper"});
    }

}

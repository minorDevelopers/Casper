package hack.the.bubble.casper.sprite;

import processing.core.PImage;

import java.awt.image.BufferedImage;
import java.util.Random;

public class SpriteSheet {

    private BufferedImage image;
    private int width;
    private int height;
    private int offsetX;
    private int offsetY;

    private int horizontalSprites;
    private int verticalSprites;

    private final Random random = new Random();

    public SpriteSheet(BufferedImage image, int width, int height, int offsetX, int offsetY) {
        this.image = image;
        this.width = width;
        this.height = height;
        this.offsetX = offsetX;
        this.offsetY = offsetY;
        this.horizontalSprites = (int) Math.floor((image.getWidth() - offsetX) / (double) width);
        this.verticalSprites = (int) Math.floor((image.getHeight() - offsetY) / (double) height);
    }

    public int getSprites() {
        return verticalSprites * horizontalSprites;
    }

    public PImage getSprite(int x, int y) {
        if (x > horizontalSprites) throw new IllegalArgumentException("Sprite index out of range");
        if (y > verticalSprites) throw new IllegalArgumentException("Sprite index out of range");

        return new PImage(this.image.getSubimage(offsetX + (x * width), offsetY + (y * height), width, height));
    }

    public PImage getRandomSprite() {
        return getSprite(random.nextInt(horizontalSprites), random.nextInt(verticalSprites));
    }

}

package hack.the.bubble.casper.entities;

import hack.the.bubble.casper.DrawBuffer;
import hack.the.bubble.casper.ResourceManager;
import processing.core.PApplet;

import java.util.Random;

public class Candy extends BaseEntity {

    private static final String[] IMAGE_OPTIONS = {
            "candy.corn",
            "candy.lolly",
            "candy.wrapped",
    };
    private static final Random random = new Random();

    private final String sprite;
    private boolean isVisible = true;

    public Candy(DrawBuffer applet) {
        super(applet, "candy.wrapped", 50);
        this.sprite = IMAGE_OPTIONS[random.nextInt(IMAGE_OPTIONS.length)];

        // TODO: generate positions based on map
        this.setPosX(random.nextInt(applet.width));
        this.setPosY(random.nextInt(applet.height));
    }

    @Override
    public void draw() {
        if (!isVisible) return;
        this.mainInstance.image(
                ResourceManager.getInstance().getImage(sprite),
                this.getPosX(),
                this.getPosY(),
                30,
                ResourceManager.getInstance().getScaledHeight(this.sprite, 30)
        );
    }

    @Override
    public void update() {

    }

    @Override
    public void onCollide() {
        isVisible = false;
    }
}

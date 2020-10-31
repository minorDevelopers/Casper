package hack.the.bubble.casper.entities;

import hack.the.bubble.casper.DrawBuffer;
import hack.the.bubble.casper.ResourceManager;

import java.util.Collection;
import java.util.Random;

public class Candy extends BaseEntity {

    private static final String[] IMAGE_OPTIONS = {
            "candy.corn",
            "candy.lolly",
            "candy.wrapped",
    };
    private static final Random random = new Random();

    public Candy(DrawBuffer applet) {
        super(applet, IMAGE_OPTIONS[random.nextInt(IMAGE_OPTIONS.length)], 30);

        this.setEntityType("candy");

        // TODO: generate positions based on map
        this.setPosX(random.nextInt(applet.width));
        this.setPosY(random.nextInt(applet.height));
    }

    
    @Override
    public void update() {

    }

    @Override
    public void onClicked(Player player, Collection<BaseEntity> entitiesList) {
        // Possibly make it so you have to click candy to get points?
    }

    @Override
    public void onCollide(BaseEntity e) {
        this.setVisible(false);
    }
}

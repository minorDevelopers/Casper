package hack.the.bubble.casper.entities;

import hack.the.bubble.casper.DrawBuffer;

import java.util.Collection;
import java.util.Random;

public abstract class CandyableEntity extends BaseEntity {

    protected static final Random random = new Random();

    protected boolean hasCandy;

    public CandyableEntity(DrawBuffer instance, String imageId, int pixWidth, boolean hasCandy) {
        super(instance, imageId, pixWidth);
        this.hasCandy = hasCandy;
    }

    public CandyableEntity(DrawBuffer instance, String imageId, int pixWidth) {
        this(instance, imageId, pixWidth, random.nextBoolean());
    }

    public boolean hasCandy() {
        return hasCandy;
    }

    public void removeCandy(){
        hasCandy = false;
    }

    @Override
    public void onClicked(Player player, Collection<BaseEntity> entitiesList) {
        removeCandy();
        // TODO: increment score
    }

}

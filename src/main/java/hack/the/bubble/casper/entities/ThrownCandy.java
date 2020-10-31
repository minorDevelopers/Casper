package hack.the.bubble.casper.entities;

import hack.the.bubble.casper.Coordinate;
import hack.the.bubble.casper.DrawBuffer;

import java.util.Collection;
import java.util.Random;

public class ThrownCandy extends BaseEntity{
    static Random rd = new Random();


    private int lastTime;
    private int currentTime;
    private int totalTime;
    private float timeToCross;
    private static String candyStrings[] = {"candy.lolly", "candy.wrapped", "candy.corn"};

    private Coordinate startPos, endPos;

    public ThrownCandy(DrawBuffer instance, BaseEntity startEntity, BaseEntity targetEntity) {
        super(instance, candyStrings[rd.nextInt(candyStrings.length)], 40);

        this.isVisible = true;
        this.currentTime = (int)System.currentTimeMillis();
        this.lastTime = this.currentTime;
        this.totalTime = 0;
        this.timeToCross = 0.5f;
        this.hasCovid = false;
        this.setEntityType("ThrownCandy");
        this.transmissionRate = 0;
        this.startPos = new Coordinate(startEntity.getPosX(),startEntity.getPosY());
        this.endPos = new Coordinate(targetEntity.getPosX(), targetEntity.getPosY());
    }

    @Override
    public void onCollide(BaseEntity e) {

    }

    @Override
    public void update() {
        currentTime = (int)System.currentTimeMillis();
        totalTime += currentTime - lastTime;
        lastTime = currentTime;
        float percentage = (float)totalTime/ ((float)timeToCross * 1000.f);
        if (percentage >= 2.0){
            this.isVisible = false;
            return;
        }
        this.setPosX(linearInterpolate(this.startPos.getX(), this.endPos.getX(), percentage));
        this.setPosY(linearInterpolate(this.startPos.getY(), this.endPos.getY(), percentage));
    }

    @Override
    public void onClicked(Player player, Collection<BaseEntity> entitiesList) {

    }

    private int linearInterpolate(int y1, int y2, float mu) {
        return Math.max((int)((float)y1 * (1 - mu) + (float) y2 * mu), y2);
    }

}

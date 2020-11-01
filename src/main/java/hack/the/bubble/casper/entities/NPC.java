package hack.the.bubble.casper.entities;

import hack.the.bubble.casper.Coordinate;
import hack.the.bubble.casper.DrawBuffer;

import java.util.Collection;
import java.util.Random;

public class NPC extends BaseEntity{
    static Random rd = new Random();


    private int lastTime;
    private int currentTime;
    private int totalTime;
    private int timeToCross;

    private static final String[] npcStrings = {"player-frankenstein", "player-ghost", "player-pumpkin-man", "player-vampire"};

    private Coordinate startPos, endPos;

    public NPC(DrawBuffer mainInstance, int minTime, int maxTime) {
        super(mainInstance, npcStrings[rd.nextInt(npcStrings.length)], 130);

        this.isVisible = true;
        this.currentTime = (int)System.currentTimeMillis();
        this.lastTime = this.currentTime;
        this.totalTime = 0;
        this.timeToCross = minTime + rd.nextInt(maxTime - minTime);
        this.hasCovid = rd.nextBoolean();
        this.setEntityType("NPC");
        this.transmissionRate = 0.8;
        this.startPos = new Coordinate();
        this.endPos = new Coordinate();
        setStartEndLocation(1900 * 3, 1080 * 2);
    }

    private void setStartEndLocation(int screenWidth, int screenHeight) {
        if (rd.nextBoolean()){
            this.startPos.setX(-this.pixWidth);
            this.endPos.setX(screenWidth);
        }else {
            this.startPos.setX(screenWidth);
            this.endPos.setX(-this.pixWidth);
        }
        this.startPos.setY(1 + rd.nextInt(screenHeight));
        this.endPos.setY(1 + rd.nextInt(screenHeight));
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
        if (percentage >= 1.0){
            this.isVisible = false;
            return;
        }
        this.setPos(linearInterpolate(startPos, endPos, percentage));
    }

    @Override
    public void onClicked(Player player, Collection<BaseEntity> newEntitiesList) {
        // Check if within certain distance of player
        if (this.getDistanceFrom(player) < 400 && player.getScore() > 0) {
            newEntitiesList.add(new ThrownCandy(this.mainInstance, player, this));
            // Update time to get back based on previous speed
            float originalDist = distToTravel(startPos, endPos);
            float originalSpeed = originalDist / timeToCross;
            // If so then set end coordinates to start coordinates
            endPos = startPos;
            // start coordinates to current coordinates
            startPos = this.getPos();
            float newDist = distToTravel(startPos, endPos);
            this.timeToCross = (int) ((newDist / originalSpeed) / 1.1f);
            this.currentTime = (int)System.currentTimeMillis();
            this.lastTime = this.currentTime;
            this.totalTime = 0;
            player.setScore(player.getScore() - 1);
        }

    }

    private float distToTravel(Coordinate a, Coordinate b) {
        int dx = a.getX() - b.getX();
        int dy = a.getY() - b.getY();
        double diff = Math.pow(dx, 2.f) + Math.pow(dy, 2.f);
        return (float)Math.sqrt( diff );
    }

    private Coordinate linearInterpolate(Coordinate pos1, Coordinate pos2, float mu) {
        return new Coordinate((int)((float)pos1.getX() * (1 - mu) + (float) pos2.getX() * mu),(int)((float)pos2.getY() * (1 - mu) + (float) pos2.getY() * mu));
    }

    public boolean isHasCovid() {
        return hasCovid;
    }

    public void setHasCovid(boolean hasCovid) {
        this.hasCovid = hasCovid;
    }
}

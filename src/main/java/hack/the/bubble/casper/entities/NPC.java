package hack.the.bubble.casper.entities;

import hack.the.bubble.casper.DrawBuffer;
import hack.the.bubble.casper.ResourceManager;
import processing.core.PApplet;
import java.util.Random;

public class NPC extends BaseEntity{
    Random rd = new Random();

    public boolean hasCovid;
    public boolean finishedMovement = false;
    private int lastTime;
    private int currentTime;
    private int totalTime;
    private int timeToCross;

    private int startX, startY, endX, endY;

    public NPC(DrawBuffer mainInstance, int minTime, int maxTime) {
        super(mainInstance, ResourceManager.getInstance().getRandomImageID(), 130);
        this.isVisible = true;
        this.currentTime = (int)System.currentTimeMillis();
        this.lastTime = this.currentTime;
        this.totalTime = 0;
        this.timeToCross = minTime + rd.nextInt(maxTime - minTime);
        this.hasCovid = rd.nextBoolean();
        setStartEndLocation(1900, 1000);
    }

    private void setStartEndLocation(int screenWidth, int screenHeight) {
        boolean startLeft = rd.nextBoolean();
        if (rd.nextBoolean()){
            this.startX = -this.pixWidth;
            this.endX = screenWidth;
        }else {
            this.startX = screenWidth;
            this.endX = -this.pixWidth;
        }
        this.startY = 1 + rd.nextInt(screenWidth);
        this.endY = 1 + rd.nextInt(screenWidth);
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
            finishedMovement = true;
            this.isVisible = false;
            return;
        }
        this.setPosX(linearInterpolate(startX, endX, percentage));
        this.setPosY(linearInterpolate(startY, endY, percentage));
        System.out.println("Pos x: " + this.getPosX());
        System.out.println("Pos x: " + this.getPosY());
    }

    private int linearInterpolate(int y1, int y2, float mu) {
        return (int)((float)y1 * (1 - mu) + (float) y2 * mu);
    }
}

package hack.the.bubble.casper.entities;

import hack.the.bubble.casper.ResourceManager;
import processing.core.PApplet;

public abstract class BaseEntity {

    private int posX, posY;
    protected String imageId;
    private boolean isVisible;
    protected int pixWidth;
    protected PApplet mainInstance;

    // hitbox,

    public BaseEntity(PApplet instance) {
        this.mainInstance = instance;
    }


    /*
        Abstract functions
     */
    public abstract void onCollide();
    public abstract void update();

    public void draw() {
        this.mainInstance.image(
                ResourceManager.getInstance().getImage(imageId),
                this.posX, this.posY,
                this.pixWidth, ResourceManager.getInstance().getScaledHeight(imageId, this.pixWidth)
        );
    }

    public int getPosX() {
        return posX;
    }

    public void setPosX(int posX) {
        this.posX = posX;
    }

    public int getPosY() {
        return posY;
    }

    public void setPosY(int posY) {
        this.posY = posY;
    }

}

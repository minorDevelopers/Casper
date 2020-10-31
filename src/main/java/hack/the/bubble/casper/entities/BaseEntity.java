package hack.the.bubble.casper.entities;

import hack.the.bubble.casper.DrawBuffer;
import hack.the.bubble.casper.ResourceManager;
import processing.core.PApplet;

import java.awt.*;

public abstract class BaseEntity {

    private int posX, posY;
    protected int moveSpeed;
    protected String imageId;
    protected boolean isVisible;
    protected int pixWidth, pixHeight;
    protected DrawBuffer mainInstance;
    private String entityType;


    public Rectangle hitbox() {
        return new Rectangle(this.posX, this.posY, this.pixWidth, this.pixHeight);
    }

    public BaseEntity(DrawBuffer instance, String imageId, int pixWidth) {
        this.mainInstance = instance;
        this.posX = instance.width/2;
        this.posY = instance.height/2;
        this.imageId = imageId;
        this.isVisible = true;
        this.pixWidth = pixWidth;
        this.pixHeight = ResourceManager.getInstance().getScaledHeight(imageId, pixWidth);
        this.entityType = "none";
    }


    /*
        Abstract functions
     */
    public abstract void onCollide(BaseEntity e);
    public abstract void update();

    public boolean intersects(BaseEntity e) {
        return e.hitbox().intersects(this.hitbox());
    }

    public void draw() {

        if(this.isVisible) {
            this.mainInstance.image(
                    ResourceManager.getInstance().getImage(imageId),
                    this.posX, this.posY,
                    this.pixWidth, this.pixHeight
            );
        }
    }

    public void move(String direction) {
        switch(direction) {
            case "up": this.setPosY(this.getPosY() - this.moveSpeed); break;
            case "down": this.setPosY(this.getPosY() + this.moveSpeed); break;
            case "left": this.setPosX(this.getPosX() - this.moveSpeed); break;
            case "right": this.setPosX(this.getPosX() + this.moveSpeed); break;
            default: break;
        }
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

    public boolean isVisible() {
        return isVisible;
    }

    public void setVisible(boolean visible) {
        isVisible = visible;
    }

    public String getEntityType() {
        return entityType;
    }

    public void setEntityType(String entityType) {
        this.entityType = entityType;
    }
}

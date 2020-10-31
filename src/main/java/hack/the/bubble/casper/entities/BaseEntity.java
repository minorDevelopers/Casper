package hack.the.bubble.casper.entities;

import hack.the.bubble.casper.Coordinate;
import hack.the.bubble.casper.DrawBuffer;
import hack.the.bubble.casper.ResourceManager;
import processing.core.PApplet;

import java.awt.*;
import java.math.*;

public abstract class BaseEntity {

    private int posX, posY;
    protected int moveSpeed;
    protected String imageId;
    protected boolean isVisible;
    protected int pixWidth, pixHeight;
    protected DrawBuffer mainInstance;
    private String entityType;
    protected boolean hasCovid;
    protected double transmissionRate;


    public BaseEntity(DrawBuffer instance, String imageId, int pixWidth) {
        this.mainInstance = instance;
        this.posX = instance.width / 2;
        this.posY = instance.height / 2;
        this.imageId = imageId;
        this.isVisible = true;
        this.pixWidth = pixWidth;
        if (imageId == null) this.pixHeight = pixWidth;
        else this.pixHeight = ResourceManager.getInstance().getScaledHeight(imageId, pixWidth);
        this.entityType = "none";
        this.hasCovid = false;
        this.transmissionRate = 1.0;
    }


    /*
        Abstract functions
     */
    public abstract void onCollide(BaseEntity e);

    public abstract void update();

    public abstract void onClicked(Player player);



    // Returns true if the passed entity hitbox intersects with this entity's hotbox
    public boolean intersects(BaseEntity e) {
        return e.hitbox().intersects(this.hitbox());
    }

    public boolean intersects(int x, int y) {
        return this.hitbox().intersects(x, y, 1, 1);
    }

    public boolean intersects(Rectangle hitbox) {
        return this.hitbox().intersects(hitbox);
    }

    // If the entity is visible, draw it to the screen
    public void draw() {
        if(this.isVisible) {
            this.mainInstance.image(
                    ResourceManager.getInstance().getImage(imageId),
                    this.posX, this.posY,
                    this.pixWidth, this.pixHeight
            );
        }
    }

    // Moves the entity by its move speed in the direction specified.
    public void move(String direction) {
        switch (direction) {
            case "up":
                this.setPosY(this.getPosY() - this.moveSpeed);
                break;
            case "down":
                this.setPosY(this.getPosY() + this.moveSpeed);
                break;
            case "left":
                this.setPosX(this.getPosX() - this.moveSpeed);
                break;
            case "right":
                this.setPosX(this.getPosX() + this.moveSpeed);
                break;
            default:
                break;
        }
    }

    public Rectangle simulateMove(String direction) {
        Rectangle position = new Rectangle(hitbox());
        switch (direction) {
            case "up":
                position.setLocation((int) position.getX(), this.getPosY() - this.moveSpeed);
                break;
            case "down":
                position.setLocation((int) position.getX(), this.getPosY() + this.moveSpeed);
                break;
            case "left":
                position.setLocation(this.getPosX() - this.moveSpeed, (int) position.getY());
                break;
            case "right":
                position.setLocation(this.getPosX() + this.moveSpeed, (int) position.getY());
                break;
            default:
                break;
        }

        return position;
    }


    public Coordinate getCenter() {
        return new Coordinate( this.posX+this.pixWidth/2, this.posY+this.pixHeight/2 );
    }

    public float getDistanceFrom(BaseEntity e) {
        Coordinate a, b;
        a = e.getCenter();
        b = this.getCenter();
        int dx = a.getX() - b.getX();
        int dy = a.getY() - b.getY();
        double diff = Math.pow(dx, 2.f) + Math.pow(dy, 2.f);
        return (float)Math.sqrt( diff );
    }

    // Returns a Rectangle object surrounding the entity at its position
    public Rectangle hitbox() {
        return new Rectangle(this.posX, this.posY, this.pixWidth, this.pixHeight);
    }


    /*
        Getters and setters
     */

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

    public boolean hasCovid() {
        return hasCovid;
    }

    public void setCovid(boolean b) {
        this.hasCovid = b;
    }

    public double getTransmissionRate() {
        return this.transmissionRate;
    }
}

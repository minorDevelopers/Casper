package hack.the.bubble.casper.entities;

import hack.the.bubble.casper.Coordinate;
import hack.the.bubble.casper.DrawBuffer;
import hack.the.bubble.casper.ResourceManager;

import java.awt.*;
import java.util.Collection;

public abstract class BaseEntity {

    private Coordinate pos;
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
        this.pos = new Coordinate(instance.width / 2, instance.height / 2);
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

    public abstract void onClicked(Player player, Collection<BaseEntity> newEntitiesList);



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
                    this.pos.getX(), this.pos.getY(),
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
        return new Coordinate( this.pos.getX()+this.pixWidth/2, this.pos.getY()+this.pixHeight/2 );
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
        return new Rectangle(this.pos.getX(), this.pos.getY(), this.pixWidth, this.pixHeight);
    }


    /*
        Getters and setters
     */

    public int getPosX() {
        return pos.getX();
    }

    public void setPosX(int posX) {
        this.pos.setX(posX);
    }

    public int getPosY() {
        return pos.getY();
    }

    public void setPosY(int posY) {
        this.pos.setY(posY);
    }

    public Coordinate getPos() {
        return this.pos;
    }

    public void setPos(Coordinate pos) {
        this.pos = pos;
    }

    public void setPos(int x, int y) {
        this.pos = new Coordinate(x, y);
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

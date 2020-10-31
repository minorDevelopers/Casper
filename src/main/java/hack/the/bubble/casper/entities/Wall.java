package hack.the.bubble.casper.entities;

import hack.the.bubble.casper.DrawBuffer;

import java.awt.Rectangle;

public class Wall extends BaseEntity{

    private int width;
    private int height;

    public Wall(DrawBuffer instance, int width, int height) {
        super(instance, null, 0);
        this.width = width;
        this.height = height;
        this.setEntityType("wall.solid");
    }

    @Override
    public void draw() {
        this.mainInstance.rect(this.getPosX(), this.getPosY(), width, height);
    }

    @Override
    public Rectangle hitbox() {
        return new Rectangle(getPosX(), getPosY(), width, height);
    }

    @Override
    public void onCollide(BaseEntity e) {
    }

    @Override
    public void update() {

    }

    @Override
    public void onClicked(Player player) {

    }

}

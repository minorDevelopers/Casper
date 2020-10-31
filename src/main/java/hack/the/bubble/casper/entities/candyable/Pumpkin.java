package hack.the.bubble.casper.entities.candyable;

import hack.the.bubble.casper.Coordinate;
import hack.the.bubble.casper.DrawBuffer;
import hack.the.bubble.casper.entities.BaseEntity;
import hack.the.bubble.casper.entities.CandyableEntity;

public class Pumpkin extends CandyableEntity {

    public static Coordinate generateValidPumpkinCoordinate() {
        int x = random.nextInt(1920 * 2); // First two columns
        int y = random.nextInt(1080) + 1080; // Second row

        if (x > 1920) { // If its second column push it into the third
            x += 1920;
        }

        return new Coordinate(x, y);
    }

    public Pumpkin(DrawBuffer instance, Coordinate coordinate) {
        super(instance, random.nextBoolean() ? "outside.pumpkin1" : "outside.pumpkin2", 100);
        setPosX(coordinate.getX());
        setPosY(coordinate.getY());
        setEntityType("pumpkin.solid");
    }

    @Override
    public void onCollide(BaseEntity e) {

    }

    @Override
    public void update() {

    }
}

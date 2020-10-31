package hack.the.bubble.casper.entities.candyable;

import hack.the.bubble.casper.Coordinate;
import hack.the.bubble.casper.DrawBuffer;
import hack.the.bubble.casper.entities.BaseEntity;
import hack.the.bubble.casper.entities.CandyableEntity;

public class Bush extends CandyableEntity {

    public static Coordinate generateValidBushCoordinate() {
        int row = random.nextInt(3); // 0, 1, 2

        int x;
        int y = row * random.nextInt(1080);

        if (row == 1) {
            x = random.nextInt(1920 * 2); // First two columns
            if (x > 1920) { // If its second column push it into the third
                x += 1920;
            }
        } else {
            x = random.nextInt(1920 * 3); // First three columns
        }
        x = x % 100 + 50 * (random.nextInt(2) - 1);
        y = y % 100 + 50 * (random.nextInt(2) - 1);

        return new Coordinate(x, y);
    }

    public Bush(DrawBuffer instance, Coordinate coordinate) {
        super(instance, random.nextBoolean() ? "outside.bush1" : "outside.bush2", 70);
        setPosX(coordinate.getX());
        setPosY(coordinate.getY());
        setEntityType("bush.solid");
    }

    @Override
    public void onCollide(BaseEntity e) {

    }

    @Override
    public void update() {

    }
}

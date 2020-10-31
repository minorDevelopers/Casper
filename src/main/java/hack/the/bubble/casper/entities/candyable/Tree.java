package hack.the.bubble.casper.entities.candyable;

import hack.the.bubble.casper.Coordinate;
import hack.the.bubble.casper.DrawBuffer;
import hack.the.bubble.casper.entities.BaseEntity;
import hack.the.bubble.casper.entities.CandyableEntity;

public class Tree extends CandyableEntity {
    public static Coordinate generateValidTreeCoordinate() {
        // I know its the same as Bush, sue me
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

        return new Coordinate(x, y);
    }

    public Tree(DrawBuffer instance, Coordinate coordinate) {
        super(instance, "outside.tree", 120);
        setPosX(coordinate.getX());
        setPosY(coordinate.getY());
        setEntityType("tree");
    }

    @Override
    public void onCollide(BaseEntity e) {

    }

    @Override
    public void update() {

    }
}

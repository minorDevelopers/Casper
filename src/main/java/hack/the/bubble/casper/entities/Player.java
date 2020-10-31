package hack.the.bubble.casper.entities;


import de.gurkenlabs.litiengine.IUpdateable;
import de.gurkenlabs.litiengine.entities.Creature;
import de.gurkenlabs.litiengine.input.PlatformingMovementController;

public class Player extends Creature implements IUpdateable {
    private static Player instance;

    public static Player instance() {
        if (instance == null) {
            instance = new Player();
        }
        return instance;
    }

    private Player() {
        super("pumpkin");
        this.addController(new PlatformingMovementController<>(this));
    }

    @Override
    public void update() {

    }
}

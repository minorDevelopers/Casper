package hack.the.bubble.casper;

import de.gurkenlabs.litiengine.Game;
import de.gurkenlabs.litiengine.resources.Resources;

public class Casper {

    public static void main(String[] args) {
        Game.info().setName("Casper");
        Game.info().setSubTitle("");
        Game.info().setVersion("v0.1");
        Game.info().setWebsite("https://github.com/minorDevelopers/Casper");
        Game.info().setDescription("");

        Game.init(args);
        Game.start();
    }

}

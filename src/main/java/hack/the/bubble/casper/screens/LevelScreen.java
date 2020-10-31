package hack.the.bubble.casper.screens;

import hack.the.bubble.casper.Casper;
import hack.the.bubble.casper.GameplayConfig;
import hack.the.bubble.casper.LevelStats;
import hack.the.bubble.casper.ResourceManager;

public class LevelScreen extends Screen {


    LevelStats stats;

    public LevelScreen(Casper casper) {
        super(casper);
    }

    @Override
    public void setup(Object payload) {
        stats = (LevelStats) payload;
    }

    @Override
    public void clicked(int x, int y) {
        if (!stats.isHasCOVID()) {
            getCasper().updateScreen(new Gameplay(getCasper()), new GameplayConfig(
                    (int) Math.ceil(this.stats.getConfig().getCandyLimit() * 1.5),
                    this.stats.getConfig().getLevel() + 1,
                    (int) Math.ceil(this.stats.getConfig().getNpcCount() * 1.5),
                    (int) Math.ceil(this.stats.getConfig().getSpiderCount() * 1.5),
                    (int) Math.ceil(this.stats.getConfig().getFoliageCount() * 1.5)
            ));
        } else {
            getCasper().updateScreen(new MenuScreen(getCasper()), null);
        }
    }

    @Override
    public void render() {
        String text = "";
        this.getCasper().getDrawBuffer().image(ResourceManager.getInstance().getImage("BetweenLevelScreen"), 0, 0, getCasper().getDrawBuffer().width, getCasper().getDrawBuffer().height);
        //this.getCasper().getDrawBuffer().hudText("Level Completed", 300,265);
        if (stats.isHasCOVID()) {
            text = "Sorry. You got COVID. \nClick anywhere to\n restart the game.";
        } else {
            text = "Congrats. \nClick anywhere to go\n to the next level.";
        }
        this.getCasper().getDrawBuffer().hudText(text, 300, 265);
        this.getCasper().getDrawBuffer().textSize(100);

    }
}

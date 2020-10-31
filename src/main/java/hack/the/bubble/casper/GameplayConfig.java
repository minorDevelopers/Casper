package hack.the.bubble.casper;

public class GameplayConfig {

    private int candyLimit;
    private int level;
    private int npcCount;
    private int spiderCount;
    private int foliageCount;

    public GameplayConfig(int candyLimit, int level, int npcCount, int spiderCount, int foliageCount) {
        this.candyLimit = candyLimit;
        this.level = level;
        this.npcCount = npcCount;
        this.spiderCount = spiderCount;
        this.foliageCount = foliageCount;
    }

    public int getCandyLimit() {
        return candyLimit;
    }

    public int getLevel() {
        return level;
    }

    public int getNpcCount() {
        return npcCount;
    }

    public int getSpiderCount() {
        return spiderCount;
    }

    public int getFoliageCount() {
        return foliageCount;
    }
}

package hack.the.bubble.casper;

public class LevelStats {

    int candyCount;
    int level;
    boolean hasCOVID;
    private GameplayConfig config;

    public int getCandyCount() {
        return candyCount;
    }

    public void setCandyCount(int candyCount) {
        this.candyCount = candyCount;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public boolean isHasCOVID() {
        return hasCOVID;
    }

    public void setHasCOVID(boolean hasCOVID) {
        this.hasCOVID = hasCOVID;
    }

    public GameplayConfig getConfig() {
        return config;
    }

    public LevelStats(int candyCount, int level, boolean hasCOVID, GameplayConfig config) {
        this.candyCount = candyCount;
        this.level = level;
        this.hasCOVID = hasCOVID;
        this.config = config;
    }
}

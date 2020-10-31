package hack.the.bubble.casper;

public class LevelStats {

    int candyCount;
    int level;
    boolean hasCOVID;

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

    public LevelStats(int candyCount, int level, boolean hasCOVID) {
        this.candyCount = candyCount;
        this.level = level;
        this.hasCOVID = hasCOVID;
    }



}

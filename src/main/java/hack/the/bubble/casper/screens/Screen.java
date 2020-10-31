package hack.the.bubble.casper.screens;

import hack.the.bubble.casper.Casper;

public abstract class Screen {

    private Casper casper;

    public Screen(Casper casper) {
        this.casper = casper;
    }

    protected Casper getCasper() {
        return casper;
    }

    public abstract void setup();

    public abstract void clicked(int x, int y);

    public abstract void render();

    public void dispose() {
    }

}

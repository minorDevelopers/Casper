package hack.the.bubble.casper.screens;

import hack.the.bubble.casper.Casper;
import hack.the.bubble.casper.ResourceManager;

public class MenuScreen extends Screen{


    public MenuScreen(Casper casper) {
        super(casper);
    }

    @Override
    public void setup() {

    }

    @Override
    public void clicked(int x, int y) {
        this.getCasper().updateScreen(new Gameplay(getCasper()));
    }

    @Override
    public void render() {
        this.getCasper().getDrawBuffer().image(ResourceManager.getInstance().getImage("menuScreen"), 0,0);
        this.getCasper().getDrawBuffer().hudText("Click Anywhere \nto start the game.", 300,265);
        this.getCasper().getDrawBuffer().textSize(100);

    }
}

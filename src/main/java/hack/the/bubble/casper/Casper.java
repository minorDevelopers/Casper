package hack.the.bubble.casper;

import processing.core.PApplet;

public class Casper extends PApplet {

    @Override
    public void settings() {
        size(720, 480);
    }

    @Override
    public void draw() {
        background(0xff0ff);
        text("Hello, World!", width / 2, height / 2);
    }

    public static void main(String[] args) {
        PApplet.main(new String[]{"hack.the.bubble.casper.Casper"});
    }

}

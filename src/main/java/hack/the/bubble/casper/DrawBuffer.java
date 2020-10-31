package hack.the.bubble.casper;

import processing.core.PApplet;
import processing.core.PImage;

public class DrawBuffer {

    private PApplet parent;

    private double xOffset = 0;
    private double yOffset = 0;

    private double scale = 1;

    public int width;
    public int height;

    public DrawBuffer(PApplet parent, int mockWidth, int mockHeight) {
        this.parent = parent;
        this.width = mockWidth;
        this.height = mockHeight;
    }

    public void setxOffset(double xOffset) {
        this.xOffset = xOffset;
    }

    public void setyOffset(double yOffset) {
        this.yOffset = yOffset;
    }

    public double getyOffset() {
        return this.yOffset;
    }

    public double getxOffset() {
        return this.xOffset;
    }

    public void offset(double x, double y) {
        this.xOffset = x;
        this.yOffset = y;
    }

    public void offsetX(double x) {
        this.xOffset += x;
    }

    public void offsetY(double y) {
        this.yOffset += y;
    }

    public void setScale(double scale) {
        this.scale = scale;
    }

    public void image(PImage image, int x, int y) {

        image(image, x, y, image.width, image.height);
    }

    public void image(PImage image, int x, int y, int width, int height) {
        parent.text("(" + xOffset + ", " + yOffset + ")", 30, 30);

        double newWidth = width * scale;
        double newHeight = height * scale;

        double newX = (x + xOffset) * scale;
        double newY = (y + yOffset) * scale;

        // If its out of bounds actually skip rendering
        if (newX + newWidth < 0) return;
        if (newY + newHeight < 0) return;
        if (newX > this.width) return;
        if (newY > this.height) return;

        parent.image(image, (float) newX, (float) newY, (float) newWidth, (float) newHeight);
    }

    public void rect(int x, int y, int width, int height) {
        double newWidth = width * scale;
        double newHeight = height * scale;

        double newX = (x + xOffset) * scale;
        double newY = (y + yOffset) * scale;

        // If its out of bounds actually skip rendering
        if (newX + newWidth < 0) return;
        if (newY + newHeight < 0) return;
        if (newX > this.width) return;
        if (newY > this.height) return;

        parent.rect((float) newX, (float) newY, (float) newWidth, (float) newHeight);
    }

    public Coordinate convertScreenToGameCoordinates(int x, int y) {
        return new Coordinate(
                (int) ((x / scale) - xOffset),
                (int) ((y / scale) - yOffset)
        );
    }

}

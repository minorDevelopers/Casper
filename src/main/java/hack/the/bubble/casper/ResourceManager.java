package hack.the.bubble.casper;

import processing.core.PImage;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

public class ResourceManager {

    private static ResourceManager instance;

    public static ResourceManager getInstance() {
        if (instance == null) instance = new ResourceManager();
        return instance;
    }

    private final Map<String, PImage> imageSet = new ConcurrentHashMap<>();

    /**
     * Registers a sprite from file path into the image cache. Throws an IllegalArgumentException if sprite name already
     * exists or if the file does not exist. Wrapper for the {@link #registerSprite(String, File)}.
     * @param name the name to load
     * @param location the location of the image file
     */
    public void registerSprite(String name, String location) throws IOException {
        registerSprite(name, new File(location));
    }

    /**
     * Registers a sprite from file into the image cache. Throws an IllegalArgumentException if sprite name already
     * exists and IO exception if the file cannot be loaded or does not exist
     * @param name the name to load
     * @param location the location of the file
     */
    public void registerSprite(String name, File location) throws IOException {
        if (!location.exists()) {
            throw new IOException("Sprite file does not exist");
        }

        registerSprite(name, ImageIO.read(location));
    }

    /**
     * Registers a sprite directly into the image cache. Throws an IllegalArgumentException if sprite name already
     * exists.
     * @param name the name to load
     * @param image the image to save
     */
    public void registerSprite(String name, BufferedImage image) {
        if (this.imageSet.containsKey(name)) {
            throw new IllegalArgumentException("Sprite name would override a previously loaded sprite");
        }

        imageSet.put(name, new PImage(image));
    }

    /**
     * Returns the sprite with this ID or null if it is not loaded
     * @param id the id to load
     * @return the image or null if not loaded
     */
    public PImage getImage(String id) {
        return this.imageSet.get(id);
    }

}

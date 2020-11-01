package hack.the.bubble.casper;

import hack.the.bubble.casper.sprite.SpriteSheet;
import processing.core.PImage;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Map;
import java.util.Random;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

public class ResourceManager {

    private static ResourceManager instance;

    public static ResourceManager getInstance() {
        if (instance == null) instance = new ResourceManager();
        return instance;
    }

    /**
     * Contains a cache of all currently loaded image files associated with their image IDs
     */
    private final Map<String, PImage> imageSet = new ConcurrentHashMap<>();
    private final Map<String, SpriteSheet> sheetSet = new ConcurrentHashMap<>();
    private final Random random = new Random();

    /**
     * Registers a sprite from file path into the image cache. Throws an IllegalArgumentException if sprite name already
     * exists or if the file does not exist. Wrapper for the {@link #registerSprite(String, File)}.
     *
     * @param name     the name to load
     * @param location the location of the image file
     */
    public void registerSprite(String name, String location) throws IOException {
        registerSprite(name, new File(location));
    }

    /**
     * Registers a sprite from file into the image cache. Throws an IllegalArgumentException if sprite name already
     * exists and IO exception if the file cannot be loaded or does not exist
     *
     * @param name     the name to load
     * @param location the location of the file
     */
    public void registerSprite(String name, File location) throws IOException {
        if (!location.exists()) {
            throw new IOException("Sprite file does not exist");
        }

        registerSprite(name, ImageIO.read(location));
    }

    /**
     * Registers a sprite from url into the image cache. Throws an IllegalArgumentException if sprite name already
     * exists or if the image cannot be loaded
     *
     * @param name the name to load
     * @param path the path from which to laod the image
     * @throws IOException if the URL cannot be loaded
     */
    public void registerSprite(String name, URL path) throws IOException {
        registerSprite(name, ImageIO.read(path));
    }

    /**
     * Registers a sprite directly into the image cache. Throws an IllegalArgumentException if sprite name already
     * exists.
     *
     * @param name  the name to load
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
     *
     * @param id the id to load
     * @return the image or null if not loaded
     */
    public PImage getImage(String id) {
        return this.imageSet.get(id);
    }

    /**
     * Returns the sprite sheet with this ID or null if it is not loaded
     *
     * @param id the id to load
     * @return the sheet or null if not loaded
     */
    public SpriteSheet getSheet(String id) {
        return this.sheetSet.get(id);
    }

    /**
     * Registers a spritesheet from file path into the image cache. Throws an IllegalArgumentException if sprite name
     * already exists or if the file does not exist. Wrapper for the {@link #registerSprite(String, File)}.
     *
     * @param name     the name to load
     * @param location the location of the image file
     */
    public void registerSpriteSheet(String name, String location, int width, int height, int xoffset, int yoffset) throws IOException {
        registerSpriteSheet(name, new File(location), width, height, xoffset, yoffset);
    }

    /**
     * Registers a sprite sheet from file into the image cache. Throws an IllegalArgumentException if sprite name already
     * exists and IO exception if the file cannot be loaded or does not exist
     *
     * @param name     the name to load
     * @param location the location of the file
     */
    public void registerSpriteSheet(String name, File location, int width, int height, int xoffset, int yoffset) throws IOException {
        if (!location.exists()) {
            throw new IOException("Sprite sheet file does not exist");
        }

        registerSpriteSheet(name, ImageIO.read(location), width, height, xoffset, yoffset);
    }

    /**
     * Registers a sprite sheet from url into the image cache. Throws an IllegalArgumentException if sprite name already
     * exists or if the image cannot be loaded
     *
     * @param name the name to load
     * @param path the path from which to laod the image
     * @throws IOException if the URL cannot be loaded
     */
    public void registerSpriteSheet(String name, URL path, int width, int height, int xoffset, int yoffset) throws IOException {
        registerSpriteSheet(name, ImageIO.read(path), width, height, xoffset, yoffset);
    }

    /**
     * Registers a sprite sheet directly into the image cache. Throws an IllegalArgumentException if sprite name already
     * exists.
     *
     * @param name  the name to load
     * @param image the image to save
     */
    public void registerSpriteSheet(String name, BufferedImage image, int width, int height, int xoffset, int yoffset) {
        if (this.imageSet.containsKey(name)) {
            throw new IllegalArgumentException("Sprite name would override a previously loaded sprite");
        }

        sheetSet.put(name, new SpriteSheet(image, width, height, xoffset, yoffset));
    }

    /**
     * Scales the images height based on the input width, maintaining aspect ratio
     *
     * @param id    the id of the image which is being scaled
     * @param width the new rendering width
     * @return the new rendering height
     */
    public int getScaledHeight(String id, int width) {
        PImage img = this.imageSet.get(id);
        double scale = (double) width / (double) img.width;
        return (int) (img.height * scale);
    }

    /**
     * Scales the images width based on the input height, maintaining aspect ratio
     *
     * @param id     the id of the image which is being scaled
     * @param height the new rendering height
     * @return the new rendering width
     */
    public int getScaledWidth(String id, int height) {
        PImage img = this.imageSet.get(id);
        double scale = (double) height / (double) img.height;
        return (int) (img.width * scale);
    }

    /**
     * Returns the set of currently loaded image ids
     *
     * @return set of image ids, unique as it is backed by a map
     */
    public Set<String> getLoadedImageIDs() {
        return this.imageSet.keySet();
    }

    /**
     * Picks a random image ID from the set of loaded IDs
     *
     * @return a randomly selected loaded ID
     */
    public String getRandomImageID(String category) {
        ArrayList<String> arrayList = new ArrayList<>(this.getLoadedImageIDs());
        return arrayList.get(random.nextInt(arrayList.size()));
    }

}

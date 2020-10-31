package hack.the.bubble.casper.interaction;

import processing.event.KeyEvent;

import java.util.HashSet;
import java.util.Set;

/**
 * A key manager which supports concurrent key presses from a processing window
 */
public class KeyManager {

    private final static int KEY_DOWN_EVENT = 1;
    private final static int KEY_UP_EVENT = 2;

    /**
     * The set of currently held key codes
     */
    private final Set<Integer> keySet = new HashSet<>();

    /**
     * Handles a processing key event to update the internal key set
     *
     * @param event the event to process
     */
    public void handleKey(KeyEvent event) {
        if (event.getAction() == KEY_DOWN_EVENT) keySet.add(event.getKeyCode());
        if (event.getAction() == KEY_UP_EVENT) keySet.remove(event.getKeyCode());
    }

    /**
     * Returns if the current key is pressed
     *
     * @param key the key character to test
     * @return if the key is currently pressed
     */
    public boolean isPressed(char key) {
        return keySet.contains(java.awt.event.KeyEvent.getExtendedKeyCodeForChar(key));
    }

    /**
     * Returns if the current key is pressed
     *
     * @param code the key code (as from {@link KeyEvent}
     * @return if the key is currently pressed
     */
    public boolean isPressed(int code) {
        return keySet.contains(code);
    }

}

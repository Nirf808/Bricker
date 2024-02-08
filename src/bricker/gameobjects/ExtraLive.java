package bricker.gameobjects;

import bricker.Constants;
import bricker.main.BrickerGameManager;
import danogl.GameObject;
import danogl.collisions.Collision;
import danogl.gui.rendering.Renderable;
import danogl.util.Vector2;
/**
 * The ExtraLive class represents an extra life object in the Bricker game.
 * When collided with the main paddle, it grants an additional life to the player.
 */
public class ExtraLive extends GameObject {

    // Fields
    private final BrickerGameManager brickerGameManager; // Reference to the game manager

    /**
     * Constructs a new ExtraLive object.
     *
     * @param topLeftCorner      The top left corner of the display area of the display area
     * @param dimensions         The width and height of the object in window coordinates.
     * @param renderable         The renderable representing the object. Can be null, in which case
     *                           the GameObject will not be rendered.
     * @param brickerGameManager The BrickerGameManager instance for managing game objects.
     */
    public ExtraLive(Vector2 topLeftCorner, Vector2 dimensions, Renderable renderable,
                     BrickerGameManager brickerGameManager) {
        super(topLeftCorner, dimensions, renderable);
        this.brickerGameManager = brickerGameManager; // Set the game manager reference
    }

    /**
     * Determines whether the ExtraLive object should collide with another GameObject.
     *
     * @param other The GameObject to check collision against.
     * @return True if the other GameObject has the tag MAIN_PADDLE_TAG, false otherwise.
     */
    @Override
    public boolean shouldCollideWith(GameObject other) {
        return other.getTag().equals(Constants.MAIN_PADDLE_TAG); // Collision allowed only with the main paddle
    }

    /**
     * Handles the collision event when the ExtraLive object collides with another GameObject.
     *
     * @param other     The GameObject with which the ExtraLive object collided.
     *                  It can only be the main paddle of the game.
     * @param collision Info about the collision.
     */
    @Override
    public void onCollisionEnter(GameObject other, Collision collision) {
        super.onCollisionEnter(other, collision); // Call the superclass method

        // Delete the ExtraLive object from the game
        brickerGameManager.deleteObject(this);
        // Add an extra life to the player
        brickerGameManager.addLive();
    }
}

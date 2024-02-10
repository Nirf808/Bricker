package bricker.gameobjects;

import danogl.GameObject;
import danogl.gui.UserInputListener;
import danogl.gui.rendering.Renderable;
import danogl.util.Vector2;

import java.awt.event.KeyEvent;

/**
 * Represents a paddle GameObject in the Bricker game.
 * The paddle can be moved horizontally using keyboard input.
 */
public class Paddle extends GameObject {

    private static final float MOVEMENT_SPEED = 300; // Speed of paddle movement
    private final UserInputListener inputListener; // Listener for user input
    private final float windowWidth; // Width of the game window

    /**
     * Constructs a new Paddle object.
     *
     * @param topLeftCorner   The position of the top-left corner of the paddle.
     * @param dimensions      The dimensions (width and height) of the paddle.
     * @param renderable      The renderable object used to render the paddle.
     * @param inputListener   The input listener for user interactions with the paddle.
     * @param windowWidth     The width of the game window.
     */
    public Paddle(Vector2 topLeftCorner, Vector2 dimensions,
                  Renderable renderable, UserInputListener inputListener, float windowWidth) {
        super(topLeftCorner, dimensions, renderable);
        this.inputListener = inputListener;
        this.windowWidth = windowWidth;
    }

    /**
     * Updates the state of the paddle based on user input.
     *
     * @param deltaTime The time elapsed since the last update.
     */
    @Override
    public void update(float deltaTime) {
        super.update(deltaTime);

        Vector2 movementDir = Vector2.ZERO;

        // Check if the left arrow key is pressed
        if (inputListener.isKeyPressed(KeyEvent.VK_LEFT)) {
            // Move the paddle left if it's within the window bounds
            if (this.getTopLeftCorner().x() > 0) {
                movementDir = movementDir.add(Vector2.LEFT);
            } else {
                // Adjust the position if it's out of bounds
                this.setTopLeftCorner(new Vector2(0, this.getTopLeftCorner().y()));
            }
        }

        // Check if the right arrow key is pressed
        if (inputListener.isKeyPressed(KeyEvent.VK_RIGHT)) {
            // Move the paddle right if it's within the window bounds
            if (this.getTopLeftCorner().x() < this.windowWidth - this.getDimensions().x()) {
                movementDir = movementDir.add(Vector2.RIGHT);
            } else {
                // Adjust the position if it's out of bounds
                this.setTopLeftCorner(new Vector2(
                        this.windowWidth - this.getDimensions().x(), this.getTopLeftCorner().y()));
            }
        }

        // Set the velocity based on the movement direction and speed
        setVelocity(movementDir.mult(MOVEMENT_SPEED));
    }
}

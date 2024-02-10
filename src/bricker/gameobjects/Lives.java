package bricker.gameobjects;

import bricker.main.Constants;
import bricker.main.BrickerGameManager;
import danogl.GameObject;
import danogl.collisions.Layer;
import danogl.gui.ImageReader;
import danogl.gui.rendering.Renderable;
import danogl.gui.rendering.TextRenderable;
import danogl.util.Vector2;

import java.awt.*;

import static java.lang.String.format;

/**
 * The Lives class represents the player's remaining lives in the Bricker game.
 * It manages the display and management of player lives, including adding lives, losing lives,
 * and updating the visual representation of lives on the screen.
 */
public class Lives extends GameObject {

    // Constants
    private static final int INITIALIZE_LIVES = 3;
    private static final int MAX_LIVES = 4;

    // Fields
    private int lives; // Current number of lives
    private final GameObject[] livesObjs; // Array to store the lives GameObjects
    private final TextRenderable textRenderable; // Text renderer for displaying the number of lives
    private final BrickerGameManager brickerGameManager; // The Game Manager that the class manages

    /**
     * Constructor to initialize the Lives object.
     *
     * @param topLeftCorner      The top-left corner position of the lives display area.
     * @param dimensions         The dimensions (size) of each life GameObject.
     * @param renderable         The renderable object for rendering the lives.
     * @param imageReader        The image reader object for reading images.
     * @param brickerGameManager The game manager that the class manages the lives in.
     */
    public Lives(Vector2 topLeftCorner, Vector2 dimensions, Renderable renderable,
                 ImageReader imageReader, BrickerGameManager brickerGameManager) {
        super(topLeftCorner, dimensions, renderable);
        this.lives = INITIALIZE_LIVES;
        this.brickerGameManager = brickerGameManager;
        this.livesObjs = new GameObject[MAX_LIVES + 1];

        // Create inner objects for rendering lives
        this.textRenderable = new TextRenderable(format("%d", lives));
        Renderable livesImage = imageReader.readImage(Constants.LIVE_IMAGE, true);
        updateLivesColor(); // Update text color based on the number of lives

        // Create text and image objects for each life
        livesObjs[0] = new GameObject(topLeftCorner,
                new Vector2(dimensions), textRenderable);
        for (int i = 1; i < livesObjs.length; i++) {
            livesObjs[i] = new GameObject(
                    new Vector2(topLeftCorner.x() + (dimensions.x() + Constants.HEART_SPACING) * i,
                            topLeftCorner.y()),
                    new Vector2(dimensions), livesImage);
        }

        // Add life objects to the game manager's UI layer
        for (int i = 0; i < lives + 1; i++) {
            brickerGameManager.addObject(livesObjs[i], Layer.UI);
        }
    }

    /**
     * Adds a life to the player's remaining lives if the maximum limit has not been reached.
     */
    public void addLive() {
        if(lives < MAX_LIVES) {
            lives ++;
            updateLivesColor();
            brickerGameManager.addObject(livesObjs[lives], Layer.UI);
        }
    }

    /**
     * Decreases the player's remaining lives by one if lives are greater than 0.
     */
    public void loseLive() {
        if (lives > 0) {
            //Removes the visual representation of the lost life from the screen.
            brickerGameManager.deleteObject(livesObjs[lives], Layer.UI);
            lives--;
            updateLivesColor();
        }
    }

    /**
     * Gets the current number of lives.
     *
     * @return The current number of lives.
     */
    public int getLives() {
        return lives;
    }

    /**
     * Updates the color and text representation of lives based on the current number of lives.
     */
    private void updateLivesColor() {
        switch (lives){
            case 0:
                break;
            case 1:
                textRenderable.setColor(Color.red);
                break;
            case 2:
                textRenderable.setColor(Color.yellow);
                break;
            default:
                textRenderable.setColor(Color.green);
                break;
        }
        textRenderable.setString(Integer.toString(lives));
    }
}

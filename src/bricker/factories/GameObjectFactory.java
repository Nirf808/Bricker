package bricker.factories;

import bricker.gameobjects.Ball;
import bricker.gameobjects.ExtraLive;
import bricker.gameobjects.ExtraPaddle;
import bricker.gameobjects.Paddle;
import bricker.main.BrickerGameManager;
import danogl.gui.ImageReader;
import danogl.gui.Sound;
import danogl.gui.SoundReader;
import danogl.gui.UserInputListener;
import danogl.gui.rendering.Renderable;
import danogl.util.Vector2;
/**
 * The GameObjectFactory class is responsible for creating instances of various game objects
 * used in the Bricker game.
 */
public class GameObjectFactory {

    // Fields
    private final ImageReader imageReader; // Reader for loading images
    private final SoundReader soundReader; // Reader for loading sounds

    /**
     * Constructs a new GameObjectFactory object.
     *
     * @param imageReader The image reader instance for reading images.
     * @param soundReader The sound reader instance for reading sounds.
     */
    public GameObjectFactory(ImageReader imageReader, SoundReader soundReader) {
        this.imageReader = imageReader; // Set the image reader reference
        this.soundReader = soundReader; // Set the sound reader reference
    }

    /**
     * Creates an instance of the ExtraLive power-up.
     *
     * @param brickerGameManager The BrickerGameManager instance for managing game objects.
     * @param imagePath          The file path to the image representing the ExtraLive power-up.
     * @param dimensions         The dimensions (size) of the ExtraLive power-up.
     * @param centerLocation     The center location of the ExtraLive power-up.
     * @param extraLiveSpeed          The speed vector of the ExtraLive power-up.
     * @return An instance of the ExtraLive power-up.
     */
    public ExtraLive createLive(BrickerGameManager brickerGameManager,
                                String imagePath, Vector2 dimensions, Vector2 centerLocation,
                                Vector2 extraLiveSpeed) {
        Renderable liveImage = imageReader.readImage(imagePath, true); // Read the image
        //initialize the extraLive to the top left corner of the screen.
        //since the object will move, the position of the top left corner doesn't matter.
        ExtraLive extraLive = new ExtraLive(Vector2.ZERO, dimensions, liveImage, brickerGameManager);
        extraLive.setCenter(centerLocation); // Set the center location
        extraLive.setVelocity(extraLiveSpeed); // Set the velocity
        return extraLive;
    }

    /**
     * Creates an instance of the Ball object.
     *
     * @param brickerGameManager The BrickerGameManager instance for managing game objects.
     * @param imagePath          The file path to the image representing the ball.
     * @param soundPath          The file path to the sound for collision events.
     * @param size               The size of the ball.
     * @param centerLocation     The center location of the ball.
     * @param ballSpeed          The speed vector of the ball.
     * @return An instance of the Ball object.
     */
    public Ball createBall(BrickerGameManager brickerGameManager,
                           String imagePath,
                           String soundPath,
                           float size,
                           Vector2 centerLocation,
                           Vector2 ballSpeed) {
        Renderable ballImage = imageReader.readImage(imagePath, true); // Read the ball image
        Sound collisionSound = soundReader.readSound(soundPath); // Read the collision sound
        //initialize the ball to the top left corner of the screen.
        //since the object will move, the position of the top left corner doesn't matter.
        Ball ball = new Ball(brickerGameManager, Vector2.ZERO, new Vector2(size, size), ballImage,
                collisionSound);
        ball.setCenter(centerLocation); // Set the center location
        ball.setVelocity(ballSpeed); // Set the velocity
        return ball;
    }

    /**
     * Creates an instance of the Paddle object.
     *
     * @param brickerGameManager The BrickerGameManager instance for managing game objects.
     * @param paddleImage        The file path to the image representing the paddle.
     * @param paddleDimensions   The dimensions (size) of the paddle.
     * @param inputListener      The user input listener for handling input events.
     * @param windowWidth        The width of the game window.
     * @param paddleCenter       The center location of the paddle.
     * @param isExtraPuddle      Indicates whether the paddle is an extra paddle.
     * @return An instance of the Paddle object.
     */
    public Paddle createPaddle(BrickerGameManager brickerGameManager,
                               String paddleImage,
                               Vector2 paddleDimensions,
                               UserInputListener inputListener,
                               float windowWidth,
                               Vector2 paddleCenter,
                               boolean isExtraPuddle) {
        Paddle paddle; // Declare a reference to Paddle
        // Check if the paddle is an extra paddle
        if (isExtraPuddle) {
            paddle = new ExtraPaddle(brickerGameManager,
                    Vector2.ZERO, paddleDimensions,
                    imageReader.readImage(paddleImage, true),
                    inputListener,
                    windowWidth);
        } else {
            paddle = new Paddle(Vector2.ZERO, paddleDimensions,
                    imageReader.readImage(paddleImage, true),
                    inputListener,
                    windowWidth);
        }
        paddle.setCenter(paddleCenter); // Set the center location
        return paddle;
    }
}

package bricker.main;

import danogl.collisions.Layer;

/**
 * Class containing constants used throughout the Bricker game.
 */
public class Constants {

    /**
     * Path to the image file for the standard ball.
     */
    public static String BALL_IMAGE =  "./assets/ball.png";

    /**
     * Path to the sound file for the ball collision.
     */
    public static String BALL_SOUND = "./assets/blop.wav";

    /**
     * Path to the image file for the puck ball.
     */
    public static String PUCK_BALL_IMAGE = "./assets/mockBall.png";

    /**
     * Path to the image file for the paddle.
     */
    public static String PADDLE_IMAGE = "assets/paddle.png";

    /**
     * Path to the image file for the heart symbol representing lives.
     */
    public static String LIVE_IMAGE = "./assets/heart.png";

    /**
     * Diameter of the ball.
     */
    public static float BALL_SIZE = 20;

    /**
     * Speed of the ball.
     */
    public static final float BALL_SPEED = 200;

    /**
     * Size of the heart symbol representing lives.
     */
    public static final float HEART_SIZE = 20;

    /**
     * Spacing between heart symbols representing lives.
     */
    public static final float HEART_SPACING = 2;

    /**
     * Tag for identifying the main paddle.
     */
    public static final String MAIN_PADDLE_TAG = "Main Paddle";

    /**
     * Maximum number of collision strategies that can be applied to a single brick.
     */
    public static final int MAX_MULTIPLE_STRATEGIES = 3;

    /**
     * Width of the paddle.
     */
    public static final float PADDLE_WIDTH = 100;

    /**
     * Length of the paddle.
     */
    public static final float PADDLE_LENGTH = 15;

    /**
     * Height of the paddle.
     */
    public static final float PADDLE_HEIGHT = 30;

    /**
     * Width of the walls.
     */
    public static final float WALLS_WIDTH = 5;

    /**
     * Length of the game window.
     */
    public static final float WINDOW_LENGTH = 700;

    /**
     * Height of the game window.
     */
    public static final float WINDOW_HEIGHT = 500;

    /**
     * Height of a brick.
     */
    public static final float BRICK_HEIGHT = 15;

    /**
     * Factor used to determine the spacing between bricks along the X-axis.
     */
    public static final float SPACING_X_FACTOR = 2;

    /**
     * Factor used to determine the spacing between bricks along the Y-axis.
     */
    public static final float SPACING_Y_FACTOR = 1;

    /**
     * Default number of bricks per row.
     */
    public static final int DEFAULT_BRICKS_PER_ROW = 8;

    /**
     * Default number of rows of bricks.
     */
    public static final int DEFAULT_ROWS = 8;


    /**
     * Layer of the camera follower in the game
     */
    public static final int CAMERA_FOLLOWER_LAYER = Layer.BACKGROUND;
}

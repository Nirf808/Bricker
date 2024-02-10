package bricker.main;

import bricker.brick_strategies.AdditionalPaddleStrategy;
import bricker.factories.GameObjectFactory;
import bricker.factories.StrategyFactory;
import bricker.gameobjects.Ball;
import bricker.gameobjects.Brick;
import bricker.gameobjects.Lives;
import danogl.GameManager;
import danogl.GameObject;
import danogl.collisions.Layer;
import danogl.components.CoordinateSpace;
import danogl.gui.ImageReader;
import danogl.gui.SoundReader;
import danogl.gui.UserInputListener;
import danogl.gui.WindowController;
import danogl.gui.rendering.Renderable;
import danogl.util.Vector2;

import java.util.Random;

/**
 * The game manager for the Bricker game.
 * Responsible for initializing game elements, updating game state, handling collisions, and managing game objects.
 */
public class BrickerGameManager extends GameManager {
    private final int bricksPerRow; // Number of bricks per row
    private final int rows; // Number of rows of bricks
    private Lives lives; // Player lives
    private int totalBricks; // Total number of bricks in the game
    private Ball ball; // The main ball object

    private Vector2 windowDimensions; // Dimensions of the game window
    private WindowController windowController; // Controller for the game window
    private UserInputListener inputListener; // Listener for user input
    private GameObjectFactory gameObjectFactory; // Factory for creating game objects
    private final StrategyFactory strategyFactory; // Factory for creating collision strategies

    /**
     * Constructs a new BrickerGameManager with default number of bricks per row and rows.
     *
     * @param windowTitle      Title of the game window.
     * @param windowDimensions Dimensions of the game window.
     */
    public BrickerGameManager(String windowTitle, Vector2 windowDimensions) {
        this(windowTitle, windowDimensions, Constants.DEFAULT_BRICKS_PER_ROW, Constants.DEFAULT_ROWS);
    }

    /**
     * Constructs a new BrickerGameManager.
     *
     * @param windowTitle      Title of the game window.
     * @param windowDimensions Dimensions of the game window.
     * @param bricksPerRow     Number of bricks per row.
     * @param rows             Number of rows of bricks.
     */
    public BrickerGameManager(String windowTitle, Vector2 windowDimensions, int bricksPerRow, int rows) {
        super(windowTitle, windowDimensions);
        this.bricksPerRow = bricksPerRow;
        this.rows = rows;
        this.strategyFactory = new StrategyFactory(this);
    }

    /**
     * Initializes the game, including game objects, collision strategies, and other elements.
     *
     * @param imageReader      Image reader for loading images.
     * @param soundReader      Sound reader for loading sounds.
     * @param inputListener    User input listener.
     * @param windowController Window controller for managing the game window.
     */
    @Override
    public void initializeGame(ImageReader imageReader, SoundReader soundReader,
                               UserInputListener inputListener, WindowController windowController) {
        super.initializeGame(imageReader, soundReader, inputListener, windowController);
        windowDimensions = windowController.getWindowDimensions();
        this.windowController = windowController;
        this.inputListener = inputListener;
        this.totalBricks = bricksPerRow * rows;
        this.gameObjectFactory = new GameObjectFactory(imageReader, soundReader);

        AdditionalPaddleStrategy.setHasExtraPaddle(false);

        // Create game elements
        createBall();
        createPaddle(inputListener);
        createWalls();
        createBackground(imageReader);
        createBricks(imageReader);
        createLives(imageReader);
    }

    /**
     * Retrieves the dimensions of the game window.
     *
     * @return The dimensions of the game window.
     */
    public Vector2 getWindowDimensions() {
        return windowDimensions;
    }

    /**
     * Retrieves the user input listener.
     *
     * @return The user input listener.
     */
    public UserInputListener getUserInput() {
        return inputListener;
    }

    /**
     * Retrieves the main ball object.
     *
     * @return The main ball object.
     */
    public Ball getMainBall() {
        return ball;
    }

    /**
     * Retrieves the game object factory.
     *
     * @return The game object factory.
     */
    public GameObjectFactory getGameObjectFactory() {
        return gameObjectFactory;
    }

    /**
     * Adds a life to the player.
     */
    public void addLive() {
        lives.addLive();
    }

    /**
     * Updates the game state.
     *
     * @param deltaTime Time elapsed since the last update.
     */
    @Override
    public void update(float deltaTime) {
        super.update(deltaTime);
        checkForGameEnd();
    }

    /**
     * Deletes a brick GameObject from the game, updating the total brick count if successful.
     *
     * @param obj The brick GameObject to delete.
     */
    public void deleteBrick(GameObject obj) {
        if (gameObjects().removeGameObject(obj, Layer.STATIC_OBJECTS)) {
            totalBricks--;
        }
    }

    /**
     * Deletes a GameObject from the game.
     *
     * @param obj The GameObject to delete.
     */
    public void deleteObject(GameObject obj) {
        gameObjects().removeGameObject(obj);
    }

    /**
     * Deletes a GameObject from a specific layer in the game.
     *
     * @param obj     The GameObject to delete.
     * @param layerId The layer ID from which to delete the GameObject.
     */
    public void deleteObject(GameObject obj, int layerId) {
        gameObjects().removeGameObject(obj, layerId);
    }

    /**
     * Adds a GameObject to the game.
     *
     * @param Obj The GameObject to add.
     */
    public void addObject(GameObject Obj) {
        gameObjects().addGameObject(Obj);
    }

    /**
     * Adds a GameObject to a specific layer in the game.
     *
     * @param Obj   The GameObject to add.
     * @param layer The layer to which to add the GameObject.
     */
    public void addObject(GameObject Obj, int layer) {
        gameObjects().addGameObject(Obj, layer);
    }


    private void checkForGameEnd() {
        float ballHeight = ball.getCenter().y();
        //lost live
        if (ballHeight > windowDimensions.y()) {
            lives.loseLive();
            //defeat
            if (lives.getLives() == 0) {
                if (windowController.openYesNoDialog("You lose! Try again?")) {
                    windowController.resetGame();
                } else {
                    windowController.closeWindow();
                }
            }
            //still has lives
            else {
                resetMainBall(ball);
            }
        }
        //victory
        if (totalBricks <= 0) {
            if (windowController.openYesNoDialog("You Won! Try again?")) {
                windowController.resetGame();
            } else {
                windowController.closeWindow();
            }
        }
    }

    private void resetMainBall(Ball ball) {
        ball.setCenter(windowDimensions.mult(0.5F));
        this.ball = ball;
        this.gameObjects().addGameObject(ball);
        ball.setVelocity(chooseBallDirection());
    }

    private void createBall() {
        Vector2 ballSpeed = chooseBallDirection();

        this.ball = gameObjectFactory.createBall(this, Constants.BALL_IMAGE,
                Constants.BALL_SOUND, Constants.BALL_SIZE, windowDimensions.mult(0.5F), ballSpeed);
        this.gameObjects().addGameObject(ball);

    }

    private Vector2 chooseBallDirection() {
        float ballXSpeed = Constants.BALL_SPEED;
        float ballYSpeed = Constants.BALL_SPEED;
        Random rand = new Random();
        if (rand.nextBoolean()) {
            ballXSpeed *= -1;
        }
        if (rand.nextBoolean()) {
            ballYSpeed *= -1;
        }
        return new Vector2(ballXSpeed, ballYSpeed);
    }

    private void createPaddle(
                              UserInputListener inputListener) {
        GameObject paddle = this.gameObjectFactory.createPaddle(this,
                "assets/paddle.png",  new Vector2(Constants.PADDLE_WIDTH, Constants.PADDLE_LENGTH),
                inputListener, windowDimensions.x(),
                new Vector2(windowDimensions.x() / 2, windowDimensions.y() - Constants.PADDLE_HEIGHT),
                false);
        paddle.setTag(Constants.MAIN_PADDLE_TAG);
        this.gameObjects().addGameObject(paddle);
    }

    private void createWalls() {
        GameObject leftWall =
                new GameObject(Vector2.ZERO,
                        new Vector2(windowDimensions.x(), Constants.WALLS_WIDTH),
                        null);
        this.gameObjects().addGameObject(leftWall, Layer.STATIC_OBJECTS);
        GameObject topWall =
                new GameObject(Vector2.ZERO,
                        new Vector2(Constants.WALLS_WIDTH, windowDimensions.y()),
                        null);
        this.gameObjects().addGameObject(topWall, Layer.STATIC_OBJECTS);
        GameObject rightWall =
                new GameObject(new Vector2(windowDimensions.x() - Constants.WALLS_WIDTH + 1, 0),
                        new Vector2(Constants.WALLS_WIDTH, windowDimensions.y()),
                        null);
        this.gameObjects().addGameObject(rightWall, Layer.STATIC_OBJECTS);
    }

    private void createBackground(ImageReader imageReader) {
        Renderable backgroundImage =
                imageReader.readImage("assets/DARK_BG2_small.jpeg", false);
        GameObject background = new GameObject(Vector2.ZERO,
                new Vector2(windowDimensions.x(), windowDimensions.y()),
                backgroundImage);
        background.setCoordinateSpace(CoordinateSpace.CAMERA_COORDINATES);
        gameObjects().addGameObject(background, Layer.BACKGROUND);
    }

    private void createBricks(ImageReader imageReader) {
        Renderable brickImage = imageReader.readImage("./assets/brick.png", false);
        float width =
                (windowDimensions.x() - Constants.WALLS_WIDTH * 2 - Constants.SPACING_X_FACTOR * bricksPerRow) / bricksPerRow;
        for (int row = 0; row < this.rows; row++) {
            for (int col = 0; col < this.bricksPerRow; col++) {
                Brick brick = new Brick(
                        new Vector2((width + Constants.SPACING_X_FACTOR) * col + Constants.SPACING_X_FACTOR / 2 + Constants.WALLS_WIDTH,
                                (Constants.BRICK_HEIGHT + Constants.SPACING_Y_FACTOR) * row + Constants.WALLS_WIDTH),
                        new Vector2(width, Constants.BRICK_HEIGHT),
                        brickImage, strategyFactory.createStrategy(new Random().nextInt(10)));
                this.gameObjects().addGameObject(brick, Layer.STATIC_OBJECTS);
            }

        }
    }

    private void createLives(ImageReader imageReader) {
        this.lives = new Lives(new Vector2(0, windowDimensions.y() - Constants.HEART_SIZE),
                new Vector2(Constants.HEART_SIZE, Constants.HEART_SIZE), null, imageReader,
                this);
    }

    /**
     * The main method responsible for starting the Bricker game.
     *
     * @param args Command-line arguments (not used in this implementation).
     */
    public static void main(String[] args) {
        BrickerGameManager gameManager;
        Vector2 screenSize = new Vector2(Constants.WINDOW_LENGTH, Constants.WINDOW_HEIGHT);

        if (args.length == 0) {
            // If no arguments are provided, create a game manager with default parameters
            gameManager = new BrickerGameManager("Bricker", screenSize);
        } else {
            // If arguments are provided, create a game manager with specified parameters
            gameManager = new BrickerGameManager("Bricker", screenSize,
                    Integer.parseInt(args[0]), Integer.parseInt(args[1]));
        }

        gameManager.run();
    }

}

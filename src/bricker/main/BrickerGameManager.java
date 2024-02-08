package bricker.main;

import bricker.Constants;
import bricker.brick_strategies.AdditionalPaddleStrategy;
import bricker.brick_strategies.BasicCollisionStrategy;
import bricker.brick_strategies.PuckStrategy;
import bricker.factories.BallFactory;
import bricker.factories.GameObjectFactory;
import bricker.factories.PaddleFactory;
import bricker.gameobjects.Ball;
import bricker.gameobjects.Brick;
import bricker.gameobjects.Lives;
import bricker.gameobjects.Paddle;
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

public class BrickerGameManager extends GameManager {

    private static final float BALL_SIZE = 20;
    private static final float BALL_SPEED = 200;
    private static final float PADDLE_WIDTH = 100;
    private static final float PADDLE_LENGTH = 15;
    private static final float PADDLE_HEIGHT = 30;
    private static final float WALLS_WIDTH = 5;
    private static final float WINDOW_LENGTH = 700;
    private static final float WINDOW_HEIGHT = 500;
    private static final float BRICK_HEIGHT = 15;
    private static final float SPACING_X_FACTOR = 2;
    private static final float SPACING_Y_FACTOR = 1;
    private static final int DEFAULT_BRICKS_PER_ROW = 8;
    private static final int DEFAULT_ROWS = 8;


    private final int bricksPerRow;
    private final int rows;
    private Lives lives;
    private int totalBricks;
    private Ball ball;

    private Vector2 windowDimensions;
    private WindowController windowController;
    private UserInputListener inputListener;
    //TODO create universal GameObjectFactory
    private BallFactory ballFactory;
    private PaddleFactory paddleFactory;
    private GameObjectFactory gameObjectFactory;


    public BrickerGameManager(String windowTitle, Vector2 windowDimensions) {
        this(windowTitle, windowDimensions, DEFAULT_BRICKS_PER_ROW, DEFAULT_ROWS);
    }

    public BrickerGameManager(String windowTitle, Vector2 windowDimensions, int bricksPerRow, int rows) {
        super(windowTitle, windowDimensions);
        this.bricksPerRow = bricksPerRow;
        this.rows = rows;

    }

    @Override
    public void initializeGame(ImageReader imageReader, SoundReader soundReader,
                               UserInputListener inputListener, WindowController windowController) {
        super.initializeGame(imageReader, soundReader, inputListener, windowController);
        //define window size
        windowDimensions = windowController.getWindowDimensions();
        this.windowController = windowController;
        this.inputListener = inputListener;
        this.totalBricks = bricksPerRow * rows;
        this.ballFactory = new BallFactory(imageReader, soundReader);
        this.paddleFactory = new PaddleFactory(imageReader);
        this.gameObjectFactory = new GameObjectFactory(imageReader, soundReader);


        AdditionalPaddleStrategy.setHasExtraPuddle(false);
        //TODO maybe define GameObject factory?


        //create ball
        createBall(imageReader, soundReader);

        //create paddle
        createPaddle(imageReader, inputListener);

        //create walls
        createWalls();

        //create background
        createBackground(imageReader);

        //create brick
        createBricks(imageReader);

        //create lives
        createLives(imageReader);
    }


    @Override
    public void update(float deltaTime) {
        super.update(deltaTime);
        checkForGameEnd();

    }
    //TODO double code, merge with chooseBallDirection

    private void resetMainBall(Ball ball) {
        ball.setCenter(windowDimensions.mult(0.5F));
        this.ball = ball;
        this.gameObjects().addGameObject(ball);

        float ballXSpeed = BALL_SPEED;
        float ballYSpeed = BALL_SPEED;
        Random rand = new Random();
        if(rand.nextBoolean()) {
            ballXSpeed *= -1;
        }
        if(rand.nextBoolean()){
            ballYSpeed *= -1;
        }
        ball.setVelocity(new Vector2(ballXSpeed, ballYSpeed));
    }

    private void checkForGameEnd() {
        float ballHeight = ball.getCenter().y();
        //lost live
        if(ballHeight > windowDimensions.y()) {
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
        if(totalBricks <= 0) {
            if(windowController.openYesNoDialog("You Won! Try again?")) {
                windowController.resetGame();
            }
            else  {
                windowController.closeWindow();
            }
        }
    }

    public static void main(String[] args) {
        BrickerGameManager gameManager;
        Vector2 screenSize = new Vector2(WINDOW_LENGTH, WINDOW_HEIGHT);
        if (args.length == 0) {
            gameManager = new BrickerGameManager("Bricker", screenSize);
        } else {
            gameManager = new BrickerGameManager("Bricker", screenSize,
                    Integer.parseInt(args[0]), Integer.parseInt(args[1]));
        }
        gameManager.run();

    }

    //TODO maybe we can use tags to unite deleteBrick and deleteObject
    public void deleteBrick(GameObject obj) {
        if (gameObjects().removeGameObject(obj, Layer.STATIC_OBJECTS)) {
            totalBricks--;
        }
    }

    public void deleteObject(GameObject obj) {
        gameObjects().removeGameObject(obj);
    }

    public void deleteObject(GameObject obj, int layerId) {
        gameObjects().removeGameObject(obj, layerId);
    }

    public void addObject(GameObject Obj) {
        gameObjects().addGameObject(Obj);
    }

    public void addObject(GameObject Obj, int layer) {
        gameObjects().addGameObject(Obj, layer);
    }

    private void createBall(ImageReader imageReader, SoundReader soundReader) {
        Vector2 ballSpeed = chooseBallDirection();

        this.ball = ballFactory.createBall(this, Constants.BALL_IMAGE, Constants.BALL_SOUND,
                Constants.BALL_SIZE, windowDimensions.mult(0.5F), ballSpeed);
        this.gameObjects().addGameObject(ball);

    }

    public Vector2 chooseBallDirection() {
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

    private void createPaddle(ImageReader imageReader,
                              UserInputListener inputListener) {
        Renderable paddleImage =
                imageReader.readImage("assets/paddle.png", true);
        GameObject paddle =
                new Paddle(Vector2.ZERO, new Vector2(PADDLE_WIDTH, PADDLE_LENGTH),
                        paddleImage, inputListener, windowDimensions.x());
        paddle.setCenter(new Vector2(windowDimensions.x() / 2, windowDimensions.y() - PADDLE_HEIGHT));
        paddle.setTag(Constants.MAIN_PADDLE_TAG);
        this.gameObjects().addGameObject(paddle);
    }

    private void createWalls() {
    Renderable wallRender = null;
        //Renderable wallRender = new RectangleRenderable(Color.BLUE);
        GameObject leftWall =
                new GameObject(Vector2.ZERO,
                        new Vector2(windowDimensions.x(), WALLS_WIDTH),
                        wallRender);
        this.gameObjects().addGameObject(leftWall, Layer.STATIC_OBJECTS);
//        GameObject bottomWall =
//                new GameObject(new Vector2(0, windowDimensions.y() - WALLS_WIDTH + 1),
//                        new Vector2(windowDimensions.x(), WALLS_WIDTH),
//                        wallRender);
//        this.gameObjects().addGameObject(bottomWall, Layer.STATIC_OBJECTS);
        GameObject topWall =
                new GameObject(Vector2.ZERO,
                        new Vector2(WALLS_WIDTH, windowDimensions.y()),
                        wallRender);
        this.gameObjects().addGameObject(topWall, Layer.STATIC_OBJECTS);
        GameObject rightWall =
                new GameObject(new Vector2(windowDimensions.x() - WALLS_WIDTH + 1, 0),
                        new Vector2(WALLS_WIDTH, windowDimensions.y()),
                        wallRender);
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
        float width = (windowDimensions.x() - WALLS_WIDTH * 2 - SPACING_X_FACTOR * bricksPerRow) / bricksPerRow;
        for (int row = 0; row < this.rows; row++) {
            for (int col = 0; col < this.bricksPerRow; col++) {
                Brick brick = new Brick(
                        new Vector2((width + SPACING_X_FACTOR) * col + SPACING_X_FACTOR / 2 + WALLS_WIDTH,
                                (BRICK_HEIGHT + SPACING_Y_FACTOR) * row + WALLS_WIDTH),
                        new Vector2(width, BRICK_HEIGHT),
                        brickImage, (row == this.rows - 1 && col == this.bricksPerRow - 1) ?
                        new PuckStrategy(this): new PuckStrategy(this));
                this.gameObjects().addGameObject(brick, Layer.STATIC_OBJECTS);
            }

        }
    }

    private void createLives(ImageReader imageReader) {
        this.lives = new Lives(new Vector2(0, windowDimensions.y() - Constants.HEART_SIZE),
                new Vector2(Constants.HEART_SIZE, Constants.HEART_SIZE), null, imageReader,
                this);
    }

    public BallFactory getBallFactory() {
        return ballFactory;
    }

    public PaddleFactory getPaddleFactory() {
        return paddleFactory;
    }

    public Vector2 getWindowDimensions() {
        return windowDimensions;
    }

    public UserInputListener getUserInput() {
        return inputListener;
    }

    public Ball getMainBall() {
        return ball;
    }
    public GameObjectFactory getGameObjectFactory() {
        return gameObjectFactory;
    }
    public void addLive() { lives.addLive();}
}

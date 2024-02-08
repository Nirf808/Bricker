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

public class GameObjectFactory {
    private final ImageReader imageReader;
    private final SoundReader soundReader;

    public GameObjectFactory(ImageReader imageReader, SoundReader soundReader){
        this.imageReader = imageReader;
        this.soundReader = soundReader;
    }

    public ExtraLive createLive(BrickerGameManager brickerGameManager,
            String imagePath, Vector2 dimensions, Vector2 centerLocation,
                                Vector2 liveSpeed) {
        Renderable liveImage = imageReader.readImage(imagePath, true);
        ExtraLive extraLive = new ExtraLive(Vector2.ZERO, dimensions, liveImage, brickerGameManager);
        extraLive.setCenter(centerLocation);
        extraLive.setVelocity(liveSpeed);
        return extraLive;
    }

    public Ball createBall(BrickerGameManager brickerGameManager,
                           String imagePath,
                           String soundPath,
                           float size,
                           Vector2 centerLocation,
                           Vector2 ballSpeed) {
        Renderable ballImage =
                imageReader.readImage(imagePath, true);
        Sound collisionSound = soundReader.readSound(soundPath);
        Ball ball =
                new Ball(brickerGameManager, Vector2.ZERO, new Vector2(size, size), ballImage,
                        collisionSound);
        ball.setCenter(centerLocation);
        ball.setVelocity(ballSpeed);
        return ball;
    }

    public Paddle createPaddle(BrickerGameManager brickerGameManager,
                               String paddleImage,
                               Vector2 paddleDimensions,
                               UserInputListener inputListener,
                               float windowWidth,
                               Vector2 paddleCenter,
                               boolean isExtraPuddle) {
        Paddle paddle = null;

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

        paddle.setCenter(paddleCenter);
        return paddle;
    }
}

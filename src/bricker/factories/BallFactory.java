package bricker.factories;

import bricker.gameobjects.Ball;
import bricker.main.BrickerGameManager;
import danogl.gui.ImageReader;
import danogl.gui.Sound;
import danogl.gui.SoundReader;
import danogl.gui.rendering.Renderable;
import danogl.util.Vector2;

public class BallFactory {
    private ImageReader imageReader;
    private SoundReader soundReader;

    public BallFactory(ImageReader imageReader, SoundReader soundReader) {
        this.imageReader = imageReader;
        this.soundReader = soundReader;
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
}

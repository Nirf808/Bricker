package bricker.factories;

import bricker.gameobjects.Ball;
import bricker.gameobjects.ExtraLive;
import bricker.main.BrickerGameManager;
import danogl.gui.ImageReader;
import danogl.gui.Sound;
import danogl.gui.SoundReader;
import danogl.gui.rendering.Renderable;
import danogl.util.Vector2;

public class GameObjectFactory {
    private ImageReader imageReader;
    private SoundReader soundReader;

    public GameObjectFactory(ImageReader imageReader, SoundReader soundReader){
        this.imageReader = imageReader;
        this.soundReader = soundReader;
    }

    public ExtraLive createLive(Vector2 dimensions, String imagePath, Vector2 center,
                                BrickerGameManager brickerGameManager) {
        Renderable liveImage = imageReader.readImage(imagePath, true);
        ExtraLive extraLive = new ExtraLive(Vector2.ZERO, dimensions, liveImage, brickerGameManager);
        extraLive.setCenter(center);
        extraLive.setVelocity(Vector2.DOWN.mult(100));
        return extraLive;
    }
}

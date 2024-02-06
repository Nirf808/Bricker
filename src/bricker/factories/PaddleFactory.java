package bricker.factories;

import bricker.gameobjects.ExtraPaddle;
import bricker.gameobjects.Paddle;
import bricker.main.BrickerGameManager;
import danogl.gui.ImageReader;
import danogl.gui.UserInputListener;
import danogl.util.Vector2;

public class PaddleFactory {
    private ImageReader imageReader;

    public PaddleFactory(ImageReader imageReader) {
        this.imageReader = imageReader;
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

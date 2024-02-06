package bricker.gameobjects;

import bricker.main.BrickerGameManager;
import danogl.GameObject;
import danogl.gui.UserInputListener;
import danogl.gui.rendering.Renderable;
import danogl.util.Vector2;

import java.awt.event.KeyEvent;

public class Paddle extends GameObject {
    private static final float MOVEMENT_SPEED = 300;
    private UserInputListener inputListener;
    private float windowWidth;

    public Paddle(Vector2 topLeftCorner, Vector2 dimensions,
                  Renderable renderable, UserInputListener inputListener, float x) {
        super(topLeftCorner, dimensions, renderable);
        this.inputListener = inputListener;
        windowWidth = x;
    }

    @Override
    public void update(float deltaTime) {
        super.update(deltaTime);
        Vector2 movementDir = Vector2.ZERO;
        if(inputListener.isKeyPressed(KeyEvent.VK_LEFT)) {
            if (this.getTopLeftCorner().x() > 0) {
                movementDir = movementDir.add(Vector2.LEFT);
            }
            else {
                this.setTopLeftCorner(new Vector2(0, this.getTopLeftCorner().y()));
            }
        }
        if(inputListener.isKeyPressed(KeyEvent.VK_RIGHT)) {
            if(this.getTopLeftCorner().x() < this.windowWidth - this.getDimensions().x()) {
                movementDir = movementDir.add(Vector2.RIGHT);
            }
            else {
                this.setTopLeftCorner(new Vector2(
                        this.windowWidth - this.getDimensions().x(), this.getTopLeftCorner().y()));
            }
        }

        setVelocity(movementDir.mult(MOVEMENT_SPEED));
    }
}

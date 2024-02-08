package bricker.gameobjects;

import bricker.Constants;
import bricker.main.BrickerGameManager;
import danogl.GameObject;
import danogl.collisions.Collision;
import danogl.gui.rendering.Renderable;
import danogl.util.Vector2;

public class ExtraLive extends GameObject {


    private BrickerGameManager brickerGameManager;

    /**
     * Construct a new GameObject instance.
     *
     * @param topLeftCorner      Position of the object, in window coordinates (pixels).
     *                           Note that (0,0) is the top-left corner of the window.
     * @param dimensions         Width and height in window coordinates.
     * @param renderable         The renderable representing the object. Can be null, in which case
     *                           the GameObject will not be rendered.
     * @param brickerGameManager
     */
    public ExtraLive(Vector2 topLeftCorner, Vector2 dimensions, Renderable renderable,
                     BrickerGameManager brickerGameManager) {
        super(topLeftCorner, dimensions, renderable);
        this.brickerGameManager = brickerGameManager;
    }

    @Override
    public boolean shouldCollideWith(GameObject other) {
        return other.getTag().equals(Constants.MAIN_PADDLE_TAG);
    }

    @Override
    public void onCollisionEnter(GameObject other, Collision collision) {
        super.onCollisionEnter(other, collision);

        brickerGameManager.deleteObject(this);
        brickerGameManager.addLive();
    }
}

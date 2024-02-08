package bricker.gameobjects;

import bricker.main.BrickerGameManager;
import danogl.GameObject;
import danogl.collisions.Layer;
import danogl.gui.rendering.Camera;
import danogl.util.Vector2;

public class CameraFollower extends GameObject {
    public static int cameraFollowerLayer = Layer.BACKGROUND;
    private final int initCollisions;
    private BrickerGameManager brickerGameManager;

    public CameraFollower(BrickerGameManager brickerGameManager) {
        super(Vector2.ZERO, Vector2.ZERO, null);
        this.brickerGameManager = brickerGameManager;
        this.initCollisions = brickerGameManager.getMainBall().getCollisionCounter();
        setManagerCamera();
    }

    @Override
    public void update(float deltaTime) {
        super.update(deltaTime);
        if (brickerGameManager.getMainBall().getCollisionCounter() > initCollisions + 4){
            rollBackCamera();
            brickerGameManager.deleteObject(this, cameraFollowerLayer);
        }
    }

    private void setManagerCamera() {
        brickerGameManager.setCamera(
                new Camera(
                        brickerGameManager.getMainBall(), //object to follow
                        Vector2.ZERO, //follow the center of the object
                        brickerGameManager.getWindowDimensions().mult(1.2f), //widen the frame a bit
                        brickerGameManager.getWindowDimensions() //share the window dimensions
                )
        );
    }

    private void rollBackCamera(){
        brickerGameManager.setCamera(null);
    }
}

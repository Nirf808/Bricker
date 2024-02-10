package bricker.brick_strategies;

import danogl.GameObject;

/**
 * Interface representing a collision strategy between game objects.
 * Implementing classes define the behavior when a collision occurs.
 */
public interface CollisionStrategy {

    /**
     * Defines the behavior when a collision occurs between two game objects.
     *
     * @param thisObj  The colliding object.
     * @param otherObj The collided object.
     */
    void onCollision(GameObject thisObj, GameObject otherObj);
}
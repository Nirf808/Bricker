package bricker.brick_strategies;

import danogl.GameObject;

/**
 * @// TODO: 08/02/2024 how to describe these?
 */
public interface CollisionStrategy {
    /**
     * the behaviour when collision occurs
     * @param thisObj the colliding object
     * @param otherObj the collided object
     */
    void onCollision(GameObject thisObj, GameObject otherObj);
}

package bricker.gameobjects;

import bricker.Constants;
import bricker.main.BrickerGameManager;
import danogl.GameObject;
import danogl.collisions.Layer;
import danogl.gui.ImageReader;
import danogl.gui.rendering.Renderable;
import danogl.gui.rendering.TextRenderable;
import danogl.util.Vector2;

import java.awt.*;

import static java.lang.String.format;

public class Lives extends GameObject {
    private static final int INITIALIZE_LIVES = 3;
    private static final int MAX_LIVES = 4;
    private int lives;
    private final GameObject[] livesObjs;
    private TextRenderable textRenderable;
    private final BrickerGameManager brickerGameManager;


    public Lives(Vector2 topLeftCorner, Vector2 dimensions, Renderable renderable,
                 ImageReader imageReader, BrickerGameManager brickerGameManager) {
        super(topLeftCorner, dimensions, renderable);
        this.lives = INITIALIZE_LIVES;
        this.brickerGameManager = brickerGameManager;
        this.livesObjs = new GameObject[MAX_LIVES + 1];
        //create inner objects
        //renderers
        this.textRenderable = new TextRenderable(format("%d", lives));
        Renderable livesImage = imageReader.readImage(Constants.LIVE_IMAGE, true);
        //text color
        updateLives();
        //text object
        float initialeTopLeftCornerY = topLeftCorner.y() - dimensions.y();
        livesObjs[0] = new GameObject(new Vector2(topLeftCorner.x(), initialeTopLeftCornerY),
                new Vector2(dimensions), textRenderable);
        for (int i = 1; i < livesObjs.length; i++) {
            livesObjs[i] = new GameObject(
                    new Vector2(topLeftCorner.x() + (dimensions.x() + Constants.HEART_SPACING) * i,
                            initialeTopLeftCornerY),
                    new Vector2(dimensions), livesImage);
        }
        //
        for (int i = 0; i < lives + 1; i++) {
            brickerGameManager.addObject(livesObjs[i], Layer.UI);
        }
    }
    public void addLive() {
        if(lives < MAX_LIVES) {
            lives ++;
            updateLives();
            brickerGameManager.addObject(livesObjs[lives], Layer.UI);
        }
    }

    public void loseLive() {
        if (lives > 0) {
            brickerGameManager.deleteObject(livesObjs[lives], Layer.UI);
            lives--;
            updateLives();
        }
    }

    public int getLives() {
        return lives;
    }

    private void updateLives() {
        switch (lives){
            case 0:
                break;
            case 1:
                textRenderable.setColor(Color.red);
                break;
            case 2:
                textRenderable.setColor(Color.yellow);
                break;
            default:
                textRenderable.setColor(Color.green);
                break;
        }
        textRenderable.setString(Integer.toString(lives));
    }

}

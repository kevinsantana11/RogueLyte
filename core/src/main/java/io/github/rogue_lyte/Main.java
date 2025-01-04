package io.github.rogue_lyte;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.FitViewport;

/** {@link com.badlogic.gdx.ApplicationListener} implementation shared by all platforms. */
public class Main extends ApplicationAdapter {
    private SpriteBatch batch;
    private FitViewport viewport;
    private Player player;
    private TextureManager txManager;
    private List<GameObject> gameObjects;

    public Main() {
        this.gameObjects = new ArrayList<>();
    }

    @Override
    public void create() {
        batch = new SpriteBatch();
        viewport = new FitViewport(8, 5);
        txManager = new TextureManager(Map.of(
            "background", new Texture("terrain/L1_Grass.PNG"),
            "player", new Texture("players/battlemage.gif"),
            "fireball", new Texture("spells/Fireball.png")

        ));

        player = new Player(txManager.get("player"), 1, 1, 4);
        gameObjects.add(player);
    }

    @Override
    public void resize(int width, int height) {
        viewport.update(width, height, true);
    }

    @Override
    public void render() {
        float deltaTime = Gdx.graphics.getDeltaTime();
        processInput(deltaTime);
        drawScreen(deltaTime);
    }
    
    private void drawScreen(float deltaTime) {
        // Setup the camera to follow the player
        viewport.getCamera().position.set(player.getX(), player.getY(), 0);
        viewport.getCamera().update();
        batch.setProjectionMatrix(viewport.getCamera().combined);

        // Clear the screen
        ScreenUtils.clear(Color.GRAY);

        batch.begin();

        float worldWidth = viewport.getWorldWidth();
        float worldHeight = viewport.getWorldWidth();

        float xOrigin = 0;
        float yOrigin = 0;

        // Draw level
        Texture backgroundTexture = txManager.get("background");
        batch.draw(backgroundTexture, xOrigin, yOrigin, worldWidth, worldHeight);
        batch.draw(backgroundTexture, xOrigin + worldWidth / 2, yOrigin + worldHeight / 2, worldWidth, worldHeight);
        batch.draw(backgroundTexture, xOrigin - worldWidth / 2, yOrigin + worldHeight / 2, worldWidth, worldHeight);
        batch.draw(backgroundTexture, xOrigin + worldWidth / 2, yOrigin - worldHeight / 2, worldWidth, worldHeight);
        batch.draw(backgroundTexture, xOrigin - worldWidth / 2, yOrigin - worldHeight / 2, worldWidth, worldHeight);
        batch.draw(backgroundTexture, xOrigin + worldWidth, yOrigin, worldWidth, worldHeight);

        for (GameObject go : gameObjects) {
            go.draw(deltaTime, batch);
        }

        batch.end();
    }

    private void processInput(float deltaTime) {
        List<Action> actions = player.processInputs(deltaTime);

        for (Action action : actions) {
            if (action instanceof InvokeSpell) {
                InvokeSpell invokeSpell = (InvokeSpell) action;

                Vector3 end = viewport.getCamera().unproject(new Vector3(invokeSpell.end.x, invokeSpell.end.y, 0));
                Projectile projectile = new Projectile(
                    txManager.get("fireball"), 
                    1, 
                    1,
                    invokeSpell.start,
                    new Vector2(end.x, end.y),
                    2f
                );
                gameObjects.add(projectile);
            }
        }
    }

    @Override
    public void dispose() {
        batch.dispose();
        txManager.cleanup();
    }
}

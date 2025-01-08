package io.github.roguelyte;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.FitViewport;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Random;

/** {@link com.badlogic.gdx.ApplicationListener} implementation shared by all platforms. */
public class Main extends ApplicationAdapter {
    private SpriteBatch batch;
    private ShapeRenderer shapeRenderer;
    private FitViewport viewport;
    private Map<String, Texture> txMap;
    private Game game;

    @Override
    public void create() {
        batch = new SpriteBatch();
        viewport = new FitViewport(8, 5);
        txMap =
                Map.of(
                        "background", new Texture("terrain/L1_Grass.PNG"),
                        "player", new Texture("players/battlemage.gif"),
                        "demon", new Texture("players/demon.gif"),
                        "fireball", new Texture("spells/Fireball.png"));

        shapeRenderer = new ShapeRenderer();

        Player player = new Player(txMap.get("player"), 1, 1, 100, 4);
        Character enemy = new Character("demon", txMap.get("demon"), 1, 1, 100, 4);

        List<Character> characters = new ArrayList<>();
        characters.add(player);
        characters.add(enemy);

        float worldWidth = viewport.getWorldWidth();
        float worldHeight = viewport.getWorldWidth();

        float xOrigin = 0;
        float yOrigin = 0;

        Level level = new Level(txMap.get("background"), xOrigin, yOrigin, worldWidth, worldHeight);

        game = new Game(level, player, characters, new ArrayList<>(), new Random());

    }

    @Override
    public void resize(int width, int height) {
        viewport.update(width, height, true);
    }

    @Override
    public void render() {
        float deltaTime = Gdx.graphics.getDeltaTime();

        // Setup the camera to follow the player
        viewport.getCamera().position.set(game.getPlayer().getSprite().getX(), game.getPlayer().getSprite().getY(), 0);
        viewport.getCamera().update();
        batch.setProjectionMatrix(viewport.getCamera().combined);
        shapeRenderer.setProjectionMatrix(viewport.getCamera().combined);

        // Clear the screen
        ScreenUtils.clear(Color.GRAY);

        // Draw sprites
        batch.begin();
        game.step(deltaTime, viewport, txMap);
        game.drawSprites(deltaTime, batch);
        batch.end();

        // Draw Shapes
        game.drawShapes(deltaTime, shapeRenderer);
    }

    @Override
    public void dispose() {
        batch.dispose();
        for (Entry<String, Texture> entry : txMap.entrySet()) {
            entry.getValue().dispose();
        }
    }
}

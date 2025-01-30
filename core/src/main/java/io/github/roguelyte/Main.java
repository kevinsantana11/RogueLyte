package io.github.roguelyte;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.FitViewport;
import io.github.roguelyte.actors.Character;
import io.github.roguelyte.actors.Player;
import io.github.roguelyte.configs.GOConfig;
import io.github.roguelyte.configs.PhysicsConfig;
import io.github.roguelyte.configs.ProjectileConfig;
import io.github.roguelyte.core.Level;
import io.github.roguelyte.core.ProjectileFactory;
import io.github.roguelyte.core.Spawner;
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
        viewport = new FitViewport(400, 300);
        txMap =
                Map.of(
                        "player", new Texture("players/battlemage.gif"),
                        "demon", new Texture("players/demon.gif"),
                        "fireball", new Texture("spells/Fireball.png"));

        shapeRenderer = new ShapeRenderer();

        Player player =
                new Player(
                        txMap.get("player"),
                        new GOConfig(20, 20, 0, 0),
                        new PhysicsConfig(200f),
                        100,
                        Map.of(
                                Input.Keys.Q,
                                new ProjectileFactory(
                                        "fireball",
                                        txMap.get("fireball"),
                                        viewport.getCamera(),
                                        new ProjectileConfig(50, 60),
                                        new GOConfig(20, 20, 0, 0),
                                        new PhysicsConfig(64f),
                                        new Random())));
        List<Character> characters = new ArrayList<>();
        characters.add(player);

        Level level =
                new Level(
                        new TmxMapLoader().load("levels/lvl_0.tmx"),
                        batch,
                        (OrthographicCamera) viewport.getCamera(),
                        16,
                        16,
                        new Spawner(
                                "demon",
                                txMap.get("demon"),
                                new GOConfig(20, 20, 0, 0),
                                new PhysicsConfig(64f),
                                100,
                                new Random(),
                                1f));

        game = new Game(level, player, characters, new ArrayList<>());
    }

    @Override
    public void resize(int width, int height) {
        viewport.update(width, height, true);
    }

    @Override
    public void render() {
        float deltaTime = Gdx.graphics.getDeltaTime();
        game.step(deltaTime);

        // Setup the camera to follow the player
        viewport.getCamera()
                .position
                .set(game.getPlayer().getSprite().getX(), game.getPlayer().getSprite().getY(), 0);
        viewport.getCamera().update();
        batch.setProjectionMatrix(viewport.getCamera().combined);
        shapeRenderer.setProjectionMatrix(viewport.getCamera().combined);

        // Clear the screen
        ScreenUtils.clear(Color.GRAY);

        game.drawLevel(deltaTime, batch);

        // Draw sprites
        batch.begin();
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

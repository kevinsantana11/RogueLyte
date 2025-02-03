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
import com.badlogic.gdx.utils.viewport.FillViewport;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

import io.github.roguelyte.actions.Action;
import io.github.roguelyte.actors.Character;
import io.github.roguelyte.actors.Enemy;
import io.github.roguelyte.actors.AcquirableItem;
import io.github.roguelyte.actors.Player;
import io.github.roguelyte.configs.GOConfig;
import io.github.roguelyte.configs.PhysicsConfig;
import io.github.roguelyte.configs.ProjectileConfig;
import io.github.roguelyte.core.Item;
import io.github.roguelyte.core.Level;
import io.github.roguelyte.core.ProjectileFactory;
import io.github.roguelyte.core.Spawner;
import io.github.roguelyte.core.Stats;
import io.github.roguelyte.ui.UI;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Random;

/** {@link com.badlogic.gdx.ApplicationListener} implementation shared by all platforms. */
public class Main extends ApplicationAdapter {
    private SpriteBatch batch;
    private ShapeRenderer shapeRenderer;
    private Viewport viewport;
    private Viewport screenport;

    private Map<String, Texture> txMap;
    private Game game;
    private UI ui;

    @Override
    public void create() {
        Random random = new Random();
        float worldWidth = 400;
        float worldHeight = 300;
        viewport = new FillViewport(worldWidth, worldHeight);
        screenport = new FitViewport(worldWidth, worldHeight);
        batch = new SpriteBatch();
        txMap =
                Map.of(
                        "player", new Texture("players/battlemage.gif"),
                        "demon", new Texture("players/demon.gif"),
                        "fireball", new Texture("spells/Fireball.png"),
                        "sword", new Texture("items/Sword01.PNG"));

        shapeRenderer = new ShapeRenderer();

        Player player =
                new Player(
                        txMap.get("player"),
                        new GOConfig(20, 20, 0, 0),
                        new PhysicsConfig(2f),
                        100,
                        Map.of(
                                Input.Keys.Q,
                                new ProjectileFactory(
                                        "fireball",
                                        txMap.get("fireball"),
                                        viewport.getCamera(),
                                        new ProjectileConfig(50, 160),
                                        new GOConfig(20, 20, 0, 0),
                                        new PhysicsConfig(160f),
                                        random)));
        List<Character> characters = new ArrayList<>();
        characters.add(player);
        Spawner<AcquirableItem> itemSpawner = new Spawner<AcquirableItem>(() -> new AcquirableItem(
            new Item(String.format("sword-%d", random.nextInt()), txMap.get("sword"), new Stats(100f, 20f, 5f, 15f)),
            new GOConfig(20, 20),
            5f));
        Spawner<Character> spawner = new Spawner<>(random,
                                1f, () -> new Enemy(
                                    "demon",
                                    txMap.get("demon"),
                                    new GOConfig(20, 20, 0, 0),
                                    new PhysicsConfig(1f),
                                    100, itemSpawner));

        Level level = new Level(new TmxMapLoader().load("levels/lvl_0.tmx"),
                        batch,
                        (OrthographicCamera) viewport.getCamera(),
                        16,
                        16);

        game = new Game(level, player, characters, new ArrayList<>(), new ArrayList<>(), spawner);
        ui = new UI(screenport, game);
    }

    @Override
    public void resize(int width, int height) {
        screenport.update(width, height);
        viewport.update(width, height);
    }

    @Override
    public void render() {
        float deltaTime = Gdx.graphics.getDeltaTime();

        // Sim the game
        this.run(deltaTime);

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
        
        // Draw UI
        ui.draw();
    }

    @Override
    public void dispose() {
        batch.dispose();
        ui.dispose();
        for (Entry<String, Texture> entry : txMap.entrySet()) {
            entry.getValue().dispose();
        }
    }

    public void run(float deltaTime) {
        List<Action> allActions = new ArrayList<>();
        allActions.addAll(game.getActions(deltaTime));
        allActions.addAll(ui.getActions(deltaTime));
        game.process(deltaTime, allActions);
    }
}

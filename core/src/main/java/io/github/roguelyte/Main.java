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
import io.github.roguelyte.actors.AcquirableItem;
import io.github.roguelyte.actors.Player;
import io.github.roguelyte.adapters.EnemyAdapter;
import io.github.roguelyte.adapters.ItemAdapter;
import io.github.roguelyte.configs.GOConfig;
import io.github.roguelyte.configs.PhysicsConfig;
import io.github.roguelyte.core.Level;
import io.github.roguelyte.core.ProjectileFactory;
import io.github.roguelyte.core.Spawner;
import io.github.roguelyte.core.Stats.StatBuilder;
import io.github.roguelyte.core.Stats.StatsBuilder;
import io.github.roguelyte.db.Db;
import io.github.roguelyte.db.tables.Enemies;
import io.github.roguelyte.db.tables.Items;
import io.github.roguelyte.ui.UI;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.function.Supplier;
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
        Db database = new Db("config/hibernate/cfg.xml");
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

        ProjectileFactory projectileFactory = new ProjectileFactory(
            "sword",
            txMap.get("sword"),
            viewport.getCamera(),
            new GOConfig(32, 32, 1/2f, 1/2f),
            new PhysicsConfig(80f),
            new StatsBuilder(
                new StatBuilder("health", Map.entry(0f, 0f)),
                new StatBuilder("armor", Map.entry(0f, 0f)),
                new StatBuilder("speed", Map.entry(0f, 0f)),
                new StatBuilder("dmg", Map.entry(50f, 50f))),
            random,
            80f);

        Player player = new Player(
            txMap.get("player"),
            new GOConfig(32, 32, 1/2f, 1/2f),
            new PhysicsConfig(2f),
            new StatsBuilder(
                new StatBuilder("health", Map.entry(100f, 100f)),
                new StatBuilder("armor", Map.entry(0f, 0f)),
                new StatBuilder("speed", Map.entry(0f, 0f)),
                new StatBuilder("dmg", Map.entry(0f, 0f))).build(random),
            Map.of(Input.Keys.Q, projectileFactory));
        List<Character> characters = new ArrayList<>();
        characters.add(player);

        List<ItemAdapter> items = database.with(sess -> {
            return Items.allItems(sess).stream()
                .map(dbItem -> new ItemAdapter(sess, dbItem)).toList();
        });
        List<Supplier<AcquirableItem>> itemSuppliers = items.stream()
            .map(itm -> {
                Supplier<AcquirableItem> supp = () -> new AcquirableItem(itm.build(random), new GOConfig(32, 32), 5f);
                return supp;
            }) .toList();
        Spawner<AcquirableItem> itemSpawner = new Spawner<AcquirableItem>(random, itemSuppliers);

        List<EnemyAdapter> enemies = database.with(sess -> {
            return Enemies.allEnemies(sess).stream()
                .map(dbEnemy -> 
                    new EnemyAdapter(
                        sess,
                        dbEnemy,
                        new PhysicsConfig(1f),
                        new GOConfig(32, 32),
                        itemSpawner))
                .toList();
        });
        List<Supplier<Character>> enemySuppliers = enemies.stream()
            .map(enemy -> {
                Supplier<Character> supp = () -> enemy.build(random);
                return supp;
            }) .toList();

        Spawner<Character> spawner = new Spawner<>(random, 1f, enemySuppliers);

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

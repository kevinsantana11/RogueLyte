package io.github.roguelyte;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
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
    private Player player;
    private final List<Character> characters;
    private final List<Projectile> projectiles;
    private final Random rand;

    public Main() {
        characters = new ArrayList<>();
        projectiles = new ArrayList<>();
        rand = new Random();
    }

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

        player = new Player(txMap.get("player"), shapeRenderer, 1, 1, 100, 4);
        Enemy enemy = new Enemy("demon", txMap.get("demon"), shapeRenderer, 1, 1, 100);

        characters.add(player);
        characters.add(enemy);
    }

    @Override
    public void resize(int width, int height) {
        viewport.update(width, height, true);
    }

    @Override
    public void render() {
        float deltaTime = Gdx.graphics.getDeltaTime();
        gameLogic();
        processInput(deltaTime);
        drawScreen(deltaTime);
    }

    private void gameLogic() {
        System.out.println(
                String.format(
                        "Processing %d projectile events for %d characters",
                        projectiles.size(), characters.size()));
        for (Projectile proj : projectiles) {
            for (Character character : characters) {
                float x = proj.getSprite().getX();
                float y = proj.getSprite().getY();
                if (character.getSprite().getBoundingRectangle().contains(x, y)
                        && !character.equals(proj.originator)) {
                    character.hit(proj.getAmt(), proj.getId());
                }
            }
        }
    }

    private void drawScreen(float deltaTime) {
        // Setup the camera to follow the player
        viewport.getCamera().position.set(player.getX(), player.getY(), 0);
        viewport.getCamera().update();
        batch.setProjectionMatrix(viewport.getCamera().combined);
        shapeRenderer.setProjectionMatrix(viewport.getCamera().combined);

        // Clear the screen
        ScreenUtils.clear(Color.GRAY);

        batch.begin();

        float worldWidth = viewport.getWorldWidth();
        float worldHeight = viewport.getWorldWidth();

        float xOrigin = 0;
        float yOrigin = 0;

        // Draw level
        Texture backgroundTexture = txMap.get("background");
        batch.draw(backgroundTexture, xOrigin, yOrigin, worldWidth, worldHeight);
        batch.draw(
                backgroundTexture,
                xOrigin + worldWidth / 2,
                yOrigin + worldHeight / 2,
                worldWidth,
                worldHeight);
        batch.draw(
                backgroundTexture,
                xOrigin - worldWidth / 2,
                yOrigin + worldHeight / 2,
                worldWidth,
                worldHeight);
        batch.draw(
                backgroundTexture,
                xOrigin + worldWidth / 2,
                yOrigin - worldHeight / 2,
                worldWidth,
                worldHeight);
        batch.draw(
                backgroundTexture,
                xOrigin - worldWidth / 2,
                yOrigin - worldHeight / 2,
                worldWidth,
                worldHeight);
        batch.draw(backgroundTexture, xOrigin + worldWidth, yOrigin, worldWidth, worldHeight);

        // Draw game objects like characters (player / enemy) or projectiles
        List<GameObject> cleanupList = new ArrayList<>();
        for (GameObject go : characters) {
            boolean cleanup = go.drawSprites(deltaTime, batch);

            if (cleanup) {
                cleanupList.add(go);
            }
        }
        characters.removeAll(cleanupList);

        cleanupList = new ArrayList<>();
        for (GameObject go : projectiles) {
            boolean cleanup = go.drawSprites(deltaTime, batch);

            if (cleanup) {
                cleanupList.add(go);
            }
        }
        projectiles.removeAll(cleanupList);
        batch.end();

        for (GameObject go : characters) {
            go.drawShapes(deltaTime, shapeRenderer);
        }
    }

    private void processInput(float deltaTime) {
        List<Action> actions = player.processInputs(deltaTime);
        System.out.println(String.format("Got %d actions from processing inputs", actions.size()));

        for (Action action : actions) {
            if (action instanceof InvokeSpell) {
                InvokeSpell invokeSpell = (InvokeSpell) action;

                Vector3 end =
                        viewport.getCamera()
                                .unproject(new Vector3(invokeSpell.end.x, invokeSpell.end.y, 0));
                Projectile projectile =
                        new Projectile(
                                player,
                                String.format("fireball-%d", rand.nextInt()),
                                txMap.get("fireball"),
                                1,
                                1,
                                invokeSpell.start,
                                new Vector2(end.x, end.y),
                                2f,
                                25,
                                5);
                projectiles.add(projectile);
            }
        }
    }

    @Override
    public void dispose() {
        batch.dispose();
        for (Entry<String, Texture> entry : txMap.entrySet()) {
            entry.getValue().dispose();
        }
    }
}

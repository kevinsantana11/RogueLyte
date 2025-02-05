package io.github.roguelyte;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;

import io.github.roguelyte.actions.Action;
import io.github.roguelyte.actions.Spawn;
import io.github.roguelyte.actors.Actor;
import io.github.roguelyte.actors.Character;
import io.github.roguelyte.actors.AcquirableItem;
import io.github.roguelyte.actors.Player;
import io.github.roguelyte.core.Drawable;
import io.github.roguelyte.core.GO;
import io.github.roguelyte.core.Level;
import io.github.roguelyte.core.Projectile;
import io.github.roguelyte.core.Spawner;

import java.util.ArrayList;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
@AllArgsConstructor
public class Game {
    @Getter private Level level;
    @Getter private Player player;
    @Getter private List<Character> characters;
    @Getter private List<AcquirableItem> items;
    private List<Projectile> projectiles;
    private Spawner<Character> spawner;

    public List<Action> getActions(float deltaTime) {
        List<Action> actions = new ArrayList<>();
        getActors().forEach((actor) -> { actions.addAll(actor.act(deltaTime)); });
        List<Action> spawnActions = spawn(deltaTime);
        actions.addAll(spawnActions);
        return actions;
    }

   public void process(float deltaTime, List<Action> actions) {
        actions.forEach((action) -> action.apply(this));
        cleanup();
    }

    public List<Actor> getActors() {
        List<Actor> actors = new ArrayList<>();
        actors.addAll(characters);
        actors.addAll(projectiles);
        actors.addAll(items);
        return actors;
    }

    public List<Drawable> getDrawables() {
        List<Drawable> drawables = new ArrayList<>();
        drawables.addAll(characters);
        drawables.addAll(projectiles);
        drawables.addAll(items);
        return drawables;
    }

    public void addItem(AcquirableItem item) {
        items.add(item);
    }

    public void addProjectile(Projectile proj) {
        projectiles.add(proj);
    }

    public void addCharacater(Character character) {
        characters.add(character);
    }

    public List<Action> spawn(float deltaTime) {
        List<Action> actions = new ArrayList<>();
        spawner.tick(deltaTime);
        List<Vector2> spawnPoints = level.getSpawnPoints();

        for (Vector2 spawnPoint : spawnPoints) {
            Spawn<Character> spawnChar = spawner.trySpawn(spawnPoint.x, spawnPoint.y);
            if ((spawnChar = spawner.trySpawn(spawnPoint.x, spawnPoint.y)) != null) {
                actions.add(spawnChar);
            }
        }
        return actions;
    }

    private void cleanup() {
        List<GO> cleanupList = new ArrayList<>();

        for (Character character : characters) {
            if (character.canCleanup()) {
                cleanupList.add(character);
            }
        }
        characters.removeAll(cleanupList);

        cleanupList = new ArrayList<>();
        for (Projectile proj : projectiles) {
            if (proj.canCleanup()) {
                cleanupList.add(proj);
            }
        }
        projectiles.removeAll(cleanupList);

        cleanupList = new ArrayList<>();
        for (AcquirableItem item : items) {
            if (item.canCleanup()) {
                cleanupList.add(item);
            }
        }
        items.removeAll(cleanupList);
    }

    public void drawLevel(float deltaTime, SpriteBatch batch) {
        level.drawSprites(deltaTime, batch);
    }

    public void drawSprites(float deltaTime, SpriteBatch batch) {
        getDrawables().forEach((drawable) -> drawable.drawSprites(deltaTime, batch));
    }

    public void drawShapes(float deltaTime, ShapeRenderer shapeRenderer) {
        getDrawables().forEach((drawable) -> drawable.drawShapes(deltaTime, shapeRenderer));
    }
}

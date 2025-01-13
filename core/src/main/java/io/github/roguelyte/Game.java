package io.github.roguelyte;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import io.github.roguelyte.actors.Character;
import io.github.roguelyte.actors.Player;
import io.github.roguelyte.core.GO;
import io.github.roguelyte.core.Level;
import io.github.roguelyte.core.Projectile;
import java.util.ArrayList;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
public class Game {
    @Getter private Level level;
    @Getter private Player player;
    @Getter private List<Character> characters;
    private List<Projectile> projectiles;

    public void step(float deltaTime) {
        processActions(deltaTime);
        sim(deltaTime);
    }

    public void addProjectile(Projectile proj) {
        projectiles.add(proj);
    }

    public void addCharacater(Character character) {
        characters.add(character);
    }

    private void sim(float deltaTime) {
        List<GO> cleanupList = new ArrayList<>();
        for (Projectile proj : projectiles) {
            proj.step(deltaTime);
            for (Character character : characters) {
                float x = proj.getSprite().getX();
                float y = proj.getSprite().getY();
                if (character.getSprite().getBoundingRectangle().contains(x, y)
                        && !character.equals(proj.getOriginator())) {
                    character.hit(proj.getProjectileConfig().getDamage(), proj.getId());
                }
            }

            if (proj.canCleanup()) {
                cleanupList.add(proj);
            }
        }
        projectiles.removeAll(cleanupList);

        cleanupList = new ArrayList<>();
        for (Character character : characters) {
            if (character.canCleanup()) {
                cleanupList.add(character);
            }
        }
        characters.removeAll(cleanupList);
    }

    private void processActions(float deltaTime) {
        level.spawn(deltaTime).forEach((a) -> a.apply(this));
        player.act(deltaTime).forEach((a) -> a.apply(this));
    }

    public void drawLevel(float deltaTime, SpriteBatch batch) {
        level.drawSprites(deltaTime, batch);
    }

    public void drawSprites(float deltaTime, SpriteBatch batch) {
        List<GO> drawables = new ArrayList<>(characters);
        drawables.addAll(projectiles);

        for (GO drawable : drawables) {
            drawable.drawSprites(deltaTime, batch);
        }
    }

    public void drawShapes(float deltaTime, ShapeRenderer shapeRenderer) {
        List<GO> drawables = new ArrayList<>(characters);
        drawables.addAll(projectiles);
        drawables.addAll(characters);
        for (GO drawable : drawables) {
            drawable.drawShapes(deltaTime, shapeRenderer);
        }
    }
}

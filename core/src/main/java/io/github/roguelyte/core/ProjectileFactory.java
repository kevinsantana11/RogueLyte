package io.github.roguelyte.core;

import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import io.github.roguelyte.actors.Character;
import io.github.roguelyte.configs.GOConfig;
import io.github.roguelyte.configs.PhysicsConfig;
import io.github.roguelyte.configs.ProjectileConfig;
import io.github.roguelyte.core.Stats.StatsBuilder;

import java.util.Random;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class ProjectileFactory {
    private final String spellName;
    private final Texture texture;
    private final Camera camera;
    private final ProjectileConfig projectileConfig;
    private final GOConfig config;
    private final PhysicsConfig physics;
    private final StatsBuilder statsBuilder;
    private final Random rand;

    /**
     * Creates a new projectile from screen coordinates.
     * @param originator The character casting the projectile
     * @param screenX The x screen coordinate of the target
     * @param screenY The y screen coordinate of the target
     * @return A new Projectile instance
     */
    public Projectile createFromScreenCoords(Character originator, int screenX, int screenY) {
        Vector3 worldCoords = camera.unproject(new Vector3(screenX, screenY, 0));
        float startx =
                originator.getSprite().getX()
                        + (originator.getSprite().getWidth() / 2 - (config.getWidth() / 2));
        float starty =
                originator.getSprite().getY()
                        + (originator.getSprite().getHeight() / 2 - (config.getHeight() / 2));
        Vector2 start = new Vector2(startx, starty);
        Vector2 end = new Vector2(worldCoords.x, worldCoords.y);

        return new Projectile(
                originator,
                String.format("%s-%d", spellName, rand.nextInt()),
                texture,
                new GOConfig(config.getWidth(), config.getHeight(), start.x, start.y),
                physics,
                projectileConfig,
                statsBuilder.build(rand),
                end);
    }
}

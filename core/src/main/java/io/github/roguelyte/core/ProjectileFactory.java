package io.github.roguelyte.core;

import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import io.github.roguelyte.actors.Character;
import io.github.roguelyte.configs.GOConfig;
import io.github.roguelyte.configs.PhysicsConfig;
import io.github.roguelyte.core.Stats.StatsBuilder;
import io.github.roguelyte.movement.LinearRange;
import io.github.roguelyte.movement.Meele;

import java.util.Random;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class ProjectileFactory {
    private final String spellName;
    private final Texture texture;
    private final Camera camera;
    private final GOConfig config;
    private final PhysicsConfig physics;
    private final StatsBuilder statsBuilder;
    private final Random rand;
    private final float maxDistance;

    /**
     * Creates a new projectile from screen coordinates.
     * @param originator The character casting the projectile
     * @param screenX The x screen coordinate of the target
     * @param screenY The y screen coordinate of the target
     * @return A new Projectile instance
     */
    public Projectile createFromScreenCoords(Character originator, int screenX, int screenY) {
        Vector3 worldCoords = camera.unproject(new Vector3(screenX, screenY, 0));
        Vector2 start = new Vector2(originator.getSprite().getX(), originator.getSprite().getY());
        Vector2 end = new Vector2(worldCoords.x, worldCoords.y);
        GOConfig newConfig = new GOConfig(
            config.getWidth(),
            config.getHeight(),
            start.x,
            start.y,
            config.getScalex(),
            config.getScaley());

        return new Projectile(
                originator,
                String.format("%s-%d", spellName, rand.nextInt()),
                texture,
                newConfig,
                statsBuilder.build(rand),
                new LinearRange(originator, physics, newConfig, maxDistance, end),
                // new Meele(originator, physics, newConfig, end, 40),
                end);
    }
}

package io.github.roguelyte;

import com.badlogic.gdx.graphics.Texture;

import java.util.Random;

import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class ProjectileFactory {
    private final String spellName;
    private final Texture texture;
    private final Camera camera;
    private final ProjectileConfig projConfig;
    private final GOConfig config;
    private final PhysicsConfig physics;
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
        Vector2 start = new Vector2(originator.getSprite().getX(), originator.getSprite().getY());
        Vector2 end = new Vector2(worldCoords.x, worldCoords.y);
        
        return new Projectile(
            originator,
            String.format("%s-%d", spellName, rand.nextInt()),
            texture,
            config.getWidth(),
            config.getHeight(),
            start,
            end,
            physics.getSpeed(),
            projConfig.getDamage(),
            projConfig.getMaxDistance()
        );
    }
}

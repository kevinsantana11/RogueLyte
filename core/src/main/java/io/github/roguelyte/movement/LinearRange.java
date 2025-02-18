package io.github.roguelyte.movement;

import com.badlogic.gdx.math.Vector2;

import io.github.roguelyte.actors.Character;
import io.github.roguelyte.configs.GOConfig;
import io.github.roguelyte.configs.PhysicsConfig;

public class LinearRange extends Movement {
    Vector2 initialPosition;
    float maxDistance;
    float currDistance;

    public LinearRange(Character character, PhysicsConfig physics, GOConfig config, float maxDistance, Vector2 end) {
        super(character, physics, config, end);
        this.initialPosition = character.getCenter();
        this.maxDistance = maxDistance;
        this.currDistance = 0;
    }

    @Override
    public Vector2 getStart() {
        return initialPosition;
    }

    @Override
    public Vector2 to(Float deltaTime) {
        float distdx = deltaTime * this.physics.getSpeed();
        currDistance += distdx;
        float rot = getRot();
        float xdist = distdx * (float) Math.cos(Math.toRadians(rot));
        float ydist = distdx * (float) Math.sin(Math.toRadians(rot));
        return new Vector2(xdist, ydist);
    }

    @Override
    public Vector2 origin() {
        return new Vector2(
            character.getSprite().getWidth() * character.getSprite().getScaleX() / 2,
            character.getSprite().getHeight() * character.getSprite().getScaleY() / 2);
    }

	@Override
	public boolean isDone() {
        return currDistance >= maxDistance;
	}

    @Override
    public Vector2 getFlip() {
        return new Vector2(0, 0);
    }
}

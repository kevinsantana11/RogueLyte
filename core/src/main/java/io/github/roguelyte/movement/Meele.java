package io.github.roguelyte.movement;

import com.badlogic.gdx.math.Vector2;

import io.github.roguelyte.actors.Character;
import io.github.roguelyte.configs.GOConfig;
import io.github.roguelyte.configs.PhysicsConfig;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class Meele extends Movement {
    private float maxAngle;
    private float currAngle;
    private Vector2 currPos;
    private float stateTime;
    private float lifetime;

    public Meele(Character character, PhysicsConfig physics, GOConfig config, Vector2 end, float lifetime) {
        super(character, physics, config, end);
        Vector2 ray = getRay();
        float ang = ray.angleDeg();

        this.currAngle = ang;
        this.maxAngle = currAngle + 90;
        this.stateTime = 0;
        this.lifetime = lifetime;

        float mag = (float) Math.sqrt((double) character.getSize().dot(character.getSize()));
        this.currPos = character.getCenter().add(new Vector2(
            (float) Math.cos((double) Math.toRadians(currAngle)) * mag, 
            (float) Math.sin((double) Math.toRadians(currAngle)) * mag));
        log.info("Meele attack settings, max angle {}, curr angle {}, ray angle {}", maxAngle, currAngle, ang);
    }

    @Override
    public Vector2 getStart() {
        return currPos;
    }

	@Override
	public Vector2 to(Float deltaTime) {
        stateTime += deltaTime;
        currAngle += physics.getSpeed() * deltaTime;

        float mag = (float) Math.sqrt((double) character.getSize().dot(character.getSize()));
        Vector2 newPosition = character.getCenter()
            .add(new Vector2(
                (float) Math.cos((double) Math.toRadians(currAngle)) * mag * character.getSprite().getScaleX(),
                (float) Math.sin((double) Math.toRadians(currAngle)) * mag * character.getSprite().getScaleY()));
        Vector2 translation = new Vector2(newPosition).sub(currPos);
        currPos = newPosition;
        
        return translation;
	}

    @Override
    public Float getRot() {
        return currAngle;
    }

    @Override
    public Vector2 origin() {
        return new Vector2(
            character.getSprite().getWidth() * character.getSprite().getScaleX() / 2,
            character.getSprite().getHeight() * character.getSprite().getScaleY() / 2);
    }

	@Override
	public boolean isDone() {
        return currAngle >= maxAngle || stateTime > lifetime;
	}

    @Override
    public Vector2 getFlip() {
        return new Vector2( 0f, 0f);
    }
    
}

package io.github.roguelyte.movement;

import com.badlogic.gdx.math.Vector2;

import io.github.roguelyte.actors.Character;
import io.github.roguelyte.configs.GOConfig;
import io.github.roguelyte.configs.PhysicsConfig;

public abstract class Movement {
    Character character;
    PhysicsConfig physics;
    GOConfig config;
    Vector2 end;

    public abstract Vector2 getStart();
    public abstract Vector2 to(Float deltaTime);
    public abstract Vector2 origin();
    public abstract Vector2 getFlip();
    public abstract boolean isDone();

    protected Movement(Character character, PhysicsConfig physics, GOConfig config, Vector2 end) {
        this.character = character;
        this.physics = physics;
        this.config = config;
        this.end = end;
    }

    public Float getRot() {
        return new Vector2(end).sub(getStart()).angleDeg();
    }

    public Vector2 getRay() {
        return new Vector2(end).sub(character.getPosition());//.add(character.getSize().scl(1/2)));
    }
}

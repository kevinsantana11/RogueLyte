package io.github.roguelyte.core;

import java.util.List;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;

import io.github.roguelyte.actions.Action;
import io.github.roguelyte.actions.Translate;
import io.github.roguelyte.actors.Character;
import io.github.roguelyte.actors.Actor;
import io.github.roguelyte.configs.GOConfig;
import io.github.roguelyte.movement.Movement;
import lombok.Getter;

public class Projectile implements GO, Actor, Drawable {
    @Getter private Character originator;
    protected float stateTime;
    @Getter protected Sprite sprite;
    private GOConfig config;
    @Getter private String id;
    @Getter private Stats stats;
    private Vector2 start;
    protected Vector2 end;
    protected Movement movement;

    public Projectile(
            Character originator,
            String name,
            Texture texture,
            GOConfig config,
            Stats stats,
            Movement movement,
            Vector2 end) {
        this.id = name;
        this.originator = originator;
        this.stateTime = 0;
        this.config = config;
        this.stats = stats;
        this.movement = movement;
        this.end = end;

        sprite = new Sprite(texture);
        sprite.setSize(this.config.getWidth(), this.config.getHeight());
        sprite.setScale(this.config.getScalex(), this.config.getScaley());

        start = movement.getStart();

        sprite.setX(start.x);
        sprite.setY(start.y);
    }

    @Override
    public boolean canCleanup() {
        return movement.isDone();
    }

    @Override
    public void drawSprites(float deltaTime, SpriteBatch batch) {
        Vector2 flipvec = movement.getFlip();
        Vector2 origin = movement.origin();
        sprite.setFlip(flipvec.x == 1f, flipvec.y == 1f);
        sprite.setOrigin(origin.x, origin.y);
        sprite.setRotation(movement.getRot());
        sprite.draw(batch);
    }

    @Override
    public void drawShapes(float deltaTime, ShapeRenderer shapeRenderer) {
        shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
        shapeRenderer.setColor(1, 0, 0, 1);
        shapeRenderer.rect(
                sprite.getX(),
                sprite.getY(),
                sprite.getOriginX(),
                sprite.getOriginY(),
                sprite.getWidth(), 
                sprite.getHeight(),
                sprite.getScaleX(),
                sprite.getScaleY(),
                sprite.getRotation()
                );
        shapeRenderer.end();
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        shapeRenderer.setColor(1, 0, 0, 1);
        shapeRenderer.line(start, end);
        shapeRenderer.end();
        shapeRenderer.begin(ShapeRenderer.ShapeType.Point);
        shapeRenderer.end();
    }

	@Override
	public List<Action> act(float deltaTime) {
        stateTime += deltaTime;
        Vector2 translation = movement.to(deltaTime);
        return List.of(
            new Translate<Projectile>(this, translation.x, translation.y, false)
        );
	}
}

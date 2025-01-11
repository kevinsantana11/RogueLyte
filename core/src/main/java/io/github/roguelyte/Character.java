package io.github.roguelyte;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

import java.util.HashSet;

public class Character extends Damageable implements GO {
    Sprite sprite;
    String name;
    GOConfig config;
    PhysicsConfig physics;

    public Character(
            String name,
            Texture texture,
            GOConfig config,
            PhysicsConfig physics,
            float startingHealth) {
        super(startingHealth);
        this.name = name;
        this.config = config;
        this.physics = physics;
        this.hitby = new HashSet<>();
        sprite = new Sprite(texture);
        sprite.setSize(config.getWidth(), config.getHeight());
    }

    public Sprite getSprite() {
        return sprite;
    }

    @Override
    public void drawSprites(float deltaTime, SpriteBatch batch) {
        batch.draw(sprite.getTexture(), sprite.getX(), sprite.getY(), config.getWidth(), config.getHeight());
    }

    @Override
    public void drawShapes(float deltaTime, ShapeRenderer shapeRenderer) {
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        shapeRenderer.setColor(1, 0, 0, 1);
        float green = health / maxHealth;
        float red = 1 - green;
        shapeRenderer.setColor(0, 1, 0, 1);
        shapeRenderer.rect(
                sprite.getX(),
                sprite.getY() + sprite.getHeight() + 0.02f,
                sprite.getWidth() * green,
                .06f);
        if (red > 0) {
            shapeRenderer.setColor(1, 0, 0, 1);
            shapeRenderer.rect(
                    sprite.getX() + sprite.getWidth() * green,
                    sprite.getY() + sprite.getHeight() + 0.02f,
                    sprite.getWidth() * red,
                    .06f);
        }
        shapeRenderer.end();
    }

    @Override
    public boolean canCleanup() {
        return this.health == 0;
    }

    @Override
    public void step(float deltaTime) {}
}

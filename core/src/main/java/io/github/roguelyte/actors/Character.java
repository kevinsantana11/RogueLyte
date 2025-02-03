package io.github.roguelyte.actors;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import io.github.roguelyte.configs.GOConfig;
import io.github.roguelyte.configs.PhysicsConfig;
import io.github.roguelyte.core.Damageable;
import io.github.roguelyte.core.GO;
import io.github.roguelyte.core.HasSprite;

public abstract class Character implements GO, Actor, HasSprite {
    Sprite sprite;
    String name;
    GOConfig config;
    PhysicsConfig physics;
    Damageable healthbar;

    public Character(
            String name,
            Texture texture,
            GOConfig config,
            PhysicsConfig physics,
            float startingHealth) {
        this.name = name;
        this.config = config;
        this.physics = physics;
        this.healthbar = new Damageable(startingHealth);
        sprite = new Sprite(texture);
        sprite.setPosition(config.getX(), config.getY());
        sprite.setSize(config.getWidth(), config.getHeight());
    }

    public Sprite getSprite() {
        return sprite;
    }

    public PhysicsConfig getPhysicsConfig() {
        return this.physics;
    }

    public void hit(float amt, String id) {
        this.healthbar.hit(amt, id);
    }

    public void heal(float amt, String id) {
        this.healthbar.heal(amt);
    }

    @Override
    public void drawSprites(float deltaTime, SpriteBatch batch) {
        batch.draw(
                sprite.getTexture(),
                sprite.getX(),
                sprite.getY(),
                config.getWidth(),
                config.getHeight());
    }

    @Override
    public void drawShapes(float deltaTime, ShapeRenderer shapeRenderer) {
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        shapeRenderer.setColor(1, 0, 0, 1);
        float green = healthbar.getHealth() / healthbar.getMaxHealth();
        float red = 1 - green;
        shapeRenderer.setColor(0, 1, 0, 1);
        shapeRenderer.rect(
                sprite.getX(),
                sprite.getY() + sprite.getHeight() + 0.02f,
                sprite.getWidth() * green,
                .6f);
        if (red > 0) {
            shapeRenderer.setColor(1, 0, 0, 1);
            shapeRenderer.rect(
                    sprite.getX() + sprite.getWidth() * green,
                    sprite.getY() + sprite.getHeight() + 0.02f,
                    sprite.getWidth() * red,
                    .6f);
        }
        shapeRenderer.end();
    }

    @Override
    public boolean canCleanup() {
        return this.healthbar.getHealth() == 0;
    }
}

package io.github.roguelyte.actors;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;

import io.github.roguelyte.configs.GOConfig;
import io.github.roguelyte.configs.PhysicsConfig;
import io.github.roguelyte.core.Damageable;
import io.github.roguelyte.core.Drawable;
import io.github.roguelyte.core.GO;
import io.github.roguelyte.core.Stats;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public abstract class Character implements GO, Actor, Drawable {
    Sprite sprite;
    String name;
    @Getter GOConfig config;
    PhysicsConfig physics;
    Damageable healthbar;
    @Getter Stats stats;

    public Character(
            String name,
            Texture texture,
            GOConfig config,
            PhysicsConfig physics,
            Stats stats) {
        this.name = name;
        this.config = config;
        this.physics = physics;
        this.stats = stats;
        this.healthbar = new Damageable(this.stats.get("health"));
        sprite = new Sprite(texture);
        sprite.setPosition(config.getX(), config.getY());
        sprite.setSize(config.getWidth(), config.getHeight());
        sprite.setScale(config.getScalex(), config.getScaley());
    }

    @Override
    public String getId() {
        return this.name;
    }

    public Sprite getSprite() {
        return sprite;
    }

    public Vector2 getPosition() {
        return new Vector2(sprite.getX(), sprite.getY());
    }

    public Vector2 getSize() {
        return new Vector2(sprite.getWidth() * sprite.getScaleX(), sprite.getHeight() * sprite.getScaleY());
    }

    public Vector2 getCenter() {
        return getPosition().add(getSize().scl(1/2f));
    }

    public PhysicsConfig getPhysicsConfig() {
        return this.physics;
    }

    public void hit(Stats stats, String id) {
        Float dmg = stats.get("dmg");
        if (dmg != null && dmg != 0) {
            log.info("Character {} hit for {} damage", name, dmg);
            this.healthbar.hit(dmg, id);
        }
    }

    public void heal(float amt, String id) {
        this.healthbar.heal(amt);
    }

    @Override
    public void drawSprites(float deltaTime, SpriteBatch batch) {
        sprite.draw(batch);
    }

    @Override
    public void drawShapes(float deltaTime, ShapeRenderer shapeRenderer) {
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
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

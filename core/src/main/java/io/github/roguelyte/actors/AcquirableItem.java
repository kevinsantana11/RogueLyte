package io.github.roguelyte.actors;

import java.util.List;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

import io.github.roguelyte.actions.Action;
import io.github.roguelyte.configs.GOConfig;
import io.github.roguelyte.core.Drawable;
import io.github.roguelyte.core.GO;
import io.github.roguelyte.core.Item;
import io.github.roguelyte.core.Stats;
import lombok.Getter;
import lombok.Setter;

public class AcquirableItem implements GO, Actor, Drawable {
    private @Getter Item item;
    private @Setter boolean acquired;
    private GOConfig config;
    private @Getter Sprite sprite;
    private float stateTime;
    private float lifetime;
    

    public AcquirableItem(Item item, GOConfig config, float lifetime) {
        this.item = item;
        this.config = config;
        this.acquired = false;
        this.lifetime = lifetime;
        this.sprite = new Sprite(item.getTexture());
        sprite.setPosition(config.getX(), config.getY());
        sprite.setSize(config.getWidth(), config.getHeight());
        this.stateTime = 0;
    }

    public Stats getStats() {
        return item.getStats();
    }

    public String getId() {
        return this.item.getName();
    }

    @Override
    public boolean canCleanup() {
        return acquired || stateTime > lifetime;
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
    public void drawShapes(float deltaTime, ShapeRenderer shapeRenderer) {}

    @Override
    public List<Action> act(float deltaTime) { this.stateTime += deltaTime; return List.of(); }
}

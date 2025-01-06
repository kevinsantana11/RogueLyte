package io.github.roguelyte;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import java.util.HashSet;
import java.util.Set;

public abstract class Character extends Damageable implements GameObject {
    ShapeRenderer shapeRenderer;
    Sprite sprite;
    float width;
    float height;
    String name;
    Set<String> hitby;

    public Character(
            String name,
            Texture texture,
            ShapeRenderer shapeRenderer,
            float width,
            float height,
            float startingHealth) {
        super(startingHealth);
        this.name = name;
        this.width = width;
        this.height = height;
        this.hitby = new HashSet<>();
        sprite = new Sprite(texture);
        sprite.setSize(width, height);
        this.shapeRenderer = shapeRenderer;
    }

    public Sprite getSprite() {
        return sprite;
    }

    public void hit(float amt, String id) {
        if (!hitby.contains(id)) {
            System.out.println(String.format("%s Got hit with %f from %s", this.name, amt, id));
            super.hit(amt);
            hitby.add(id);
        }
    }

    @Override
    public boolean drawSprites(float deltaTime, SpriteBatch batch) {
        batch.draw(sprite.getTexture(), sprite.getX(), sprite.getY(), width, height);
        return this.health == 0;
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
}

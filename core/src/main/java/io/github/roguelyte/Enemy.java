package io.github.roguelyte;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

public class Enemy extends Character {
    public Enemy(
            String name,
            Texture texture,
            ShapeRenderer shapeRenderer,
            float width,
            float height,
            float startingHealth) {
        super(name, texture, shapeRenderer, width, height, startingHealth);
    }
}

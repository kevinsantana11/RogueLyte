package io.github.roguelyte.core;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

public interface Drawable {
    boolean canCleanup();

    public void drawSprites(float deltaTime, SpriteBatch batch);

    public void drawShapes(float deltaTime, ShapeRenderer shapeRenderer);
}

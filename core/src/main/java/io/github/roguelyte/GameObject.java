package io.github.roguelyte;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

public interface GameObject {
    public boolean drawSprites(float deltaTime, SpriteBatch batch);

    public void drawShapes(float deltaTime, ShapeRenderer shapeRenderer);
}

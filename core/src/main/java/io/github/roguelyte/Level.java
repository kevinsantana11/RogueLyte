package io.github.roguelyte;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class Level implements Drawable, Ephemeral, GameObject {
    Texture texture;
    float xOrigin;
    float yOrigin;
    float worldWidth;
    float worldHeight;

    @Override
    public void drawSprites(float deltaTime, SpriteBatch batch) {
        batch.draw(texture, xOrigin, yOrigin, worldWidth, worldHeight);
        batch.draw(
                texture,
                xOrigin + worldWidth / 2,
                yOrigin + worldHeight / 2,
                worldWidth,
                worldHeight);
        batch.draw(
                texture,
                xOrigin - worldWidth / 2,
                yOrigin + worldHeight / 2,
                worldWidth,
                worldHeight);
        batch.draw(
                texture,
                xOrigin + worldWidth / 2,
                yOrigin - worldHeight / 2,
                worldWidth,
                worldHeight);
        batch.draw(
                texture,
                xOrigin - worldWidth / 2,
                yOrigin - worldHeight / 2,
                worldWidth,
                worldHeight);
        batch.draw(texture, xOrigin + worldWidth, yOrigin, worldWidth, worldHeight);
    }

    @Override
    public void drawShapes(float deltaTime, ShapeRenderer shapeRenderer) { }

    @Override
    public boolean canCleanup() {
        return false;
    }

    @Override
    public void step(float deltaTime) {}
}

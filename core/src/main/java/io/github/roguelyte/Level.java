package io.github.roguelyte;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;

public class Level implements GO {
    private OrthogonalTiledMapRenderer renderer;

    public Level(TiledMap map) {
        renderer = new OrthogonalTiledMapRenderer(map);
    }


    @Override
    public void drawSprites(float deltaTime, SpriteBatch batch) {
        renderer.render();
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

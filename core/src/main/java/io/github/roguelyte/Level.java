package io.github.roguelyte;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;

public class Level implements GO {
    private OrthogonalTiledMapRenderer renderer;
    private OrthographicCamera camera;
    private TiledMap map;
    private int tileWidth;
    private int tileHeight;

    public Level(TiledMap map, SpriteBatch batch, OrthographicCamera camera, int tileWidth, int tileHeight) {
        renderer = new OrthogonalTiledMapRenderer(map, batch);
        this.camera = camera;
        this.map = map;
        this.tileWidth = tileWidth;
        this.tileHeight = tileHeight;
    }

    public boolean collides(int x, int y) {

        TiledMapTileLayer layer = (TiledMapTileLayer) map.getLayers().get("obstacle");
        TiledMapTileLayer.Cell cell = layer.getCell(x / tileWidth, y / tileHeight);

        if (cell != null) {
            Boolean val = (Boolean) cell.getTile().getProperties().get("obstacle");
            return val.booleanValue();
        }

        return false;
    }


    @Override
    public void drawSprites(float deltaTime, SpriteBatch batch) {
        renderer.setView(camera);
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

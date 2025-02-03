package io.github.roguelyte.core;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.objects.EllipseMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Vector2;

import java.util.ArrayList;
import java.util.List;

public class Level implements GO {
    private OrthogonalTiledMapRenderer renderer;
    private OrthographicCamera camera;
    private TiledMap map;
    private int tileWidth;
    private int tileHeight;

    public Level(
            TiledMap map,
            SpriteBatch batch,
            OrthographicCamera camera,
            int tileWidth,
            int tileHeight) {
        renderer = new OrthogonalTiledMapRenderer(map, batch);
        this.camera = camera;
        this.map = map;
        this.tileWidth = tileWidth;
        this.tileHeight = tileHeight;
    }

    public List<Vector2> getSpawnPoints() {
        List<Vector2> spawnPoints = new ArrayList<>();

        MapLayer layer = map.getLayers().get("spawns");
        for (EllipseMapObject mo : layer.getObjects().getByType(EllipseMapObject.class)) {
            spawnPoints.add(new Vector2(mo.getEllipse().x, mo.getEllipse().y));
        }
        return spawnPoints;
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
    public void drawShapes(float deltaTime, ShapeRenderer shapeRenderer) {}

    @Override
    public boolean canCleanup() {
        return false;
    }
}

package io.github.roguelyte.core;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.objects.EllipseMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import io.github.roguelyte.actions.Action;
import io.github.roguelyte.actions.Spawn;
import io.github.roguelyte.actors.Character;
import java.util.ArrayList;
import java.util.List;

public class Level implements GO {
    private OrthogonalTiledMapRenderer renderer;
    private OrthographicCamera camera;
    private TiledMap map;
    private int tileWidth;
    private int tileHeight;
    private Spawner spawner;

    public Level(
            TiledMap map,
            SpriteBatch batch,
            OrthographicCamera camera,
            int tileWidth,
            int tileHeight,
            Spawner spawner) {
        renderer = new OrthogonalTiledMapRenderer(map, batch);
        this.camera = camera;
        this.map = map;
        this.tileWidth = tileWidth;
        this.tileHeight = tileHeight;
        this.spawner = spawner;
    }

    public List<Action> spawn(float deltaTime) {
        List<Action> actions = new ArrayList<>();
        spawner.tick(deltaTime);
        MapLayer layer = map.getLayers().get("spawns");
        for (EllipseMapObject mo : layer.getObjects().getByType(EllipseMapObject.class)) {
            Character spawnedCharacter = spawner.trySpawn(mo.getEllipse().x, mo.getEllipse().y);
            if (spawnedCharacter != null) {
                actions.add(new Spawn(spawnedCharacter));
            }
        }
        return actions;
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

    @Override
    public void step(float deltaTime) {}
}

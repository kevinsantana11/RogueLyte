package io.github.roguelyte.adapters;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import org.hibernate.Session;

import com.badlogic.gdx.graphics.Texture;

import io.github.roguelyte.actors.AcquirableItem;
import io.github.roguelyte.actors.Enemy;
import io.github.roguelyte.configs.GOConfig;
import io.github.roguelyte.configs.PhysicsConfig;
import io.github.roguelyte.core.Spawner;
import io.github.roguelyte.core.Stats.StatBuilder;
import io.github.roguelyte.core.Stats.StatsBuilder;
import io.github.roguelyte.db.tables.Enemies;
import io.github.roguelyte.db.tables.Stats;

public class EnemyAdapter extends Adapter {
    private String name;
    private Texture texture;
    private StatsBuilder statsBuilder;
    private PhysicsConfig physics;
    private GOConfig config;
    private Spawner<AcquirableItem> itemSpawner;
    
    /** Requires a valid db session */
    public EnemyAdapter(
        Session session,
        Enemies dbEnemy,
        PhysicsConfig physics,
        GOConfig config,
        Spawner<AcquirableItem> itemSpawner) 
    {
        super(session, dbEnemy.getName());

        Map<String, Stats> statMap = new HashMap<>();
        for (Stats stat : dbEnemy.getEntity().getStats()) {
            statMap.put(stat.getName(), stat);
        }

        statsBuilder = new StatsBuilder(
            new StatBuilder("health", statMap.get("health").toEntry()),
            new StatBuilder("armor", statMap.get("armor").toEntry()),
            new StatBuilder("speed", statMap.get("speed").toEntry()),
            new StatBuilder("dmg", statMap.get("dmg").toEntry()));

        this.texture = new Texture(dbEnemy.getAssetpath());
        this.physics = physics;
        this.config = config;
        this.itemSpawner = itemSpawner;
    }

    public Enemy build(Random rand) {
        return new Enemy(
            getName(rand),
            texture,
            config,
            physics,
            statsBuilder.build(rand),
            itemSpawner);
    }
}
package io.github.roguelyte.adapters;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import org.hibernate.Session;

import com.badlogic.gdx.graphics.Texture;

import io.github.roguelyte.core.Item;
import io.github.roguelyte.core.ItemType;
import io.github.roguelyte.core.Stats.StatsBuilder;
import io.github.roguelyte.db.tables.Items;
import io.github.roguelyte.db.tables.Stats;

public class ItemAdapter {
    private String name;
    private ItemType type;
    private Texture texture;
    private StatsBuilder statsBuilder;
    
    /** Requires a valid db session */
    public ItemAdapter(Session session, Items dbItem) {
        if (!session.isConnected()) {
            throw new RuntimeException("No valid session, stale object");
        }

        Map<String, Stats> statMap = new HashMap<>();
        for (Stats stat : dbItem.getStats()) {
            statMap.put(stat.getName(), stat);
        }

        name = dbItem.getName();
        type = dbItem.getType();
        texture = new Texture(dbItem.getAssetpath());
        statsBuilder = new StatsBuilder(
            statMap.get("health").toEntry(),
            statMap.get("armor").toEntry(),
            statMap.get("speed").toEntry(),
            statMap.get("dmg").toEntry());
    }

    public Item build(Random rand) {
        return new Item(String.format("%s-%d", name, rand.nextInt()), type, texture, statsBuilder.build(rand));
    }
}

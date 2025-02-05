package io.github.roguelyte.adapters;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import org.hibernate.Session;

import com.badlogic.gdx.graphics.Texture;

import io.github.roguelyte.core.Item;
import io.github.roguelyte.core.ItemType;
import io.github.roguelyte.core.Stats.StatBuilder;
import io.github.roguelyte.core.Stats.StatsBuilder;
import io.github.roguelyte.db.tables.Items;
import io.github.roguelyte.db.tables.Stats;

public class ItemAdapter extends Adapter {
    private ItemType type;
    private Texture texture;
    private StatsBuilder statsBuilder;
    
    /** Requires a valid db session */
    public ItemAdapter(Session session, Items dbItem) {
        super(session, dbItem.getName());
        Map<String, Stats> statMap = new HashMap<>();
        for (Stats stat : dbItem.getEntity().getStats()) {
            statMap.put(stat.getName(), stat);
        }

        type = dbItem.getType();
        texture = new Texture(dbItem.getAssetpath());

        statsBuilder = new StatsBuilder(
            new StatBuilder("health", statMap.get("health").toEntry()),
            new StatBuilder("armor", statMap.get("armor").toEntry()),
            new StatBuilder("speed", statMap.get("speed").toEntry()),
            new StatBuilder("dmg", statMap.get("dmg").toEntry()));
    }

    public Item build(Random rand) {
        return new Item(getName(rand), type, texture, statsBuilder.build(rand));
    }
}

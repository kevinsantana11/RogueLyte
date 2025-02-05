package io.github.roguelyte.core;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Map.Entry;

import lombok.AllArgsConstructor;
import lombok.Getter;


@AllArgsConstructor
@Getter
public class Stats {
    Map<String, Float> stats;

    public List<Entry<String, String>> asEntrySet() {
        return stats.keySet().stream().map(attr -> Map.entry(attr, stats.get(attr).toString())).toList();
    }

    public Float get(String key) {
        return stats.get(key);
    }

    @AllArgsConstructor
    public static class StatBuilder {
        @Getter String attrName;
        Entry<Float, Float> range;

        public Float genValue(Random rand) {
            if (range.getKey().equals(range.getValue())) {
                return range.getKey();
            }
            return rand.nextFloat(range.getKey(), range.getValue());
        }

    }

    public static class StatsBuilder {
        List<StatBuilder> statBuilders;

        public StatsBuilder(StatBuilder... stats) {
            this.statBuilders = Arrays.asList(stats);
        }

        public Stats build(Random rand) {
            Map<String, Float> statMap = new HashMap<>();

            for (StatBuilder statBuilder : statBuilders) {
                statMap.put(statBuilder.getAttrName(), statBuilder.genValue(rand));
            }
            return new Stats(statMap);
        }

    }
}

package io.github.roguelyte.core;

import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Map.Entry;

import lombok.AllArgsConstructor;
import lombok.Getter;


@AllArgsConstructor
@Getter
public class Stats {
    Float health;
    Float armor;
    Float speed;
    Float dmg;

    public List<Entry<String, String>> asEntrySet() {
        return List.of(
            Map.entry("health", health.toString()),
            Map.entry("armor", armor.toString()),
            Map.entry("speed", speed.toString()),
            Map.entry("dmg", dmg.toString()));

    }

    public static class StatsBuilder {
        Entry<Float, Float> health;
        Entry<Float, Float> armor;
        Entry<Float, Float> speed;
        Entry<Float, Float> dmg;

        public StatsBuilder(Entry<Float, Float> health, Entry<Float, Float> armor, Entry<Float, Float> speed, Entry<Float, Float> dmg) {
            this.health = health;
            this.armor = armor;
            this.speed = speed;
            this.dmg = dmg;
        }

        public Stats build(Random rand) {
            return new Stats(
                genValue(health, rand),
                genValue(armor, rand),
                genValue(speed, rand),
                genValue(dmg, rand)
            );
        }

        public Float genValue(Entry<Float, Float> entry, Random rand) {
            if (entry.getKey().equals(entry.getValue())) {
                return entry.getKey();
            }
            return rand.nextFloat(entry.getKey(), entry.getValue());
        }
    }
}

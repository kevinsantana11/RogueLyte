package io.github.roguelyte.core;

import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import lombok.AllArgsConstructor;


@AllArgsConstructor
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
}

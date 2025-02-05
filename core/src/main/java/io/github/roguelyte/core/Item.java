package io.github.roguelyte.core;

import com.badlogic.gdx.graphics.Texture;

import lombok.Getter;

public class Item {
    @Getter String name;
    @Getter ItemType type;
    @Getter Texture texture;
    @Getter Stats stats;

    public Item(String name, ItemType type, Texture texture, Stats stats) {
        this.name = name;
        this.type = type;
        this.texture = texture;
        this.stats = stats;
    }
}

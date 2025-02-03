package io.github.roguelyte.core;


import com.badlogic.gdx.graphics.Texture;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
public class Item {
    @Getter String name;
    @Getter Texture texture;
    @Getter Stats stats;
}

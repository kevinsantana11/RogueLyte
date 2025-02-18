package io.github.roguelyte.configs;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class GOConfig {
    private final float width;
    private final float height;
    private final float x;
    private final float y;
    private final float scalex;
    private final float scaley;

    public GOConfig(float width, float height) {
        this.width = width;
        this.height = height;

        this.x = 0f;
        this.y = 0f;
        this.scalex = 1;
        this.scaley = 1;
    }

    public GOConfig(float width, float height, float sx, float sy) {
        this.width = width;
        this.height = height;
        this.scalex = sx;
        this.scaley = sy;
        this.x = 0f;
        this.y = 0f;
    }
}

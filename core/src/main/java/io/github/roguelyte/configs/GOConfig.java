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

    public GOConfig(float width, float height) {
        this.width = width;
        this.height = height;

        this.x = 0f;
        this.y = 0f;
    }
}

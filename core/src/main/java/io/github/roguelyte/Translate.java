package io.github.roguelyte;

import com.badlogic.gdx.graphics.g2d.Sprite;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class Translate implements Action {
    Sprite sprite;
    float x;
    float y;

    @Override
    public void apply(Game game) {
        sprite.translate(x, y);
    }
}

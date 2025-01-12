package io.github.roguelyte;

import com.badlogic.gdx.graphics.g2d.Sprite;
import java.lang.Math;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class Translate implements Action {
    Sprite sprite;
    float x;
    float y;

    @Override
    public void apply(Game game) {
        int newX = (int) Math.ceil(sprite.getX() + x);
        int newY = (int) Math.ceil(sprite.getY() + y);

        System.out.println(String.format("Moving to: (x, y): (%s, %s)", newX, newY));

        if (!game.getLevel().collides(newX, newY)) {
            sprite.translate(x, y);
        }
    }
}

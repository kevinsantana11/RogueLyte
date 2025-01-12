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
        int newX = 0;
        int newY = 0;

        if (x > 0) {
            newX = (int) Math.ceil(sprite.getX() + x + sprite.getWidth() / 2);
        } else {
            newX = (int) Math.ceil(sprite.getX() + x);
        }

        if (y > 0) {
            newY = (int) Math.ceil(sprite.getY() + y + sprite.getHeight() / 2);

        } else {
            newY = (int) Math.ceil(sprite.getY() + y);
        }

        System.out.println(String.format("Moving to: (x, y): (%s, %s)", newX, newY));

        if (!game.getLevel().collides(newX, newY)) {
            sprite.translate(x, y);
        }
    }
}

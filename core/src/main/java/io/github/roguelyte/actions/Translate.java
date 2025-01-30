package io.github.roguelyte.actions;

import com.badlogic.gdx.graphics.g2d.Sprite;
import io.github.roguelyte.Game;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class Translate implements Action {
    Sprite sprite;
    float x;
    float y;

    @Override
    public void apply(Game game) {

        if (!collides(game, x, y)) {
            sprite.translate(x, y);
        }
    }

    boolean collides(Game game, float x, float y) {
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
        return game.getLevel().collides(newX, newY);

    }
}

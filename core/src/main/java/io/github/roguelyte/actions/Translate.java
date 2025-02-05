package io.github.roguelyte.actions;

import com.badlogic.gdx.graphics.g2d.Sprite;
import io.github.roguelyte.Game;
import io.github.roguelyte.core.GO;
import io.github.roguelyte.actors.Character;
import lombok.AllArgsConstructor;

import static io.github.roguelyte.utils.Utility.spritesOverlaping;

@AllArgsConstructor
public class Translate<E extends GO> implements Action {
    E e;
    float x;
    float y;
    private boolean collisionCheck;

    public Translate(E e, float x, float y) {
        this.e = e;
        this.x = x;
        this.y = y;
        this.collisionCheck = true;
    }

    @Override
    public void apply(Game game) {

        if (!collisionCheck || !collides(game, x, y)) {
            e.getSprite().translate(x, y);
        }

        for (Character character : game.getCharacters()) {
            if (spritesOverlaping(character, e) && character != game.getPlayer()) {
                character.hit(e.getStats(), e.getId());
            }
        }
    }

    boolean collides(Game game, float x, float y) {
        int newX = 0;
        int newY = 0;
        Sprite sprite = e.getSprite();

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

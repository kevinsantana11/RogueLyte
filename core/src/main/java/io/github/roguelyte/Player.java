package io.github.roguelyte;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import java.util.ArrayList;
import java.util.List;

public class Player extends Character {
    public Player(
            Texture texture,
            float width,
            float height,
            float startingHealth,
            float speed) {
        super("player", texture, width, height, startingHealth, speed);
    }

    public List<Action> processInputs(float deltaTime) {
        List<Action> actions = new ArrayList<>();
        float xtransform = 0;
        float ytransform = 0;

        if (Gdx.input.isKeyPressed(Input.Keys.D)) {
            xtransform = speed * deltaTime;
        } else if (Gdx.input.isKeyPressed(Input.Keys.A)) {
            xtransform = -1 * speed * deltaTime;
        }

        if (Gdx.input.isKeyPressed(Input.Keys.W)) {
            ytransform = speed * deltaTime;
        } else if (Gdx.input.isKeyPressed(Input.Keys.S)) {
            ytransform = -1 * speed * deltaTime;
        }

        if (xtransform != 0 || ytransform != 0) {
            sprite.translate(xtransform, ytransform);
            actions.add(new Move(sprite.getX(), sprite.getY()));
        }

        if (Gdx.input.isButtonJustPressed(Input.Buttons.RIGHT)) {
            Action action =
                    new InvokeSpell(
                            "fireball",
                            new Vector2(
                                    sprite.getX() + (sprite.getWidth() / 2),
                                    sprite.getY() + (sprite.getHeight() / 2)),
                            new Vector2(Gdx.input.getX(), Gdx.input.getY()));
            actions.add(action);
        }

        return actions;
    }
}

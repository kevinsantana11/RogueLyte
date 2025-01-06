package io.github.roguelyte;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import java.util.ArrayList;
import java.util.List;

public class Player extends Character {
    private final float speed;

    public Player(
            Texture texture,
            ShapeRenderer shapeRenderer,
            float width,
            float height,
            float startingHealth,
            float speed) {
        super("player", texture, shapeRenderer, width, height, startingHealth);
        this.speed = speed;
    }

    @Override
    public boolean drawSprites(float deltaTime, SpriteBatch batch) {
        batch.draw(sprite.getTexture(), sprite.getX(), sprite.getY(), width, height);
        return false;
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

    public float getX() {
        return sprite.getX();
    }

    public float getY() {
        return sprite.getY();
    }
}

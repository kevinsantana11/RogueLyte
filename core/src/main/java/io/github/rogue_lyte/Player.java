package io.github.rogue_lyte;

import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;

public class Player implements GameObject {
    private Sprite sprite;
    private float speed;
    private float width;
    private float height;

    public Player(Texture texture, float width, float height, float speed) {
        this.speed = speed;
        this.width = width;
        this.height = height;
        sprite = new Sprite(texture);
        sprite.setSize(width, height);
    }

    @Override
    public void draw(float deltaTime, SpriteBatch batch) {
        batch.draw(sprite.getTexture(), sprite.getX(), sprite.getY(), width, height);
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

        if (Gdx.input.isButtonPressed(Input.Buttons.RIGHT)) {
            Action action = new InvokeSpell("fireball",
                new Vector2(sprite.getX(), sprite.getY()),
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

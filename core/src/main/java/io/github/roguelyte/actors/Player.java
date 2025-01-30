package io.github.roguelyte.actors;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;

import io.github.roguelyte.actions.Action;
import io.github.roguelyte.actions.InvokeSkill;
import io.github.roguelyte.actions.Translate;
import io.github.roguelyte.configs.GOConfig;
import io.github.roguelyte.configs.PhysicsConfig;
import io.github.roguelyte.core.ProjectileFactory;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

public class Player extends Character {
    Map<Integer, ProjectileFactory> skillMap;

    public Player(
            Texture texture,
            GOConfig config,
            PhysicsConfig physics,
            float startingHealth,
            Map<Integer, ProjectileFactory> skillMap) {
        super("player", texture, config, physics, startingHealth);
        this.skillMap = skillMap;
    }

    @Override
    public List<Action> act(float deltaTime) {
        List<Action> actions = new ArrayList<>();
        float xtransform = 0;
        float ytransform = 0;

        if (Gdx.input.isKeyPressed(Input.Keys.D)) {
            xtransform = 1;
        } else if (Gdx.input.isKeyPressed(Input.Keys.A)) {
            xtransform = -1;
        }

        if (Gdx.input.isKeyPressed(Input.Keys.W)) {
            ytransform = 1;
        } else if (Gdx.input.isKeyPressed(Input.Keys.S)) {
            ytransform = -1;
        }

        Vector2 transform = new Vector2(xtransform, ytransform);
        double ang = transform.angleRad();
        float dx = (float) Math.cos(ang) * this.physics.getSpeed();
        float dy = (float) Math.sin(ang) * this.physics.getSpeed();


        if (xtransform != 0 || ytransform != 0) {
            actions.add(new Translate(this.getSprite(), dx, dy));
        }

        for (Entry<Integer, ProjectileFactory> kv : skillMap.entrySet()) {
            if (this.isInputJustPressed(kv.getKey())) {
                actions.add(
                        new InvokeSkill(this, kv.getValue(), Gdx.input.getX(), Gdx.input.getY()));
            }
        }

        return actions;
    }

    private boolean isInputJustPressed(int input) {
        return Gdx.input.isButtonJustPressed(input) || Gdx.input.isKeyJustPressed(input);
    }
}

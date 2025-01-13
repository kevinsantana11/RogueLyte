package io.github.roguelyte.actors;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;

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
        Map<Integer, ProjectileFactory> skillMap
    ) {
        super("player", texture, config, physics, startingHealth);
        this.skillMap = skillMap;
    }

    @Override
    public List<Action> act(float deltaTime) {
        List<Action> actions = new ArrayList<>();
        float xtransform = 0;
        float ytransform = 0;

        if (Gdx.input.isKeyPressed(Input.Keys.D)) {
            xtransform = physics.getSpeed() * deltaTime;
        } else if (Gdx.input.isKeyPressed(Input.Keys.A)) {
            xtransform = -1 * physics.getSpeed() * deltaTime;
        }

        if (Gdx.input.isKeyPressed(Input.Keys.W)) {
            ytransform = physics.getSpeed() * deltaTime;
        } else if (Gdx.input.isKeyPressed(Input.Keys.S)) {
            ytransform = -1 * physics.getSpeed() * deltaTime;
        }

        if (xtransform != 0 && ytransform != 0) {
            float xbound = Math.signum(xtransform) * (float) Math.sin(45) * physics.getSpeed() * deltaTime;
            float ybound = Math.signum(ytransform) * (float) Math.cos(45) * physics.getSpeed() * deltaTime;

            xtransform =  Math.abs(xbound) > Math.abs(xtransform) ? xtransform : xbound;
            ytransform =  Math.abs(ybound) > Math.abs(ytransform) ? ytransform : ybound;
        }

        actions.add(new Translate(
            this.getSprite(),
            xtransform,
            ytransform
        ));

        for (Entry<Integer, ProjectileFactory> kv : skillMap.entrySet()) {
            if (this.isInputJustPressed(kv.getKey())) {
                actions.add(new InvokeSkill(
                    this,
                    kv.getValue(),
                    Gdx.input.getX(),
                    Gdx.input.getY()
                ));
            }
        }

        return actions;
    }

    private boolean isInputJustPressed(int input) {
        return Gdx.input.isButtonJustPressed(input) || Gdx.input.isKeyJustPressed(input);
    }
}

package io.github.roguelyte.actors;

import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.graphics.Texture;

import io.github.roguelyte.actions.Action;
import io.github.roguelyte.actions.GotoPlayer;
import io.github.roguelyte.configs.GOConfig;
import io.github.roguelyte.configs.PhysicsConfig;

public class AI extends Character {
    public AI(
            String name,
            Texture texture,
            GOConfig config,
            PhysicsConfig physics,
            float startingHealth) {
        super(name, texture, config, physics, startingHealth);
    }

    @Override
    public List<Action> act(float deltaTime) {
        ArrayList<Action> accs = new ArrayList<>();
        accs.add(new GotoPlayer(this));
        return accs;
    }
    
}

package io.github.roguelyte.actors;

import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.graphics.Texture;

import io.github.roguelyte.actions.Action;
import io.github.roguelyte.actions.GotoPlayer;
import io.github.roguelyte.configs.GOConfig;
import io.github.roguelyte.configs.PhysicsConfig;
import io.github.roguelyte.core.Spawner;

public class Enemy extends Character {
    private Spawner<AcquirableItem> itemSpawner;
    private boolean itemDropped;

    public Enemy(
            String name,
            Texture texture,
            GOConfig config,
            PhysicsConfig physics,
            float startingHealth,
            Spawner<AcquirableItem> itemSpawner) {
        super(name, texture, config, physics, startingHealth);
        this.itemSpawner = itemSpawner;
        this.itemDropped = false;
    }

    @Override
    public boolean canCleanup() {
        return this.healthbar.getHealth() == 0 && this.itemDropped;
    }

    @Override
    public List<Action> act(float deltaTime) {
        ArrayList<Action> accs = new ArrayList<>();
        accs.add(new GotoPlayer(this));

        if (this.healthbar.getHealth() == 0) {
            Action spawnActions = this.itemSpawner.spawn(this.sprite.getX(), this.getSprite().getY());
            accs.add(spawnActions);
            itemDropped = true;
        }
        return accs;
    }
    
}

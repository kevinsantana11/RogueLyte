package io.github.roguelyte.core;

import com.badlogic.gdx.graphics.Texture;

import io.github.roguelyte.actors.AI;
import io.github.roguelyte.actors.Character;
import io.github.roguelyte.configs.GOConfig;
import io.github.roguelyte.configs.PhysicsConfig;
import java.util.Random;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class Spawner {
    private String enemyName;
    private Texture texture;
    private GOConfig config;
    private PhysicsConfig physics;
    private float startingHealth;
    private Random rand;
    private float stateTime;
    private float spawnInterval;

    public Spawner(
        String enemyName,
        Texture texture,
        GOConfig config,
        PhysicsConfig physics,
        float startingHealth,
        Random rand,
        float spawnInterval) {
            this.enemyName = enemyName;
            this.texture = texture;
            this.config = config;
            this.physics = physics;
            this.startingHealth = startingHealth;
            this.rand = rand;
            this.spawnInterval = spawnInterval;
            this.stateTime = 0;

    }

    public void tick(float deltaTime) {
        this.stateTime += deltaTime;
    }

    public Character trySpawn(float x, float y) {
        int pos = rand.nextInt(0, 99);
        Character character = null;
        if (pos > 89 && stateTime % spawnInterval < 0.01f) {
            character =
                    new AI(
                            this.enemyName,
                            this.texture,
                            new GOConfig(config.getWidth(), config.getHeight(), x, y),
                            physics,
                            startingHealth);
        }
        return character;
    }
}

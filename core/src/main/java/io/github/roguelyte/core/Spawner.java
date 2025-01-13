package io.github.roguelyte.core;

import java.util.Random;

import com.badlogic.gdx.graphics.Texture;

import io.github.roguelyte.configs.GOConfig;
import io.github.roguelyte.configs.PhysicsConfig;
import io.github.roguelyte.actors.Character;
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

    public void tick(float deltaTime) {
        this.stateTime += deltaTime;
    }

    public Character trySpawn(float x, float y) {
        int pos = rand.nextInt(0, 999);
        Character character = null;
        if (pos >= 899 && stateTime % spawnInterval < 0.01f) {
            character = new Character(
                this.enemyName,
                this.texture,
                new GOConfig(config.getWidth(), config.getHeight(), x, y),
                physics,
                startingHealth);
        } 
        return character;
    }
    
}

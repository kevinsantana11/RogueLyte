package io.github.roguelyte.core;

import io.github.roguelyte.actions.Spawn;

import java.util.Random;
import java.util.function.Supplier;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class Spawner<Entity extends HasSprite> {
    Entity entity;
    private Random rand;
    private float stateTime;
    private float spawnInterval;
    private Supplier<Entity> supplier;

    public Spawner(
        Random rand,
        float spawnInterval,
        Supplier<Entity> supplier) {
            this.rand = rand;
            this.spawnInterval = spawnInterval;
            this.supplier = supplier;
            this.stateTime = 0;

    }
    public Spawner(Supplier<Entity> supplier) {
            this.rand = null;
            this.spawnInterval = 0;
            this.supplier = supplier;
            this.stateTime = 0;

    }

    public void tick(float deltaTime) {
        this.stateTime += deltaTime;
    }

    public Spawn<Entity> trySpawn(float x, float y) {
        int pos = rand.nextInt(0, 99);
        if (pos > 89 && stateTime % spawnInterval < 0.01f) {
            return new Spawn<Entity>(supplier.get(), x, y);
        }
        return null;
    }

    public Spawn<Entity> spawn(float x, float y) {
        return new Spawn<Entity>(supplier.get(), x, y);
    }
}

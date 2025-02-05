package io.github.roguelyte.core;

import io.github.roguelyte.actions.Spawn;

import java.util.List;
import java.util.Random;
import java.util.function.Supplier;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class Spawner<Entity extends GO> {
    Entity entity;
    private Random rand;
    private float stateTime;
    private float spawnInterval;
    private List<Supplier<Entity>> suppliers;

    public Spawner(
        Random rand,
        float spawnInterval,
        List<Supplier<Entity>> suppliers) {
            this.rand = rand;
            this.spawnInterval = spawnInterval;
            this.suppliers = suppliers;
            this.stateTime = 0;

    }

    public Spawner(
        Random rand,
        float spawnInterval,
        Supplier<Entity> supplier) {
            this.rand = rand;
            this.spawnInterval = spawnInterval;
            this.suppliers = List.of(supplier);
            this.stateTime = 0;

    }

    public Spawner(
        Random rand,
        Supplier<Entity> supplier) {
            this.rand = rand;
            this.spawnInterval = 0;
            this.suppliers = List.of(supplier);
            this.stateTime = 0;

    }

    public Spawner(
        Random rand,
        List<Supplier<Entity>> suppliers) {
            this.rand = rand;
            this.spawnInterval = 0;
            this.suppliers = suppliers;
            this.stateTime = 0;

    }


    public Spawner(Supplier<Entity> supplier) {
            this.rand = null;
            this.spawnInterval = 0;
            this.suppliers = List.of(supplier);
            this.stateTime = 0;

    }

    public void tick(float deltaTime) {
        this.stateTime += deltaTime;
    }

    public Spawn<Entity> trySpawn(float x, float y) {
        int supplierIdx = rand.nextInt(0, suppliers.size());
        Supplier<Entity> supplier = suppliers.get(supplierIdx);
        if (rand.nextInt(0, 99) > 89 && stateTime % spawnInterval < 0.01f) {
            return new Spawn<Entity>(supplier.get(), x, y);
        }
        return null;
    }

    public Spawn<Entity> spawn(float x, float y) {
        int supplierIdx = rand.nextInt(0, suppliers.size());
        Supplier<Entity> supplier = suppliers.get(supplierIdx);
        return new Spawn<Entity>(supplier.get(), x, y);
    }
}

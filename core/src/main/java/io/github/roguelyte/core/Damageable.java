package io.github.roguelyte.core;

import java.util.HashSet;
import java.util.Set;

import lombok.Getter;

public abstract class Damageable {
    @Getter private float health;
    @Getter private float maxHealth;
    Set<String> hitby;

    public Damageable(float startingHealth) {
        this.health = startingHealth;
        this.maxHealth = startingHealth;
        this.hitby = new HashSet<>();
    }

    public void hit(float amt, String id) {
        if (!hitby.contains(id)) {
            if (amt < health) {
                health = health - amt;
            } else {
                health = 0;
            }
            hitby.add(id);
        }
    }

    public void heal(float amt) {
        if (amt + health < maxHealth) {
            health = health + amt;
        } else {
            health = maxHealth;
        }
    }
}

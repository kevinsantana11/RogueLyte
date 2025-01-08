package io.github.roguelyte;

import java.util.Set;

public abstract class Damageable {
    float health;
    float maxHealth;
    Set<String> hitby;

    Damageable(float startingHealth) {
        this.health = startingHealth;
        this.maxHealth = startingHealth;
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

package io.github.roguelyte;

public abstract class Damageable {
    float health;
    float maxHealth;

    Damageable(float startingHealth) {
        this.health = startingHealth;
        this.maxHealth = startingHealth;
    }

    public void hit(float amt) {
        if (amt < health) {
            health = health - amt;
        } else {
            health = 0;
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

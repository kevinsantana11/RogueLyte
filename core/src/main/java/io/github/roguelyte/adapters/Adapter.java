package io.github.roguelyte.adapters;

import java.util.Random;

import org.hibernate.Session;

public abstract class Adapter {
    private String name;

    public Adapter(Session session, String name) {
        if (!session.isConnected()) {
            throw new RuntimeException("No valid session, stale object");
        }
        this.name = name;
    }
    public String getName(Random rand) {
        return String.format("%s-%d", name, rand.nextInt());
    }
}

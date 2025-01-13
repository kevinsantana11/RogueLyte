package io.github.roguelyte.actors;

import java.util.List;

import io.github.roguelyte.actions.Action;

public interface Actor {
    List<Action> act(float deltaTime);
}

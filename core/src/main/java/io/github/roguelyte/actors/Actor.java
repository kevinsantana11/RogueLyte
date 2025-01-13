package io.github.roguelyte.actors;

import io.github.roguelyte.actions.Action;
import java.util.List;

public interface Actor {
    List<Action> act(float deltaTime);
}

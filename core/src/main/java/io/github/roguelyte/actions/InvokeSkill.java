package io.github.roguelyte.actions;

import io.github.roguelyte.Game;
import io.github.roguelyte.core.ProjectileFactory;
import io.github.roguelyte.actors.Character;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class InvokeSkill implements Action {
    Character originator;
    ProjectileFactory factory;
    int x;
    int y;

    @Override
    public void apply(Game game) {
        game.addProjectile(factory.createFromScreenCoords(originator, x, y));
    }

}

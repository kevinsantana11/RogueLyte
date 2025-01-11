package io.github.roguelyte;

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

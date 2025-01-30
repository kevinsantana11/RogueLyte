package io.github.roguelyte.actions;

import io.github.roguelyte.Game;
import io.github.roguelyte.actors.Character;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class Spawn implements Action {
    Character character;

    @Override
    public void apply(Game game) {
        for (Character inGameCharacter : game.getCharacters()) {
            if (character.getSprite().getX() == inGameCharacter.getSprite().getX()
                    && character.getSprite().getY() == inGameCharacter.getSprite().getY()) {
                System.out.println("Cant spawn, enemy already present here");
                return;
            }
        }

        System.out.println("Spawning an character");
        game.addCharacater(character);
    }
}

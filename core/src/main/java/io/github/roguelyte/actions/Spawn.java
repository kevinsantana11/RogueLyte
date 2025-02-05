package io.github.roguelyte.actions;

import io.github.roguelyte.Game;
import io.github.roguelyte.actors.Character;
import io.github.roguelyte.actors.AcquirableItem;
import io.github.roguelyte.core.GO;

public class Spawn<E extends GO> implements Action {
    E entity;
    float x;
    float y;

    public Spawn(E entity, float x, float y) {
        this.entity = entity;
        this.x = x;
        this.y =y;
    }

    @Override
    public void apply(Game game) {
        if (entity instanceof Character && !characterExistsOn(game, x, y)) {
            System.out.println("Spawning an character");
            entity.getSprite().setX(x);
            entity.getSprite().setY(y);
            game.addCharacater((Character) entity);
        } 
        
        if (entity instanceof AcquirableItem && !itemExistsOn(game, x, y)) {
            System.out.println("Spawning an item!");
            entity.getSprite().setX(x);
            entity.getSprite().setY(y);
            game.addItem((AcquirableItem) entity);
        }
    }

    public boolean characterExistsOn(Game game, float x, float y) {
        for (Character inGameCharacter : game.getCharacters()) {
            if (x == inGameCharacter.getSprite().getX() && y == inGameCharacter.getSprite().getY()) {
                return true;
            }
        }
        return false;

    }

    public boolean itemExistsOn(Game game, float x, float y) {
        for (AcquirableItem imGameItem : game.getItems()) {
            if (x == imGameItem.getSprite().getX() && y == imGameItem.getSprite().getY()) {
                return true;
            }
        }
        return false;

    }
}

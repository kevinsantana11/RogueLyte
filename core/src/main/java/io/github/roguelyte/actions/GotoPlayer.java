package io.github.roguelyte.actions;

import com.badlogic.gdx.math.Vector2;

import io.github.roguelyte.Game;
import io.github.roguelyte.actors.Character;

public class GotoPlayer extends Translate {
    Character character;

    public GotoPlayer(Character character) {
        super(character.getSprite(), 
            character.getSprite().getX(),
            character.getSprite().getY());
        this.character = character;
    }

    @Override
    public void apply(Game game) {
        Vector2 playerTileCoords =  new Vector2(
                    game.getPlayer().getSprite().getX(),
                    game.getPlayer().getSprite().getY());
        Vector2 characterTileCoords = new Vector2(
            character.getSprite().getX(),
            character.getSprite().getY());

        Vector2 hyp = playerTileCoords.sub(characterTileCoords);
        double ang = (double) hyp.angleRad();
        float xmag = (float) Math.cos(ang) * character.getPhysicsConfig().getSpeed();
        float ymag = (float) Math.sin(ang) * character.getPhysicsConfig().getSpeed();

        super.x = xmag;
        super.y = ymag;
        super.apply(game);
    }
    
}

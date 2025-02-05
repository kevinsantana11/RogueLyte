package io.github.roguelyte.actions;

import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.math.Vector2;

import io.github.roguelyte.Game;
import io.github.roguelyte.actors.Character;

public class GotoPlayer extends Translate<Character> {
    Character character;

    public GotoPlayer(Character character) {
        super(character, 
            character.getSprite().getX(),
            character.getSprite().getY());
        this.character = character;
    }

    @Override
    public void apply(Game game) {
        List<Float> possibleMoves = List.of(0f, 45f, 90f, 135f, 180f, 225f, 270f, 315f);

        List<Float> distArray = new ArrayList<>(possibleMoves.size());
        Float lowestDistance = Float.MAX_VALUE;
        Vector2 bestMove = null;

        for (Float move : possibleMoves) {
            float translateX = (float) Math.cos(Math.toRadians(move)) * character.getPhysicsConfig().getSpeed();
            float translateY = (float) Math.sin(Math.toRadians(move)) * character.getPhysicsConfig().getSpeed();
            float newX = character.getSprite().getX() + translateX;
            float newY = character.getSprite().getY() + translateY;
            float distance;

            if (collides(game, newX, newY)) {
                distance = Float.MAX_VALUE;
                distArray.add(Float.MAX_VALUE);
            } else {
                distance = Vector2.dst(newX,
                    newY,
                    game.getPlayer().getSprite().getX(),
                    game.getPlayer().getSprite().getY());
            }

            if (distance < lowestDistance) {
                bestMove = new Vector2(translateX, translateY);
                lowestDistance = distance;
            }
            distArray.add(distance);
        }

        if (bestMove != null) {
            super.x = bestMove.x;
            super.y = bestMove.y;
            super.apply(game);
        }
    }
    
}

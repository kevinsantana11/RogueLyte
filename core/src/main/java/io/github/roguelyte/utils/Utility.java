package io.github.roguelyte.utils;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Circle;

import io.github.roguelyte.core.GO;

public class Utility {
    
    public static boolean spritesOverlaping(GO a, GO b) {
        Sprite aSprite = a.getSprite();
        Sprite bSprite = b.getSprite();
        Circle cir = new Circle(aSprite.getX(), aSprite.getY(), aSprite.getWidth());
        return cir.contains(bSprite.getX(), bSprite.getY());
    }
}

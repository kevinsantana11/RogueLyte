package io.github.roguelyte.utils;

import com.badlogic.gdx.graphics.g2d.Sprite;

import io.github.roguelyte.core.HasSprite;

public class Utility {
    
    public static boolean spritesOverlaping(HasSprite a, HasSprite b) {
        Sprite aSprite = a.getSprite();
        Sprite bSprite = b.getSprite();
        return aSprite.getBoundingRectangle().contains(bSprite.getX(), bSprite.getY());
    }
}

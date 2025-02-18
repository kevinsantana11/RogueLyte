package io.github.roguelyte.core;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

import io.github.roguelyte.actors.Character;
import io.github.roguelyte.configs.GOConfig;
import io.github.roguelyte.movement.Movement;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class AnimatedProjectile extends Projectile {
    private final Animation<TextureRegion> animation;

    public AnimatedProjectile(
            Character originator,
            String name,
            Texture texture,
            GOConfig config,
            Stats stats,
            Movement movement,
            Vector2 end) {
        super(originator, name, texture, config, stats, movement, end);

        TextureRegion[][] tmpFrames = TextureRegion.split(texture, 16, 16);
        TextureRegion[] animationFrames = new TextureRegion[tmpFrames.length * tmpFrames[0].length];
        int index = 0;
        for (int i = 0; i < tmpFrames.length; i++) {
            for (int j = 0; j < tmpFrames[i].length; j++) {
                animationFrames[index++] = tmpFrames[i][j];
            }
        }
        this.animation = new Animation<>(0.1f, animationFrames);
    }

    @Override
    public void drawSprites(float deltaTime, SpriteBatch batch) {
        TextureRegion currFrame = animation.getKeyFrame(stateTime, true);
        Vector2 origin = movement.origin();
        sprite.setOrigin(origin.x, origin.y);
        sprite.rotate(movement.getRot());
        batch.draw(
                currFrame,
                sprite.getX(),
                sprite.getY(),
                origin.x,
                origin.y,
                sprite.getWidth(),
                sprite.getHeight(),
                sprite.getScaleX(),
                sprite.getScaleY(),
                movement.getRot());
    }
}

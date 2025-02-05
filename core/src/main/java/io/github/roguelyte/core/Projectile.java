package io.github.roguelyte.core;

import java.util.List;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;

import io.github.roguelyte.actions.Action;
import io.github.roguelyte.actions.Translate;
import io.github.roguelyte.actors.Actor;
import io.github.roguelyte.actors.Character;
import io.github.roguelyte.configs.GOConfig;
import io.github.roguelyte.configs.PhysicsConfig;
import io.github.roguelyte.configs.ProjectileConfig;
import lombok.Getter;

public class Projectile implements GO, Actor, Drawable {
    @Getter private Character originator;
    private float stateTime;
    private final Sprite sprite;
    private final Animation<TextureRegion> animation;
    private GOConfig config;
    private PhysicsConfig physics;
    @Getter private ProjectileConfig projectileConfig;
    @Getter private String id;
    private final float xmag;
    private final float ymag;
    private float rot;
    private float distanceTraveled;
    @Getter private Stats stats;

    public Projectile(
            Character originator,
            String name,
            Texture texture,
            GOConfig config,
            PhysicsConfig physics,
            ProjectileConfig projectileConfig,
            Stats stats,
            Vector2 end) {
        this.id = name;
        this.originator = originator;
        this.stateTime = 0;
        this.distanceTraveled = 0;
        this.config = config;
        this.physics = physics;
        this.projectileConfig = projectileConfig;
        this.stats = stats;

        this.sprite = new Sprite(texture);
        sprite.setSize(this.config.getWidth(), this.config.getHeight());

        float xdiff = end.x - this.config.getX();
        float ydiff = end.y - this.config.getY();
        double mag = Math.sqrt(Math.pow(xdiff, 2) + Math.pow(ydiff, 2));
        xmag = mag > 0 ? (float) (xdiff / mag) : 1;
        ymag = mag > 0 ? (float) (ydiff / mag) : 1;

        sprite.setX(this.config.getX());
        sprite.setY(this.config.getY());

        double ratio = ydiff / xdiff;
        double rads = Math.atan(ratio);
        double deg = Math.abs(Math.toDegrees(rads));

        if (xdiff > 0 && ydiff > 0) {
            rot = (float) deg;
        } else if (xdiff < 0 && ydiff > 0) {
            rot = 180 - (float) deg;
        } else if (xdiff < 0 && ydiff < 0) {
            rot = 180 + (float) deg;
        } else {
            rot = 360 - (float) deg;
        }

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

    public Sprite getSprite() {
        return sprite;
    }

    @Override
    public void drawSprites(float deltaTime, SpriteBatch batch) {
        TextureRegion currFrame = animation.getKeyFrame(stateTime, true);
        // System.out.println(String.format("offsets: (%f, %f), rot: {%f}", xOffset, yOffset, rot));
        batch.draw(
                currFrame,
                sprite.getX(),
                sprite.getY(),
                sprite.getWidth() / 2,
                sprite.getHeight() / 2,
                sprite.getWidth(),
                sprite.getHeight(),
                1,
                1,
                rot);
    }

    @Override
    public void drawShapes(float deltaTime, ShapeRenderer shapeRenderer) {}

    @Override
    public boolean canCleanup() {
        return this.distanceTraveled >= this.projectileConfig.getMaxDistance();
    }

	@Override
	public List<Action> act(float deltaTime) {
        stateTime += deltaTime;
        float xdist = deltaTime * this.physics.getSpeed() * xmag;
        float ydist = deltaTime * this.physics.getSpeed() * ymag;
        this.distanceTraveled =
                this.distanceTraveled
                        + (float) Math.sqrt((double) (xdist * xdist) + (ydist * ydist));
        return List.of(
            new Translate<Projectile>(this, xdist, ydist, false)
        );
	}
}

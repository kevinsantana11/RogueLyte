package io.github.roguelyte;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;

public class Projectile extends Damaging implements GameObject {
    public Character originator;
    private float stateTime;
    private final Sprite sprite;
    private final Animation<TextureRegion> animation;
    private final float width;
    private final float height;
    private final float speed;
    private final float xmag;
    private final float ymag;
    private float rot;
    private float distanceTraveled;
    private final float maxDistance;

    public Projectile(
            Character originator,
            String name,
            Texture texture,
            float width,
            float height,
            Vector2 start,
            Vector2 end,
            float speed,
            float dmg,
            float maxDistance) {
        super(name, dmg);
        this.originator = originator;
        this.stateTime = 0;
        this.width = width;
        this.height = height;
        this.speed = speed;
        this.maxDistance = maxDistance;
        this.distanceTraveled = 0;
        this.sprite = new Sprite(texture);
        sprite.setSize(width, height);

        double xdiff = end.x - start.x;
        double ydiff = end.y - start.y;
        double mag = Math.sqrt(Math.pow(xdiff, 2) + Math.pow(ydiff, 2));
        xmag = mag > 0 ? (float) (xdiff / mag) : 1;
        ymag = mag > 0 ? (float) (ydiff / mag) : 1;

        // sprite.setOrigin(start.x, start.y);
        sprite.setX(start.x);
        sprite.setY(start.y);
        sprite.setOrigin(start.x + width / 2, start.y + height / 2);

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
            rot = -(float) deg;
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
    public boolean drawSprites(float deltaTime, SpriteBatch batch) {
        stateTime += deltaTime;

        float xdist = deltaTime * speed * xmag;
        float ydist = deltaTime * speed * ymag;
        this.distanceTraveled =
                this.distanceTraveled
                        + (float) Math.sqrt((double) (xdist * xdist) + (ydist * ydist));

        if (this.distanceTraveled < this.maxDistance) {
            sprite.setX(sprite.getX() + xdist);
            sprite.setY(sprite.getY() + ydist);

            TextureRegion currFrame = animation.getKeyFrame(stateTime, true);
            batch.draw(currFrame, sprite.getX(), sprite.getY(), 0, 0, width, height, 1, 1, rot);
            return false;
        }

        return true;
    }

    @Override
    public void drawShapes(float deltaTime, ShapeRenderer shapeRenderer) {}
}

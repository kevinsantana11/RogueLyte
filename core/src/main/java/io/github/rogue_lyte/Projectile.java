package io.github.rogue_lyte;

import java.lang.Math;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

public class Projectile implements GameObject {
    private float stateTime;
    private Sprite sprite;
    private Animation<TextureRegion> animation;
    private float width;
    private float height;
    private float speed;
    private float xmag;
    private float ymag;
    private float rot;

    public Projectile(Texture texture,
            float width,
            float height,
            Vector2 start,
            Vector2 end,
            float speed
    ) {
        this.stateTime = 0;
        this.width = width;
        this.height = height;
        this.speed = speed;
        this.sprite = new Sprite(texture);
        sprite.setSize(width, height);

        double xdiff = end.x - start.x;
        double ydiff = end.y - start.y;
        double mag = Math.sqrt(Math.pow(xdiff, 2) + Math.pow(ydiff, 2));
        xmag = mag > 0 ? (float)(xdiff/mag) : 1;
        ymag = mag > 0 ? (float)(ydiff/mag) : 1;
        
        // sprite.setOrigin(start.x, start.y);
        sprite.setX(start.x);
        sprite.setY(start.y);
        sprite.setOrigin(start.x + width / 2, start.y + height / 2);

        double ratio = ydiff / xdiff;
        double rads = Math.atan(ratio);
        double deg = Math.abs(Math.toDegrees(rads));

        if (xdiff > 0 && ydiff > 0) {
            rot = (float)deg;
        } else if (xdiff < 0 && ydiff > 0) {
            rot = 90 + (float) deg;
        } else if (xdiff < 0 && ydiff < 0) {
            rot = 90 - (float) deg;
        } else {
            rot = -(float)deg;
        }
        System.out.println(String.format("rot: (deg, rot) - (%f, %f)", deg, rot));

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
    public void draw(float deltaTime, SpriteBatch batch) {
        stateTime += deltaTime;

        sprite.setX(sprite.getX() + deltaTime * speed * xmag);
        sprite.setY(sprite.getY() + deltaTime * speed * ymag);

        TextureRegion currFrame = animation.getKeyFrame(stateTime, true);
        batch.draw(currFrame,
            sprite.getX(),
            sprite.getY(),
            0,
            0,
            width,
            height,
            1,
            1,
            rot);
    }
}

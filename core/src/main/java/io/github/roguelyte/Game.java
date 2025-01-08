package io.github.roguelyte;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.viewport.Viewport;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
public class Game implements Drawable {
    private Level level;
    @Getter private Player player;
    private List<Character> characters;
    private List<Projectile> projectiles;
    private Random rand;

    public void step(float deltaTime, Viewport viewport, Map<String, Texture> txMap) {
        processInput(deltaTime, viewport, txMap);
        sim(deltaTime);
    }

    private void sim(float deltaTime) {
        List<Ephemeral> cleanupList = new ArrayList<>();
        for (Projectile proj : projectiles) {
            proj.step(deltaTime);
            for (Character character : characters) {
                float x = proj.getSprite().getX();
                float y = proj.getSprite().getY();
                if (character.getSprite().getBoundingRectangle().contains(x, y)
                        && !character.equals(proj.originator)) {
                    character.hit(proj.getAmt(), proj.getId());
                }
            }

            if (proj.canCleanup()) {
                cleanupList.add(proj);
            }
        }
        projectiles.removeAll(cleanupList);

        cleanupList = new ArrayList<>();
        for (Character character : characters) {
            if (character.canCleanup()) {
                cleanupList.add(character);
            }
        }
        characters.removeAll(cleanupList);
    }

    private void processInput(float deltaTime, Viewport viewport, Map<String, Texture> txMap) {
        List<Action> actions = player.processInputs(deltaTime);
        System.out.println(String.format("Got %d actions from processing inputs", actions.size()));

        for (Action action : actions) {
            if (action instanceof InvokeSpell) {
                InvokeSpell invokeSpell = (InvokeSpell) action;

                Vector3 end =
                        viewport.getCamera()
                                .unproject(new Vector3(invokeSpell.end.x, invokeSpell.end.y, 0));
                Projectile projectile =
                        new Projectile(
                                player,
                                String.format("fireball-%d", rand.nextInt()),
                                txMap.get("fireball"),
                                1,
                                1,
                                invokeSpell.start,
                                new Vector2(end.x, end.y),
                                2f,
                                25,
                                5);
                projectiles.add(projectile);
            }
        }

    }

    @Override
    public void drawSprites(float deltaTime, SpriteBatch batch) {
        level.drawSprites(deltaTime, batch);
        for (Drawable go : this.characters) {
            go.drawSprites(deltaTime, batch);
        }

        for (Drawable go : projectiles) {
            go.drawSprites(deltaTime, batch);
        }
    }

    @Override
    public void drawShapes(float deltaTime, ShapeRenderer shapeRenderer) {
        for (Drawable go : characters) {
            go.drawShapes(deltaTime, shapeRenderer);
        }
    }
}

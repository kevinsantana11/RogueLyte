package io.github.roguelyte;

import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
public class Game {
    private Level level;
    @Getter private Player player;
    private List<Character> characters;
    private List<Projectile> projectiles;

    public void step(float deltaTime) {
        processInput(deltaTime);
        sim(deltaTime);
    }

    public void addProjectile(Projectile proj) {
        projectiles.add(proj);
    }

    private void sim(float deltaTime) {
        List<GO> cleanupList = new ArrayList<>();
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

    private void processInput(float deltaTime) {
        List<Action> actions = player.getActions(deltaTime);
        System.out.println(String.format("Got %d actions from processing inputs", actions.size()));

        for (Action action : actions) {
            action.apply(this);
        }

    }

    public void drawSprites(float deltaTime, SpriteBatch batch) {
        // level.drawSprites(deltaTime, batch);

        List<GO> drawables = new ArrayList<>(characters);
        drawables.addAll(projectiles);

        for (GO drawable : drawables) {
            drawable.drawSprites(deltaTime, batch);
        }
    }

    public void drawShapes(float deltaTime, ShapeRenderer shapeRenderer) {
        for (GO drawable : characters) {
            drawable.drawShapes(deltaTime, shapeRenderer);
        }
    }
}

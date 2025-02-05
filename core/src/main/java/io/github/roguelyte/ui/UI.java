package io.github.roguelyte.ui;

import java.util.List;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.Viewport;

import io.github.roguelyte.Game;
import io.github.roguelyte.actions.Action;
import io.github.roguelyte.ui.panes.PlayGame;
import lombok.Getter;

public class UI {
    private @Getter Stage stage;
    private Skin skin;

    public UI(Viewport viewport, Game game) {
        this.stage = new Stage(viewport);
        Gdx.input.setInputProcessor(stage);

        skin = new Skin(Gdx.files.internal("skin/uiskin.json"));
        PlayGame playGamePane = new PlayGame(game, skin);
        stage.addActor(playGamePane);
        stage.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if (!playGamePane.interesects(x, y)) {
                    playGamePane.resetState();
                }
                super.clicked(event, x, y);
            }
        });
    }

    public List<Action> getActions(float deltaTime) {
        this.stage.act(deltaTime);
        return List.of();
    }

    public void draw() {
        this.stage.draw();

    }

    public void dispose() {
        this.stage.dispose();
    }
}

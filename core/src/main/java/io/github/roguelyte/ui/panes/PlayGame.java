package io.github.roguelyte.ui.panes;

import java.util.List;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;

import io.github.roguelyte.Game;
import io.github.roguelyte.core.Item;
import io.github.roguelyte.ui.components.Inspector;
import io.github.roguelyte.ui.components.Inventory;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PlayGame extends Table {
    Game game;
    Skin skin;
    Inventory inventory;
    Inspector inspector;
    TextButton inventoryButton;
    boolean inventoryVisible;
    boolean inspectorVisible;

    public PlayGame(Game game, Skin skin) {
        this.game = game;
        this.skin = skin;
        build();
    }

    public void resetState() {
        this.inventoryVisible = false;
        this.inspectorVisible = false;
        this.build();
    }

    public List<Actor> getActors() {
        return List.of(this.inventoryButton, this.inventory, this.inspector);
    }

    public boolean interesects(float x, float y) {
        return getActors().parallelStream().anyMatch(a -> new Rectangle(
            a.getX(),
            a.getY(),
            a.getWidth(),
            a.getHeight()).contains(x, y));
    }

    public void build() {
        clear();
        setFillParent(true);
        inventory = new Inventory(game.getPlayer(), skin, this::setInspectorItem);
        inventoryButton = new TextButton("Inventory", skin);

        PlayGame inst = this;
        inventoryButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                inst.setInventoryVisible(!inst.isInventoryVisible());
                inst.build();
                super.clicked(event, x, y);
            }
        });

        inspector = new Inspector(null, skin);

        add(inspector)
            .expandY()
            .top()
            .right()
            .colspan(2);
        row();
        add(inventory);
        add(inventoryButton)
            .expandX()
            .right();
        align(Align.bottomRight);
        inventory.setVisible(inventoryVisible);
        inspector.setVisible(inspectorVisible);
    }

    public void setInspectorItem(Item item) {
        inspector.setVisible(true);
        inspector.setItem(item);
    }
    
}

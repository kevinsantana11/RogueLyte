package io.github.roguelyte.ui.components;

import java.util.function.Consumer;

import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;

import io.github.roguelyte.actors.Player;
import io.github.roguelyte.core.Item;

public class Inventory extends Table {
    Player player;
    Skin skin;
    Consumer<Item> onSelectItem;

    public Inventory(Player player, Skin skin, Consumer<Item> onSelectItem) {
        this.player = player;
        this.skin = skin;
        this.onSelectItem = onSelectItem;
        build();
    }

    public void build() {
        clear();
        setSkin(skin);
        setBackground("default-pane");
        pad(5f);
        for (Item item : player.getInventory()) {
            add(new ItemIcon(item, skin, onSelectItem)).width(32);
        }
        
    }
}

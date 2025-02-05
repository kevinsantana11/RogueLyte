package io.github.roguelyte.ui.components;

import java.util.function.Consumer;

import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

import io.github.roguelyte.core.Item;

public class ItemIcon extends Table {
    Item item;
    Skin skin;
    Consumer<Item> onSelectItem;

    public ItemIcon(Item item, Skin skin, Consumer<Item> onSelectItem) {
        this.item = item;
        this.skin = skin;
        this.onSelectItem = onSelectItem;
        build();
    }

    public void build() {
        setSkin(skin);
        setBackground("default-pane");
        add(new Image(item.getTexture()));

        Item item = this.item;
        this.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                onSelectItem.accept(item);
                super.clicked(event, x, y);
            }
        });
    }

}

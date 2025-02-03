package io.github.roguelyte.ui.components;

import java.util.Map.Entry;

import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;

import io.github.roguelyte.core.Item;

public class Inspector extends Table {
    Skin skin;
    Item item;
    
    public Inspector(Item item, Skin skin) {
        this.skin = skin;
        this.item = item;
        build();
    }

    public void setItem(Item item) {
        this.item = item;
        build();
    }

    public void build() {
        clear();
        setSkin(skin);
        setBackground("default-pane");
        add(new Label("INSPECTOR", skin));

        if (item != null) {
            row();
            add(new Image(item.getTexture()));
            // Item name
            TextField valueField = new TextField(item.getName(), skin);
            valueField.setDisabled(true);
            add(valueField);
            row();

            add(new Label("STATS", skin)).left();
            row();

            for (Entry<String, String> entry : item.getStats().asEntrySet()) {
                add(new Label(entry.getKey(), skin));
                valueField = new TextField(entry.getValue(), skin);
                valueField.setDisabled(true);
                add(valueField);
                row();
            }
        }
    }
}

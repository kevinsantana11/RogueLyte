package io.github.rogue_lyte;

import java.util.Map;
import java.util.Map.Entry;

import com.badlogic.gdx.graphics.Texture;

public class TextureManager {
    Map<String, Texture> textureMap;
    public TextureManager(Map<String, Texture> initialTxMap) {
        textureMap = initialTxMap;
    }

    public void put(String key, Texture texture) {
        textureMap.put(key, texture);
    }

    public Texture get(String key) {
        return textureMap.get(key);
    }

    public void cleanup() {
        for(Entry<String, Texture> entry : textureMap.entrySet()) {
            entry.getValue().dispose();
        }
    }
}

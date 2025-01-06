package io.github.roguelyte;

import com.badlogic.gdx.math.Vector2;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class InvokeSpell implements Action {
    public String spellName;
    public Vector2 start;
    public Vector2 end;
}

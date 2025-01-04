package io.github.rogue_lyte;

import com.badlogic.gdx.math.Vector2;

public class InvokeSpell extends Action {
    public String spellName;
    public Vector2 start;
    public Vector2 end;

    public InvokeSpell(String spellName, Vector2 start, Vector2 end) {
        this.spellName = spellName;
        this.start = start;
        this.end = end;
    }
}

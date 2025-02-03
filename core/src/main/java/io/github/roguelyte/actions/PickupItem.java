package io.github.roguelyte.actions;

import io.github.roguelyte.Game;
import io.github.roguelyte.actors.AcquirableItem;
import io.github.roguelyte.actors.Player;
import lombok.AllArgsConstructor;

import static io.github.roguelyte.utils.Utility.spritesOverlaping;

@AllArgsConstructor
public class PickupItem implements Action {
    Player player;

    @Override
    public void apply(Game game) {
        for(AcquirableItem item : game.getItems()) {
            if (spritesOverlaping(player, item)) {
                player.addItem(item.getItem());
                item.setAcquired(true);
            }
        }
    }
    
}

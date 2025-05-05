package net.doodcraft.cozmyc.fireproof;

import com.projectkorra.projectkorra.BendingPlayer;
import com.projectkorra.projectkorra.Element;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;

import java.util.function.Predicate;

public class FireproofListener implements Listener {

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onEntityDamage(EntityDamageEvent event) {
        if (!(event.getEntity() instanceof Player player)) return;

        BendingPlayer bPlayer = BendingPlayer.getBendingPlayer(player);
        if (bPlayer == null
                || !bPlayer.canBendInWorld()
                || !bPlayer.hasElement(Element.FIRE)
                || !bPlayer.isPassiveToggled(Element.FIRE)
                || !bPlayer.isToggledPassives()) {
            return;
        }

        Predicate<Player> canceller =
                Fireproof.CANCEL_PREDICATES.get(event.getCause());

        if (canceller != null && canceller.test(player)) {
            player.setFireTicks(0);
            event.setCancelled(true);
        }
    }
}

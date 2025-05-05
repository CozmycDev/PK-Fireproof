package net.doodcraft.cozmyc.fireproof;

import com.projectkorra.projectkorra.BendingPlayer;
import com.projectkorra.projectkorra.Element;
import com.projectkorra.projectkorra.configuration.ConfigManager;

import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;

public class FireproofListener implements Listener {

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onEntityDamageEvent(EntityDamageEvent event) {
        Entity entity = event.getEntity();

        if (!(entity instanceof Player player)) return;

        BendingPlayer bPlayer = BendingPlayer.getBendingPlayer(player);
        if (bPlayer == null || !bPlayer.canBendInWorld()) return;
        if (!bPlayer.hasElement(Element.FIRE)) return;
        if (!bPlayer.isPassiveToggled(Element.FIRE)) return;
        if (!bPlayer.isToggledPassives()) return;
        if (!player.hasPermission("bending.ability.fireproof")) return;

        if (event.getCause() == EntityDamageEvent.DamageCause.FIRE || event.getCause() == EntityDamageEvent.DamageCause.FIRE_TICK) {
            player.setFireTicks(0);
            event.setCancelled(true);
        }

        if (event.getCause() == EntityDamageEvent.DamageCause.HOT_FLOOR) {
            if (ConfigManager.defaultConfig.get().getBoolean("ExtraAbilities.Cozmyc.Fireproof.MagmaResistance")) {
                player.setFireTicks(0);
                event.setCancelled(true);
            }
        }

        if (event.getCause() == EntityDamageEvent.DamageCause.LAVA) {
            if (ConfigManager.defaultConfig.get().getBoolean("ExtraAbilities.Cozmyc.Fireproof.LavaResistance")) {
                player.setFireTicks(0);
                event.setCancelled(true);
            }
        }
    }
}

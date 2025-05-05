package net.doodcraft.cozmyc.fireproof;

import com.projectkorra.projectkorra.ProjectKorra;
import com.projectkorra.projectkorra.ability.AddonAbility;
import com.projectkorra.projectkorra.ability.FireAbility;
import com.projectkorra.projectkorra.ability.PassiveAbility;
import com.projectkorra.projectkorra.configuration.Config;
import com.projectkorra.projectkorra.configuration.ConfigManager;

import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.permissions.Permission;
import org.bukkit.plugin.PluginManager;

import java.util.Map;
import java.util.function.Predicate;
import java.util.logging.Level;

public class Fireproof extends FireAbility implements AddonAbility, PassiveAbility {

    public static final String PERMISSION_BASE = "bending.ability.fireproof";
    public static final String MAGMA_PERMISSION = PERMISSION_BASE + ".magma";
    public static final String LAVA_PERMISSION = PERMISSION_BASE + ".lava";

    public static final Map<EntityDamageEvent.DamageCause, Predicate<Player>> CANCEL_PREDICATES =
            Map.ofEntries(
                    Map.entry(EntityDamageEvent.DamageCause.FIRE,
                            player -> player.hasPermission(PERMISSION_BASE)),
                    Map.entry(EntityDamageEvent.DamageCause.FIRE_TICK,
                            player -> player.hasPermission(PERMISSION_BASE)),
                    Map.entry(EntityDamageEvent.DamageCause.HOT_FLOOR,
                            player -> ConfigManager.defaultConfig.get()
                                    .getBoolean("ExtraAbilities.Cozmyc.Fireproof.MagmaResistance")
                                    && hasEffectivePermission(player, MAGMA_PERMISSION)),
                    Map.entry(EntityDamageEvent.DamageCause.LAVA,
                            player -> ConfigManager.defaultConfig.get()
                                    .getBoolean("ExtraAbilities.Cozmyc.Fireproof.LavaResistance")
                                    && hasEffectivePermission(player, LAVA_PERMISSION))
            );

    private static boolean hasEffectivePermission(Player player, String specific) {
        if (player.isPermissionSet(specific)) {
            return player.hasPermission(specific);
        }
        return player.hasPermission(PERMISSION_BASE);
    }

    public Fireproof(Player player) {
        super(player);
    }

    @Override
    public void progress() {}

    @Override
    public boolean isSneakAbility() {
        return false;
    }

    @Override
    public boolean isHarmlessAbility() {
        return true;
    }

    @Override
    public boolean isInstantiable() {
        return false;
    }

    @Override
    public boolean isProgressable() {
        return false;
    }

    @Override
    public long getCooldown() {
        return 0;
    }

    @Override
    public Location getLocation() {
        return this.player.getLocation();
    }

    @Override
    public String getName() {
        return "Fireproof";
    }

    @Override
    public String getAuthor() {
        return "LuxaelNI, Cozmyc";
    }

    @Override
    public String getVersion() {
        return "0.0.2";
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    @Override
    public String getDescription() {
        return ConfigManager.languageConfig.get().getString("Abilities.Fire.Fireproof.Description",
                "Missing Description. Check PK's lang.yml.");
    }

    @Override
    public String getInstructions() {
        return ConfigManager.languageConfig.get().getString("Abilities.Fire.Fireproof.Instructions",
                "Missing Instructions. Check PK's lang.yml.");
    }

    @Override
    public void load() {
        ProjectKorra.plugin.getServer()
                .getPluginManager()
                .registerEvents(new FireproofListener(), ProjectKorra.plugin);

        PluginManager pm = ProjectKorra.plugin.getServer().getPluginManager();
        for (String node : new String[]{MAGMA_PERMISSION, LAVA_PERMISSION}) {
            if (pm.getPermission(node) == null) {
                Permission perm = new Permission(node,
                        "Bypass " + node.substring(node.lastIndexOf('.')+1) + " damage");
                perm.addParent(PERMISSION_BASE, true);
                pm.addPermission(perm);
            }
        }

        Config config = ConfigManager.defaultConfig;

        config.get().addDefault("ExtraAbilities.Cozmyc.Fireproof.MagmaResistance", false);
        config.get().addDefault("ExtraAbilities.Cozmyc.Fireproof.LavaResistance", false);
        config.save();

        Config lang = ConfigManager.languageConfig;

        lang.get().addDefault("Abilities.Fire.Fireproof.Description",
                "Skilled Firebenders are able to control the heat of surrounding fire, allowing them to avoid burn damage.");
        lang.get().addDefault("Abilities.Fire.Fireproof.Instructions",
                "Toggle this ability using /pk toggle FirePassives");
        lang.save();

        ProjectKorra.log.log(Level.INFO, "Fireproof 0.0.2 by LuxaelNI and Cozmyc is now enabled!");
    }

    @Override
    public void stop() {}
}

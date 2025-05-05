package net.doodcraft.cozmyc.fireproof;

import com.projectkorra.projectkorra.ability.AddonAbility;
import com.projectkorra.projectkorra.ability.FireAbility;
import com.projectkorra.projectkorra.ability.PassiveAbility;
import com.projectkorra.projectkorra.ProjectKorra;
import com.projectkorra.projectkorra.configuration.ConfigManager;

import org.bukkit.Location;
import org.bukkit.entity.Player;

import java.util.logging.Level;

public class Fireproof extends FireAbility implements AddonAbility, PassiveAbility {

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
    public boolean isIgniteAbility() {
        return false;
    }

    @Override
    public boolean isExplosiveAbility() {
        return false;
    }

    @Override
    public long getCooldown() {
        return 0;
    }

    @Override
    public String getName() {
        return "Fireproof";
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
    public Location getLocation() {
        return this.player.getLocation();
    }

    @Override
    public void load() {
        ProjectKorra.plugin.getServer().getPluginManager().registerEvents(new FireproofListener(), ProjectKorra.plugin);

        ConfigManager.defaultConfig.get().addDefault("ExtraAbilities.Cozmyc.Fireproof.LavaResistance", false);
        ConfigManager.defaultConfig.get().addDefault("ExtraAbilities.Cozmyc.Fireproof.MagmaResistance", false);

        ConfigManager.defaultConfig.save();

        ConfigManager.languageConfig.get().addDefault("Abilities.Fire.Fireproof.Description",
                "Skilled Firebenders are able to control the heat of surrounding fire, allowing them to avoid burn damage.");
        ConfigManager.languageConfig.get().addDefault("Abilities.Fire.Fireproof.Instructions",
                "Toggle this ability using /pk toggle FirePassives");

        ConfigManager.languageConfig.save();

        ProjectKorra.log.log(Level.INFO, "Fireproof 0.0.1 by LuxaelNI and Cozmyc is now enabled!");
    }

    @Override
    public void stop() {}

    @Override
    public String getAuthor() {
        return "LuxaelNI, Cozmyc";
    }

    @Override
    public String getVersion() {
        return "0.0.1";
    }

    @Override
    public boolean isEnabled() {
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
}

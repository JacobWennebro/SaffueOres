package com.wennebro.saffueores;

import com.wennebro.saffueores.Events.BlockBreak;
import net.milkbowl.vault.economy.Economy;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.logging.Level;

public final class SaffueOres extends JavaPlugin {

    private static Economy econ = null;

    private boolean setupEconomy() {
        if (getServer().getPluginManager().getPlugin("Vault") == null) {
            return false;
        }
        RegisteredServiceProvider<Economy> rsp = getServer().getServicesManager().getRegistration(Economy.class);
        if (rsp == null) {
            return false;
        }
        econ = rsp.getProvider();
        return econ != null;
    }

    @Override
    public void onEnable() {
        // Plugin startup logic
        if (!setupEconomy()) {
            getLogger().log(Level.SEVERE, "Failed to hook into Vault, disabling plugin!");
            getServer().getPluginManager().disablePlugin(this);
            return;
        }

        // Config setup
        getConfig().options().copyDefaults();
        saveDefaultConfig();

        // Event handlers
        PluginManager man = getServer().getPluginManager();
        man.registerEvents(new BlockBreak(this), this);
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

    public static Economy getEconomy() {
        return econ;
    }

}

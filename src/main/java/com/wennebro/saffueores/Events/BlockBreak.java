package com.wennebro.saffueores.Events;

import com.wennebro.saffueores.SaffueOres;
import de.ancash.actionbar.ActionBarAPI;
import net.milkbowl.vault.economy.Economy;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.plugin.Plugin;

import java.util.Arrays;

public class BlockBreak implements Listener {

    private final Economy econ = SaffueOres.getEconomy();
    private Plugin plugin = null;

    public BlockBreak(Plugin plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void blockBreak(BlockBreakEvent e) {
        Player p = e.getPlayer();
        FileConfiguration c = plugin.getConfig();

        // Check if the player's current world is included in the allowed list.
        if(!c.getList("allowed-worlds").contains(p.getWorld().getName())) return;

        Block b = e.getBlock();

        if(c.contains("blocks."+ b.getType())) {
            int blockValue = c.getInt("blocks."+ b.getType());

            // Pay the player, only if value is not equal to 0
            // this allows for negative values if you ever
            // wanted that for whatever reason. :')
            if(blockValue != 0) {
                econ.depositPlayer(p, blockValue);
                Plugin AbAPI = plugin.getServer().getPluginManager().getPlugin("ActionBarAPI");
                if(AbAPI != null) ActionBarAPI.sendActionBar(p, ChatColor.GREEN+"+"+blockValue+" credits");
            }

            if(!c.getBoolean("block-drops")) {
                b.setType(Material.AIR);
                e.setCancelled(true);
            }

        }

    }

}

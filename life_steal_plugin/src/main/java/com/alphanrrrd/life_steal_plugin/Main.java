package com.alphanrrrd.life_steal_plugin;

import org.bukkit.plugin.java.JavaPlugin;
public class Main extends JavaPlugin {
    @Override
    public void onEnable() {
        
        getLogger().info("Starting Life-Steal-Plugin");
    }
    @Override
    public void onDisable() {
        getLogger().info("See you again, SpigotMC!");
    }
}
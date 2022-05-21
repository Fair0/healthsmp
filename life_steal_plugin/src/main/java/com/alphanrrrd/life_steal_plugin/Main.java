package com.alphanrrrd.life_steal_plugin;

import org.bukkit.plugin.java.JavaPlugin;

import java.util.UUID;
import java.util.HashMap;


public class Main extends JavaPlugin {
    private mainListener listener;
    private jsonHandler json = new jsonHandler(new HashMap<UUID, Double>());

    @Override
    public void onEnable() {
        listener = new mainListener(json.getPlayerInfo());
        //getLogger().info("Starting Life-Steal-Plugin");
    }
    @Override
    public void onDisable() {
        json.writeJSONData();
        //getLogger().info("See you again, SpigotMC!");
    }
}
package com.alphanrrrd.life_steal_plugin;

import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.Bukkit;
import org.bukkit.command.ConsoleCommandSender;

import java.util.UUID;
import java.util.HashMap;


public class Main extends JavaPlugin {
    private mainListener listener;
    private jsonHandler json = new jsonHandler(new HashMap<UUID, Double>());
    private ConsoleCommandSender console = Bukkit.getConsoleSender();

    @Override
    public void onEnable() {
        listener = new mainListener(json.getPlayerInfo());
        console.sendMessage("Starting Life-Steal-Plugin");
    }
    @Override
    public void onDisable() {
        json.writeJSONData();
        console.sendMessage("See you again, SpigotMC!");
    }
}
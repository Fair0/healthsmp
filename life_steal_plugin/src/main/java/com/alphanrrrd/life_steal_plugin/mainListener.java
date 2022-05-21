package com.alphanrrrd.life_steal_plugin;

import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.bukkit.entity.Player;
import org.bukkit.Bukkit;
import org.bukkit.command.ConsoleCommandSender;

import java.util.UUID;
import java.util.HashMap;

public class mainListener implements Listener 
{
    private HashMap<UUID, Double> playerInfo;
    private ConsoleCommandSender console = Bukkit.getConsoleSender();

    public mainListener(HashMap<UUID, Double> playerInfo) {
        this.playerInfo = playerInfo;
    }

    @EventHandler
    public void onPlayerDeath(PlayerDeathEvent event) {
        console.sendMessage(event.getEntity().getDisplayName() + "DIED LOL!");
    }

    @EventHandler
    public void onPlayerRespawn(PlayerRespawnEvent event) {
        console.sendMessage(event.getPlayer().getDisplayName() + " respawned.");
    }
    
    @EventHandler
    public void PlayerSpawnLocationEvent(Player person) {
        console.sendMessage("LOL " + person.getDisplayName() + " JOINED THE SERVER!");
    }
}

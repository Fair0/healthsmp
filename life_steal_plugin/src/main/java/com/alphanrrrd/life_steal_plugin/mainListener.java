package com.alphanrrrd.life_steal_plugin;

import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.EventHandler;

import java.util.UUID;
import java.util.HashMap;

public class mainListener implements Listener 
{
    private HashMap<UUID, Double> playerInfo;

    public mainListener(HashMap<UUID, Double> playerInfo) {
        this.playerInfo = playerInfo;
    }

    @EventHandler
    public void onPlayerDeath(EntityDeathEvent event) {
        //getLogger().sendMessage("LOL YOU DIED!");
    }
    /*
    @EventHandler
    public void PlayerSpawnLocationEvent(Player person) {
        getLogger().sendMessage("LOL " + person.getDisplayName() + " JOINED THE SERVER!");
    }*/
}

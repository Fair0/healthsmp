package com.alphanrrrd.life_steal_plugin;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import org.bukkit.Bukkit;
import org.bukkit.command.ConsoleCommandSender;

import java.util.HashMap;
import java.util.UUID;

public class jsonHandler {
    private HashMap<UUID, Double> playerInfo;
    private ConsoleCommandSender console = Bukkit.getConsoleSender();

    public jsonHandler(HashMap<UUID, Double> playerInfo) {
        this.playerInfo = playerInfo;
        populateJSONData();
    }

    private void populateJSONData() {
        /**
         * Will implement soon
         */
    }

    public void writeJSONData() {
        /**
         * Will implement soon
         */
    }

    public HashMap<UUID, Double> getPlayerInfo() {
        return playerInfo;
    }
}

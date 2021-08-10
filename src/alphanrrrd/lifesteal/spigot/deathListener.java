package alphanrrrd.lifesteal.spigot;

import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;

import java.util.UUID;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.attribute.Attribute;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.bukkit.event.EventHandler;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class deathListener implements Listener {
	//Sets up the class attributes so that they can be accessed in any method.
	ConsoleCommandSender console = Bukkit.getConsoleSender(); //This is the console because I didnt want to have to retype the console command whenever I wanted to print messages to the console.
	LivingEntity killed; //This is the killed player used in the onPlayerDeath event handler to update the health JSON object.
	Player killer; //This is the killer player used in the onPlayerDeath event handler to update the health JSON object.
	Player tempPlayer; //Temp player used to set health on respawn
	UUID killedUUID; //The UUID for the killed person used as the 'key' in the JSON object.
	UUID killerUUID; //The UUID for the killer that is used as the 'key' in the JSON object.
	public JSONObject playerDictJSON; //The JSON object that can be accessed in other classes because its public.

	@SuppressWarnings("unchecked") //org.json.simple doesnt know how to handle UUID's and Integers as they key value pair so it causes an unchecked warning. It works either way so I suppressed them to prevent compiler warnings.
	@EventHandler
	public void onPlayerDeath(EntityDeathEvent event) {
		/*This is the event handler for the death of a player.*/
		this.killed = event.getEntity();
		if(this.killed instanceof Player) {
			this.killedUUID = this.killed.getUniqueId();
			try{
				this.killer = event.getEntity().getKiller();
				try {
					this.killerUUID = event.getEntity().getKiller().getUniqueId();
				} catch(NullPointerException e) {
					this.console.sendMessage("Killer UUID is null. Most likely caused by no killer.");
				}
			} catch(NullPointerException e) {
				this.console.sendMessage("Killer is null most likely caused by server commands, the reset-plugin command, mobs, or natural causes.");
			}
			this.console.sendMessage("Killer is: " + this.killer);
			/*These three if block updates the health of the players that are involved in the PlayerDeathEvent and if they are not in the JSON
			 * object they are added to it.*/
			if((this.playerDictJSON.get(this.killerUUID) != null && this.playerDictJSON.get(killedUUID) != null) && this.killer != null){
				this.playerDictJSON.replace(killedUUID, (int)this.killed.getAttribute(Attribute.GENERIC_MAX_HEALTH).getValue() - 2);
				this.playerDictJSON.replace(killerUUID, (int)this.killer.getAttribute(Attribute.GENERIC_MAX_HEALTH).getValue() + 2);
				updateKillerHealth(this.killer);
			}
			if(this.playerDictJSON.get(this.killedUUID) == null && this.killer != null) {
				this.console.sendMessage("Added Player " + this.killedUUID + " to the Player Dictionary.");
				this.playerDictJSON.put(this.killedUUID, (int)this.killed.getAttribute(Attribute.GENERIC_MAX_HEALTH).getValue() - 2);
			}
			if(this.playerDictJSON.get(this.killerUUID) == null && this.killer != null) {
				this.console.sendMessage("Added Player " + this.killerUUID + " to the Player Dictionary.");
				this.playerDictJSON.put(this.killerUUID, (int)this.killer.getAttribute(Attribute.GENERIC_MAX_HEALTH).getValue() + 2);
				//Updates the killers health since the killed will update on respawn.
				updateKillerHealth(this.killer);
			}
		}
	}
	
	@SuppressWarnings("unchecked")
	@EventHandler
	public void onPlayerRespawn(PlayerRespawnEvent event) {
		/*When the player respawns it does checks on their health and updates them based on the JSON file. If they have 0 health in the json
		 * file it puts them in spectator mode and displays a message in the game chat.*/
		try {
			this.tempPlayer = event.getPlayer();
			UUID tempKeyName = this.tempPlayer.getUniqueId();
			Integer tempInt = (Integer) playerDictJSON.get(tempKeyName);
			if(tempInt == 0) {
				this.console.getServer().broadcastMessage(tempPlayer.getDisplayName() + " has lost all their hearts and is now in spectator mode.");
				this.tempPlayer.setGameMode(GameMode.SPECTATOR);
			} else {
				this.tempPlayer.getAttribute(Attribute.GENERIC_MAX_HEALTH).setBaseValue(tempInt);
				this.console.sendMessage(tempPlayer.getDisplayName() + " now has " + this.tempPlayer.getAttribute(Attribute.GENERIC_MAX_HEALTH).getValue() + " health.");
			}
		} catch(NullPointerException e) {
			/*This will catch the NullPointerException that the reset-plugin command causes and will set their base health to 20*/
			this.playerDictJSON.put(this.tempPlayer.getUniqueId(), 20);
			this.tempPlayer.getAttribute(Attribute.GENERIC_MAX_HEALTH).setBaseValue(20);
			this.console.sendMessage(tempPlayer.getDisplayName() + " has had their health reset to 20.");
		}
	}
	
	public void updateKillerHealth(Player killer) {
		/*Updates the killers health from their current max health.*/
		killer.getAttribute(Attribute.GENERIC_MAX_HEALTH).setBaseValue(killer.getAttribute(Attribute.GENERIC_MAX_HEALTH).getValue() + 2);
		this.console.sendMessage(killer.getDisplayName() + " now has " + killer.getAttribute(Attribute.GENERIC_MAX_HEALTH).getValue() + " health.");
	}
	
	public void updateJSONFile() {
		/*Opens the JSON File and writes the json string to it.*/
		try {
	         FileWriter file = new FileWriter("playerHealthDict.json");
	         file.write(this.playerDictJSON.toJSONString());
	         file.close();
	    } catch (IOException e) {
	         this.console.sendMessage("When trying to write the json file the FileWriter encountered and IOException. Please make sure that the directory is not read only and that there is not already a directory called playerHealthDict.json");
	         e.printStackTrace();
	    }
	}
	
	public void loadJSONFile() {
		/*Loads the file as the JSON object at the startup of the plugin. If the file doesnt exist it creates it and initializes it with
		 * default JSON parameters.*/
		try {
			JSONParser jsonParser = new JSONParser();
			this.playerDictJSON = (JSONObject) jsonParser.parse(new FileReader("playerHealthDict.json"));
			
		} catch(IOException | ParseException e) {
			this.console.sendMessage("The file playerHealthDict.json does not exist or has been tampered with, creating it.");
			try {
				FileWriter file = new FileWriter("playerHealthDict.json");
				this.playerDictJSON = new JSONObject();
				file.write(this.playerDictJSON.toJSONString());
				file.close();
			} catch (IOException e1) {
				this.console.sendMessage("Can't create the json file somthing must be very wrong. For now players wont be saved.");
				e1.printStackTrace();
			}
		}
	}
}

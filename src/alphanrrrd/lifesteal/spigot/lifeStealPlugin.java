package alphanrrrd.lifesteal.spigot;

import org.bukkit.plugin.java.JavaPlugin;

import alphanrrrd.lifesteal.spigot.commands.eliminatePlayer;
import alphanrrrd.lifesteal.spigot.commands.resetPlugin;
import alphanrrrd.lifesteal.spigot.commands.revivePlayer;


public class lifeStealPlugin extends JavaPlugin {
	//Defines the death listener before the registerEvents uses it so that other function calls can use it and its variables.
	//The deathListener is an event handler for PlayerDeathEvent and PlayerRespawnEvent and it also updates the JSON file.
	deathListener deathL = new deathListener();
	
	/*The main onEnable function which loads the json file that contains the player health, calls the death listener as the event handler, and
	 * registers the commands. (The JSON File is portable with this plugin since it uses UUIDs so it can be moved to other servers with the 
	 * same plugin to "sync" health across servers.*/
	@Override
	public void onEnable() {
		this.deathL.loadJSONFile();
		getServer().getPluginManager().registerEvents(this.deathL, this);
		getCommand("reset-plugin").setExecutor(new resetPlugin(this.deathL));
		getCommand("revive-player").setExecutor(new revivePlayer(this.deathL));
		getCommand("eliminate-player").setExecutor(new eliminatePlayer(this.deathL));
	}
	
	/*The onDisable function calls when the server is closed and updates the json file.*/
	@Override
	public void onDisable() {
		this.deathL.updateJSONFile();
	}
}
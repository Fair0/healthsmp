package alphanrrrd.lifesteal.spigot.commands;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.json.simple.JSONObject;

import alphanrrrd.lifesteal.spigot.deathListener;

public class resetPlugin implements CommandExecutor {
	/*The deathL class that can be accessed anywhere in here with 'this'*/
	private deathListener deathL;
	
	/*The constrctor that adds the deathL object created in the main class to the deathLObj variable.*/
	public resetPlugin(deathListener deathL) {
		this.deathL = deathL;
	}

	/*Resets the JSON Object to nothing and kills all players so that the respawn event sets their health to 20.*/
	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		this.deathL.playerDictJSON = new JSONObject();
		Bukkit.dispatchCommand(sender, "kill @e[type=minecraft:player]");
		return false;
	}
}

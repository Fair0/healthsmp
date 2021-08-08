package alphanrrrd.lifesteal.spigot.commands;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.attribute.Attribute;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.UUID;

import alphanrrrd.lifesteal.spigot.deathListener;

public class revivePlayer implements CommandExecutor {
	/*The deathL class that can be accessed anywhere in here with 'this'*/
	private deathListener deathLObj;
	
	/*The constrctor that adds the deathL object created in the main class to the deathLObj variable.*/
	public revivePlayer(deathListener deathL) {
		this.deathLObj = deathL;
	}

	@SuppressWarnings("unchecked")
	@Override
	public boolean onCommand(CommandSender sender, Command command, String firstArg, String[] args) {
		/*This command revives the player and takes a name and an amount of half-hearts to do so. It will also warn the player that is using
		 * the command if they have typed wrong values.*/
		try {
			Player tempPlayer = Bukkit.getPlayerExact(args[0]);
			UUID playerUUID = tempPlayer.getUniqueId();
		    tempPlayer.setGameMode(GameMode.SURVIVAL);
		    try {
		    	tempPlayer.getAttribute(Attribute.GENERIC_MAX_HEALTH).setBaseValue(Integer.parseInt(args[1]));
		    	this.deathLObj.playerDictJSON.replace(playerUUID, Integer.parseInt(args[1]));
		    } catch(ArrayIndexOutOfBoundsException e) {
		    	sender.sendMessage("No health value was passed defaulting to 20 health.");
		    	tempPlayer.getAttribute(Attribute.GENERIC_MAX_HEALTH).setBaseValue(20);
		    	this.deathLObj.playerDictJSON.replace(playerUUID, 20);
		    } catch(NumberFormatException e) {
		    	sender.sendMessage("You can't type letters in the health area please type only numbers. Defaulting to 20 health.");
		    	tempPlayer.getAttribute(Attribute.GENERIC_MAX_HEALTH).setBaseValue(20);
		    	this.deathLObj.playerDictJSON.replace(playerUUID, 20);
		    }
		} catch(NullPointerException e) {
			sender.sendMessage("That player doesn't exist.");
		} catch(ArrayIndexOutOfBoundsException e) {
			sender.sendMessage("You didn't type a player name");
		}
		return false;
	}
}
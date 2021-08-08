package alphanrrrd.lifesteal.spigot.commands;

import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import alphanrrrd.lifesteal.spigot.deathListener;

public class eliminatePlayer implements CommandExecutor {
	private deathListener deathLObj;

	public eliminatePlayer(deathListener deathL) {
		this.deathLObj = deathL;
	}

	@SuppressWarnings("unchecked")
	@Override
	public boolean onCommand(CommandSender sender, Command command, String firstArg, String[] args) {
		/*This eliminates a player using a name. It finds the UUID of they player from the name and then sets their health to 0 in the json 
		 * object so they cant go back to survival without the revive-player command. This can be used by server operators to kill people at 
		 * will.*/
		try {
			Player tempPlayer = Bukkit.getPlayerExact(args[0]);
			UUID playerUUID = tempPlayer.getUniqueId();
		    tempPlayer.setGameMode(GameMode.SPECTATOR);
	    	Bukkit.getConsoleSender().getServer().broadcastMessage(tempPlayer.getDisplayName() + " has been eliminated by a server operator.");
	    	this.deathLObj.playerDictJSON.replace(playerUUID, 0);
		} catch(NullPointerException e) {
			sender.sendMessage("That player doesn't exist.");
		} catch(ArrayIndexOutOfBoundsException e) {
			sender.sendMessage("You didn't type a player name");
		}
		return false;
	}
}
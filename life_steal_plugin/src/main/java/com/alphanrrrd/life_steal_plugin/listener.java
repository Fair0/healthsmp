import org.bukkit.event.Listener;
import org.spigotmc.event.player.PlayerSpawnLocationEvent;

import org.bukkit.event.EntityDeathEvent;
import org.bukkit.entity.Player;

public class listener implements Listener {
    
    @EventHandler
    public void onPlayerDeath(EntityDeathEvent event) {
        getLogger().sendMessage("LOL YOU DIED!");
    }

    @EventHandler
    public void PlayerSpawnLocationEvent(Player person) {
        getLogger().sendMessage("LOL " + person.getDisplayName() + " JOINED THE SERVER!");
    }
}

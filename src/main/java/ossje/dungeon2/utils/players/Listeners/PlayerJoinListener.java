package ossje.dungeon2.utils.players.Listeners;

import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import ossje.dungeon2.utils.players.PlayerManager;

public class PlayerJoinListener implements Listener {

    @EventHandler
    public void onPlayerJoin(org.bukkit.event.player.PlayerJoinEvent event) {
        Player player = event.getPlayer();

        player.teleport(new Location(player.getWorld(), 25, 20, 22)); // HUB location TODO: Change this to the actual hub location
        player.setHealth(20);
        player.setFoodLevel(20);
        player.setFlying(false);
        player.setFireTicks(0);
        player.setGameMode(GameMode.CREATIVE); // Set to CREATIVE for testing purposes, change to ADVENTURE in production

        PlayerManager playerManager = PlayerManager.getInstance();
        playerManager.addPlayer(player);
    }
}

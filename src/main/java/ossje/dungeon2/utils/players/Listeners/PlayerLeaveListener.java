package ossje.dungeon2.utils.players.Listeners;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;
import ossje.dungeon2.utils.players.PlayerManager;

public class PlayerLeaveListener implements Listener {

    @EventHandler
    public void onPlayerLeave(PlayerQuitEvent event) {
        PlayerManager.getInstance().removePlayer(
                PlayerManager.getInstance().getPlayer(event.getPlayer())
        );;
    }
}

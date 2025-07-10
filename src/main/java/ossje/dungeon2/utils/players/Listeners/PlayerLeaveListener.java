package ossje.dungeon2.utils.players.Listeners;

import com.j256.ormlite.dao.Dao;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;
import ossje.dungeon2.database.Database;
import ossje.dungeon2.utils.players.DungeonPlayer;
import ossje.dungeon2.utils.players.PlayerManager;

public class PlayerLeaveListener implements Listener {

    @EventHandler
    public void onPlayerLeave(PlayerQuitEvent event) {
        DungeonPlayer player = PlayerManager.getInstance().getPlayer(event.getPlayer());
        player.setGameManager(null);
        player.setInGame(false);

        try {
            Dao<DungeonPlayer, String> dao = Database.getPlayerDao();
            dao.update(player);
        } catch (Exception e) {
            System.out.println("player not found or Corrupt");;
        }

        PlayerManager.getInstance().removePlayer(player);
    }
}
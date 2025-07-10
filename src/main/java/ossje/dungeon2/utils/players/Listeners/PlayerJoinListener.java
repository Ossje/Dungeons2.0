package ossje.dungeon2.utils.players.Listeners;

import com.j256.ormlite.dao.Dao;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import ossje.dungeon2.database.Database;
import ossje.dungeon2.utils.players.DungeonPlayer;
import ossje.dungeon2.utils.players.PlayerManager;

public class PlayerJoinListener implements Listener {

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();

        // Teleport to hub
        player.teleport(new Location(player.getWorld(), 28, -60, 73)); // TODO: Change this to config
        player.setHealth(20);
        player.setFoodLevel(20);
        player.setFlying(false);
        player.setFireTicks(0);
        player.setGameMode(GameMode.CREATIVE); // Change to ADVENTURE in prod

        try {
            Dao<DungeonPlayer, String> dao = Database.getPlayerDao();
            String uuid = player.getUniqueId().toString();

            DungeonPlayer data = dao.queryForId(uuid);

            if (data == null) {
                data = new DungeonPlayer(player);
                dao.create(data);
            }

            PlayerManager.getInstance().addPlayer(data);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
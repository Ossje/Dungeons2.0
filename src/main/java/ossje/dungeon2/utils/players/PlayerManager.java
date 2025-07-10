package ossje.dungeon2.utils.players;

import lombok.Getter;
import org.bukkit.entity.Player;
import java.util.HashSet;
import java.util.Set;

public class PlayerManager {
    @Getter
    private final Set<DungeonPlayer> onlinePlayers = new HashSet<DungeonPlayer>();

    private static PlayerManager instance;
    public static PlayerManager getInstance() {
        if (instance == null) {
            instance = new PlayerManager();
        }
        return instance;
    }

    public DungeonPlayer addPlayer(Player player) {
        DungeonPlayer dungeonPlayer = new DungeonPlayer(player);
        onlinePlayers.add(dungeonPlayer);

        return dungeonPlayer;
    }

    public DungeonPlayer addPlayer(DungeonPlayer player) {
        onlinePlayers.add(player);

        return player;
    }

    public void removePlayer(DungeonPlayer dungeonPlayer) {
        onlinePlayers.remove(dungeonPlayer);
    }

    public  DungeonPlayer getPlayer(Player player) {
        return onlinePlayers.stream()
                .filter(dungeonPlayer -> dungeonPlayer.getUuid().equals(player.getUniqueId().toString()))
                .findFirst()
                .orElse(null);
    }
}

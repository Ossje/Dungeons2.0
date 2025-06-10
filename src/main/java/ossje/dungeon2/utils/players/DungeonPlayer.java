package ossje.dungeon2.utils.players;

import lombok.Getter;
import lombok.Setter;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import ossje.dungeon2.utils.game.GameManager;

import java.util.UUID;

public class DungeonPlayer {
    @Getter private UUID uuid;
    @Getter private  boolean inGame = false;
    @Getter @Setter private GameManager gameManager;

    public DungeonPlayer(Player player) {
        this.uuid = player.getUniqueId();
    }

    public Player getBukkit() {
        if (!Bukkit.isPrimaryThread()) throw new IllegalStateException("Cannot only get Bukkit player on main thread");

        Player player = Bukkit.getPlayer(this.getUuid());
        if (player == null) throw new IllegalStateException("Player is not online");

        return player;
    }
}

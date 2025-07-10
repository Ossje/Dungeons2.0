package ossje.dungeon2.utils.players;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import ossje.dungeon2.utils.game.GameManager;

import java.util.UUID;
@DatabaseTable(tableName = "player_data")
public class DungeonPlayer {
    @Getter @DatabaseField(id = true) private String uuid;
    @Getter @DatabaseField int coins = 0;
    @Getter @DatabaseField int gems = 0;
    @Getter @Setter private  boolean inGame = false;
    @Getter @Setter private GameManager gameManager;
    public  DungeonPlayer() {}

    public DungeonPlayer(Player player) {
        this.uuid = player.getUniqueId().toString();
    }

    public Player getBukkit() {
        if (!Bukkit.isPrimaryThread()) throw new IllegalStateException("Cannot only get Bukkit player on main thread");

        Player player = Bukkit.getPlayer(UUID.fromString(this.getUuid()));;
        if (player == null) throw new IllegalStateException("Player is not online");

        return player;
    }
}

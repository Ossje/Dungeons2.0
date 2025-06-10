package ossje.dungeon2.utils.game;

import lombok.Getter;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import ossje.dungeon2.Dungeon2;
import ossje.dungeon2.utils.dugeongen.DungeonManager;
import ossje.dungeon2.utils.game.states.DeletingState;
import ossje.dungeon2.utils.game.states.GeneratingState;
import ossje.dungeon2.utils.game.states.StartingState;
import ossje.dungeon2.utils.players.DungeonPlayer;
import ossje.dungeon2.utils.players.PlayerManager;

import java.util.ArrayList;
import java.util.List;

public class GameManager {
    @Getter private final List<DungeonPlayer> players;
    @Getter private final DungeonManager dungeonManager;
    @Getter private GameState currentState;
    @Getter private final Dungeon2 plugin;

    public GameManager(DungeonManager dungeonManager, Dungeon2 plugin, Player host) {
        this.plugin = plugin;
        this.players = new ArrayList<>();
        this.addPlayer(PlayerManager.getInstance().getPlayer(host));

        this.dungeonManager = dungeonManager;
        this.setState(new GeneratingState());
    }

    public void setState(GameState state) {

        if(currentState != null) {
            if (this.currentState.getClass() == state.getClass()) return;
            this.currentState.disable();
        }
        this.currentState = state;
        this.currentState.enable(this);
    }

    public void dungeonsetup(){
        if(players.isEmpty()) return;

        Player player = players.get(0).getBukkit();
        dungeonManager.gendungeon(player.getLocation(),
                new Location(player.getWorld(), player.getLocation().getX()+50, player.getLocation().getY(), player.getLocation().getZ()+50),
                12, 7, 15);

        dungeonManager.buildRooms();
        dungeonManager.buildHallways();
        dungeonManager.clearhallways();
    }


    //player logic

    public DungeonPlayer addPlayer(DungeonPlayer dungeonPlayer) {
        if(this.players.contains(dungeonPlayer)) return null;
        if(this.players.size() >= 4) return null; // Limit to 4 players
        this.players.add(dungeonPlayer);
        dungeonPlayer.setGameManager(this);

        if(this.currentState instanceof StartingState) {
            dungeonPlayer.getBukkit().teleport(dungeonManager.getSpawn());
        }

        return dungeonPlayer;
    }

    public void removePlayer(DungeonPlayer dungeonPlayer) {
    if (!this.players.contains(dungeonPlayer)) return;
    this.players.remove(dungeonPlayer);

    if(players.isEmpty()) this.setState(new DeletingState());
    }

}

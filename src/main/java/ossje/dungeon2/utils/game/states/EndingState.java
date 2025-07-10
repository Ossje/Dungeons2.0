package ossje.dungeon2.utils.game.states;

import org.bukkit.Location;
import ossje.dungeon2.utils.game.GameState;
import ossje.dungeon2.utils.game.tasks.StartingTask;
import ossje.dungeon2.utils.players.DungeonPlayer;

import java.util.List;

public class EndingState extends  GameState {
    @Override
    public void onEnable() {

        List<DungeonPlayer> players = this.manager.getPlayers();
        players.forEach(player->{
            player.setInGame(true);
            player.setGameManager(null);
            player.getBukkit().teleport(new Location(player.getBukkit().getWorld(), 28, -60, 73));
        });
    }
}

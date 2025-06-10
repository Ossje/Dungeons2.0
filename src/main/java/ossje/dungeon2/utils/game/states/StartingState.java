package ossje.dungeon2.utils.game.states;

import lombok.Getter;
import org.bukkit.Bukkit;
import ossje.dungeon2.utils.game.GameState;
import ossje.dungeon2.utils.game.tasks.StartingTask;
import ossje.dungeon2.utils.players.DungeonPlayer;

import java.util.List;

public class StartingState extends GameState {
    @Getter
    private StartingTask task;
    @Override

    public void onEnable(){
        this.task = new StartingTask(manager);
        this.task.runTaskTimer(this.manager.getPlugin(), 0L, 20L);

        List<DungeonPlayer> players = this.manager.getPlayers();
        players.forEach(player->{
            player.getBukkit().teleport(this.manager.getDungeonManager().getSpawn());
        });
    }



}

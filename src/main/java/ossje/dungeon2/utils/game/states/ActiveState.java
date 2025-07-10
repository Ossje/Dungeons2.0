package ossje.dungeon2.utils.game.states;

import lombok.Getter;
import ossje.dungeon2.utils.game.GameState;
import ossje.dungeon2.utils.game.tasks.GameTask;
import ossje.dungeon2.utils.game.tasks.StartingTask;
import ossje.dungeon2.utils.players.DungeonPlayer;

import java.util.List;

public class ActiveState extends GameState {

    @Getter private GameTask task;

    @Override
    public void onEnable(){
        this.task = new GameTask(this.manager);
        this.task.runTaskTimer(this.manager.getPlugin(), 0L, 20L);

        List<DungeonPlayer> players = this.manager.getPlayers();
        players.forEach(player->{
            player.setInGame(true);
        });
    }
}

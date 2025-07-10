package ossje.dungeon2.utils.players.scoreboard;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scoreboard.*;
import ossje.dungeon2.utils.game.GameManager;
import ossje.dungeon2.utils.game.states.EndingState;
import ossje.dungeon2.utils.players.DungeonPlayer;
import ossje.dungeon2.utils.players.PlayerManager;

import java.util.List;
import java.util.Set;


@RequiredArgsConstructor
public class ScoreboardTask extends BukkitRunnable {

    @Override
    public void run(){
        Set<DungeonPlayer> players = PlayerManager.getInstance().getOnlinePlayers();
        players.forEach(OnScoreboardSet::setScoreboard);
    }

}

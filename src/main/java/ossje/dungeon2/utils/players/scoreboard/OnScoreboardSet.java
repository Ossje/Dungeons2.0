package ossje.dungeon2.utils.players.scoreboard;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.scoreboard.*;

import ossje.dungeon2.utils.game.GameState;
import ossje.dungeon2.utils.game.states.ActiveState;
import ossje.dungeon2.utils.players.DungeonPlayer;
import ossje.dungeon2.utils.players.PlayerManager;

public class OnScoreboardSet implements Listener {

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        setScoreboard(PlayerManager.getInstance().getPlayer(event.getPlayer()));
    }

    public static void setScoreboard(DungeonPlayer player) {
        ScoreboardManager manager = Bukkit.getScoreboardManager();
        if (manager == null) return;

        Scoreboard scoreboard = manager.getNewScoreboard();
        Objective objective = scoreboard.registerNewObjective("dungeon", "dummy", ChatColor.GREEN + "Dungeons 2.0");
        objective.setDisplaySlot(DisplaySlot.SIDEBAR);

        Score coins = objective.getScore(ChatColor.GOLD + "Coins: " + player.getCoins());
        coins.setScore(1);

        Score greeting = objective.getScore(ChatColor.YELLOW + "Hello " + player.getBukkit().getName());
        greeting.setScore(2);

        if(player.isInGame()){
            if(player.getGameManager() == null) {
                player.getBukkit().setScoreboard(scoreboard);
                return;
            }

            GameState state = player.getGameManager().getCurrentState();

            if(!(state instanceof ActiveState activeState)) {
                player.getBukkit().setScoreboard(scoreboard);
                return;
            };

            Score gameTimer = objective.getScore(activeState.getTask().getTime() + "s");
            gameTimer.setScore(0);
        }

        player.getBukkit().setScoreboard(scoreboard);
    }
}
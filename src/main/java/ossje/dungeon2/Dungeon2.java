package ossje.dungeon2;

import com.sun.source.util.TaskEvent;
import org.bukkit.plugin.java.JavaPlugin;
import ossje.dungeon2.commands.DungeonAreaCreateCommand;
import ossje.dungeon2.commands.DungeonGenCommand;
import ossje.dungeon2.database.Database;
import ossje.dungeon2.utils.game.tasks.StartingTask;
import ossje.dungeon2.utils.players.Listeners.PlayerJoinListener;
import ossje.dungeon2.utils.players.Listeners.PlayerLeaveListener;
import ossje.dungeon2.utils.players.scoreboard.OnScoreboardSet;
import ossje.dungeon2.utils.players.scoreboard.ScoreboardTask;

import java.sql.SQLException;

public final class Dungeon2 extends JavaPlugin {

    private ScoreboardTask scoreboardTask;

    @Override
    public void onEnable() {
        try {
            Database.init();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        getCommand("gendungeon").setExecutor(new DungeonGenCommand(this));
        getCommand("dungeonarea").setExecutor(new DungeonAreaCreateCommand());
        getServer().getPluginManager().registerEvents(new PlayerJoinListener(), this);
        getServer().getPluginManager().registerEvents(new PlayerLeaveListener(), this);
        getServer().getPluginManager().registerEvents(new OnScoreboardSet(), this);

        this.scoreboardTask = new ScoreboardTask();
        scoreboardTask.runTaskTimer(this, 0L, 20L);
    }

    @Override
    public void onDisable() {
        try {
            Database.close();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}

package ossje.dungeon2;

import org.bukkit.plugin.java.JavaPlugin;
import ossje.dungeon2.commands.DungeonGenCommand;
import ossje.dungeon2.utils.players.Listeners.PlayerJoinListener;
import ossje.dungeon2.utils.players.Listeners.PlayerLeaveListener;

public final class Dungeon2 extends JavaPlugin {

    @Override
    public void onEnable() {
        getCommand("gendungeon").setExecutor(new DungeonGenCommand(this));
        getServer().getPluginManager().registerEvents(new PlayerJoinListener(), this);
        getServer().getPluginManager().registerEvents(new PlayerLeaveListener(), this);
    }

    @Override
    public void onDisable() {
        // Plugin shutdown log
    }
}

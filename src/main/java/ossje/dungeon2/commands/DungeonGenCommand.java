package ossje.dungeon2.commands;

import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import ossje.dungeon2.Dungeon2;
import ossje.dungeon2.utils.dugeongen.DungeonManager;
import ossje.dungeon2.utils.game.GameManager;
import ossje.dungeon2.utils.players.PlayerManager;

public class DungeonGenCommand implements CommandExecutor {
    private final Dungeon2 plugin;

    public DungeonGenCommand(Dungeon2 plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        if(!(commandSender instanceof Player player)) return false;


        DungeonManager manager = new DungeonManager();
        GameManager gameManager = new GameManager(manager, plugin, player);
        PlayerManager.getInstance().getPlayer(player).setGameManager(gameManager);

        return true;
    }
}

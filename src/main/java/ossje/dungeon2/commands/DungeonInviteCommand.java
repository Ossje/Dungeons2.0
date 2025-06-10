package ossje.dungeon2.commands;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import ossje.dungeon2.utils.dugeongen.DungeonManager;
import ossje.dungeon2.utils.players.DungeonPlayer;
import ossje.dungeon2.utils.players.PlayerManager;

public class DungeonInviteCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        if(!(commandSender instanceof Player player)) return false;
        DungeonPlayer host = PlayerManager.getInstance().getPlayer(player);
        if(strings[0] == null || strings[0].isEmpty()) {
            player.sendMessage("Please specify a player to invite.");
            return false;
        }

        DungeonPlayer dungeonPlayer = PlayerManager.getInstance().getPlayer(Bukkit.getPlayer(strings[0]));
        if(dungeonPlayer == null){
            player.sendMessage("Player not found or not online.");
            return false;
        }

        if(host.getGameManager() == null){
            player.sendMessage("You are not in a game.");
            return false;
        }

        if(dungeonPlayer.getGameManager() != null) {
            player.sendMessage("This player is already in a game.");
            return false;
        }

        if(host.getGameManager().addPlayer(dungeonPlayer) == null){
            player.sendMessage(ChatColor.RED + "This Game is full.");
        }


        return true;
    }
}

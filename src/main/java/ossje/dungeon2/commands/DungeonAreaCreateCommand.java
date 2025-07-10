package ossje.dungeon2.commands;

import com.j256.ormlite.dao.Dao;
import com.mysql.cj.xdevapi.Session;
import com.sk89q.worldedit.IncompleteRegionException;
import com.sk89q.worldedit.LocalSession;
import com.sk89q.worldedit.bukkit.WorldEditPlugin;
import com.sk89q.worldedit.regions.Region;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import ossje.dungeon2.database.Database;
import ossje.dungeon2.database.dungeon.DungeonPlaces;
import ossje.dungeon2.utils.dugeongen.DungeonPlaceManager;


public final class DungeonAreaCreateCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if(!(sender instanceof Player player)) return false;
        if (args.length < 2) {
            player.sendMessage("§cUsage: /dungeonarea (remove id | create id)");
            return false;
        }
        DungeonPlaceManager dp = DungeonPlaceManager.getInstance();
        if(args[0].equalsIgnoreCase("remove")) {
            DungeonPlaces place = dp.getDungeon(args[1]);
            if(place == null) {
                player.sendMessage(ChatColor.RED + "Dungeon place not found!");
                return false;
            }
            dp.removePlace(place);
            player.sendMessage(ChatColor.GREEN + "Dungeon place removed successfully!");
            return true;
        }

        Region region;
        if(args[0].equalsIgnoreCase("create")) {
            WorldEditPlugin wep = (WorldEditPlugin) Bukkit.getPluginManager().getPlugin("WorldEdit");
            LocalSession session = wep.getSession(player);
            try {
                region = session.getSelection(session.getSelectionWorld());
            } catch (IncompleteRegionException e) {
                player.sendMessage(ChatColor.RED + "You must select a region first!");
                return false;
            }


            try {
                Dao<DungeonPlaces, String> dao = Database.getPlacesDao();
                DungeonPlaces place = new DungeonPlaces(args[1], region.getMinimumPoint(), region.getMaximumPoint());

                dao.create(place);

                dp.addPlace(place);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return false;
    }
}

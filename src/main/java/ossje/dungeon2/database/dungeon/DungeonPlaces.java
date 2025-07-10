package ossje.dungeon2.database.dungeon;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;
import com.sk89q.worldedit.math.BlockVector3;
import lombok.Data;
import lombok.Getter;
import org.bukkit.Location;
import org.bukkit.util.BlockVector;


@DatabaseTable(tableName = "dungeon_places")
public class DungeonPlaces {
   @Getter @DatabaseField(id = true) private String id;
    @Getter @DatabaseField private int  minX;
    @Getter @DatabaseField private int  minY;
    @Getter @DatabaseField private int  minZ;

    @Getter @DatabaseField private int  maxX;
    @Getter @DatabaseField private int  maxY;
    @Getter @DatabaseField private int  maxZ;



    public DungeonPlaces() {
        // Default constructor required by ORMLite
    }

    public DungeonPlaces(String id, BlockVector3 min, BlockVector3 max) {
        this.id = id;
        this.minX = min.getBlockX();
        this.minY = min.getBlockY();
        this.minZ = min.getBlockZ();
        this.maxX = max.getBlockX();
        this.maxY = max.getBlockY();
        this.maxZ = max.getBlockZ();
    }
}

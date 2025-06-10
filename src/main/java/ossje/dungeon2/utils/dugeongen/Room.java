package ossje.dungeon2.utils.dugeongen;


import lombok.Getter;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.block.CreatureSpawner;
import org.bukkit.entity.EntityType;
import org.bukkit.spawner.Spawner;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public final class Room {
    @Getter
    private final int length;
    @Getter
    private final int width;
    @Getter
    private final int x; // start x
    @Getter
    private final int z;// start z
    @Getter
    private final int y;
    @Getter
    private final World world;
    @Getter
    private final List<Spawner> spawners;

    public Room(int length, int width, int x, int z, int y, World world) {
        this.length = length;
        this.width = width;
        this.z = z;
        this.x = x;
        this.y = y;
        this.world = world;

        spawners = new ArrayList<Spawner>();
    }

    public void buildInterior() {
        int spawnersLength = Math.abs((length-3) / 3);
        int spawnersWidth = Math.abs((width-3) / 3);

        for (int i = 0; i < spawnersLength; i++) {
            for (int j = 0; j < spawnersWidth; j++) {
                int spawnX = x + 4 + (j * 3);
                int spawnZ = z + 4 + (i * 3);
                Location spawnLoc = new Location(world, spawnX, y+1, spawnZ);
                placeSpawner(spawnLoc);
            }
        }
    }

    public void placeSpawner(Location location) {
        Block block = location.getBlock();
        block.setType(Material.SPAWNER);

        CreatureSpawner spawner = (CreatureSpawner) block.getState();
        spawner.setSpawnedType(EntityType.ZOMBIE);
        spawner.setSpawnCount(2);
        spawner.setMaxNearbyEntities(Math.abs((length + width ) /2));
        spawner.update();

        spawners.add(spawner);
    }
}

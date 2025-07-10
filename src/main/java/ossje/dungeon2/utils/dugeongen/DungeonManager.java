package ossje.dungeon2.utils.dugeongen;

import lombok.Getter;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import ossje.dungeon2.database.dungeon.DungeonPlaces;

import java.util.*;

public class DungeonManager {
    private Location start;
    private Location end;
    @Getter
    private Location spawn;
    int roomMinSize;
    int roomMaxSize;

    Room startroom;

    private DungeonPlaces place;


    List<Room> roomList = new ArrayList<>();
    List<HallwayWrapper> connections = new ArrayList<>();

    public void gendungeon(Location start, Location end, int maxRoom, int roomMinSize, int roomMaxSize, DungeonPlaces place){
        this.place = place;
        this.start = start;
        this.end = end;
        this.roomMinSize = roomMinSize;
        this.roomMaxSize = roomMaxSize;

        Random random = new Random();

        for(int i = 0; i < 1000; i++){
            int width = random.nextInt(roomMaxSize - roomMinSize + 1) + roomMinSize;
            int height = random.nextInt(roomMaxSize - roomMinSize + 1) + roomMinSize;

            int x = random.nextInt(this.start.getBlockX(), this.end.getBlockX()-width);
            int z = random.nextInt(this.start.getBlockZ(), this.end.getBlockZ()-height);;

            Room room = new Room(height, width, x, z, (int)start.getY(), start.getWorld());

            boolean placeTaken = false;

            for(Room room1 : roomList) {
                if(!roomOverlapping(room1, room)) continue;
                placeTaken = true;
                break;
            }

            if(placeTaken) continue;
            roomList.add(room);
            if(startroom == null) {
                startroom = room;
                spawn = new Location(start.getWorld(), room.getX() + (double) room.getWidth() / 2, start.getBlockY() + 1, room.getZ() + (double) room.getLength() / 2);
            }

            if(roomList.size() >= maxRoom) break;
        }
    }

    public void buildRooms(){
        for(Room room : roomList){
            int roomWidth = room.getWidth();
            int roomLength = room.getLength();
            int roomHeight = 6;
            int startX = room.getX();
            int startY = start.getBlockY();
            int startZ = room.getZ();

            for(int i = 0;  i < room.getWidth(); i++){
                for(int j = 0; j < room.getLength(); j++){
                    Location location = new Location(start.getWorld(), room.getX()+i, start.getY(), room.getZ()+j);
                    location.getBlock().setType(Material.STONE_BRICKS);
                }
            }
            for (int i = 0; i < roomWidth; i++) {
                for (int j = 0; j < roomLength; j++) {
                    Location location = new Location(start.getWorld(), startX + i, startY, startZ + j);
                    location.getBlock().setType(Material.STONE_BRICKS);
                }
            }

            for (int i = 0; i < roomWidth; i++) {
                for (int j = 0; j < roomHeight; j++) {

                    Location northWall = new Location(start.getWorld(), startX + i, startY + j, startZ);
                    northWall.getBlock().setType(Material.STONE_BRICKS);

                    Location southWall = new Location(start.getWorld(), startX + i, startY + j, startZ + roomLength - 1);
                    southWall.getBlock().setType(Material.STONE_BRICKS);
                }
            }

            for (int i = 0; i < roomLength; i++) {
                for (int j = 0; j < roomHeight; j++) {

                    Location eastWall = new Location(start.getWorld(), startX + roomWidth - 1, startY + j, startZ + i);
                    eastWall.getBlock().setType(Material.STONE_BRICKS);

                    Location westWall = new Location(start.getWorld(), startX, startY + j, startZ + i);
                    westWall.getBlock().setType(Material.STONE_BRICKS);
                }
            }

            for (int i = 0; i < roomWidth; i++) {
                for (int j = 0; j < roomLength; j++) {
                    Location ceiling = new Location(start.getWorld(), startX + i, startY + roomHeight, startZ + j);
                    ceiling.getBlock().setType(Material.STONE_BRICKS);
                }
            }

            if(room.equals(startroom)) continue;
            room.buildInterior();
        }
    }

    public void buildHallways() {
        List<Room> unconnectedRooms = new ArrayList<>(roomList);
        Set<Room> connectedRooms = new HashSet<>();

        Room startRoom = unconnectedRooms.get(0);
        connectedRooms.add(startRoom);
        unconnectedRooms.remove(startRoom);

        while (!unconnectedRooms.isEmpty()) {
            Room closestRoom = null;
            Room currentRoom = null;
            double closestDistance = Double.MAX_VALUE;

            for (Room connected : connectedRooms) {
                for (Room unconnected : unconnectedRooms) {
                    double distance = calculateDistance(connected, unconnected);
                    if (distance < closestDistance) {
                        closestDistance = distance;
                        closestRoom = unconnected;
                        currentRoom = connected;
                    }
                }
            }

            if (currentRoom != null) {
                createHallways(currentRoom, closestRoom);
                connections.add(new HallwayWrapper(currentRoom, closestRoom));
                connectedRooms.add(closestRoom);
                unconnectedRooms.remove(closestRoom);
            }
        }
    }

    private double calculateDistance(Room room1, Room room2) {
        int x1 = room1.getX() + room1.getWidth() / 2;
        int z1 = room1.getZ() + room1.getLength() / 2;
        int x2 = room2.getX() + room2.getWidth() / 2;
        int z2 = room2.getZ() + room2.getLength() / 2;

        return Math.sqrt(Math.pow(x2 - x1, 2) + Math.pow(z2 - z1, 2));
    }

    private void createHallways(Room room1, Room room2) {
        int hallwayHeight = 7;  // vertical height
        int hallwayHalfWidth = 1;

        int x1 = room1.getX() + room1.getWidth() / 2;
        int z1 = room1.getZ() + room1.getLength() / 2;
        int x2 = room2.getX() + room2.getWidth() / 2;
        int z2 = room2.getZ() + room2.getLength() / 2;

        // Build the X segment of the hallway
        buildHallwayLine(x1, z1, x2, z1, hallwayHeight, hallwayHalfWidth);

        // Build the Z segment of the hallway
        buildHallwayLine(x2, z1, x2, z2, hallwayHeight, hallwayHalfWidth);

    }

    public void clearhallways() {
        int hallwayHeight = 7;  // vertical height

        connections.forEach((hallway) -> {
            System.out.println("HAllway cleared");
            Room key = hallway.room;
            Room value = hallway.room2;
            int x1 = key.getX() + key.getWidth() / 2;
            int z1 = key.getZ() + key.getLength() / 2;
            int x2 = value.getX() + value.getWidth() / 2;
            int z2 = value.getZ() + value.getLength() / 2;

            for (int x = Math.min(x1, x2) - 1; x <= Math.max(x1, x2); x++) {
                for (int y = start.getBlockY(); y < start.getBlockY() + hallwayHeight; y++) {
                    for (int offset = -1; offset <= 1; offset++) {
                        Location loc = new Location(start.getWorld(), x, y, z1 + offset);
                        if (y == start.getBlockY()) {
                            loc.getBlock().setType(Material.STONE_BRICKS);
                        } else if (y == start.getBlockY() + hallwayHeight - 1) {
                            loc.getBlock().setType(Material.STONE_BRICKS);
                        } else {
                            loc.getBlock().setType(Material.AIR); // Interior air
                        }
                    }
                }
            }

            for (int z = Math.min(z1, z2) - 1; z <= Math.max(z1, z2); z++) {
                for (int y = start.getBlockY(); y < start.getBlockY() + hallwayHeight; y++) {
                    for (int offset = -1; offset <= 1; offset++) {
                        Location loc = new Location(start.getWorld(), x2 + offset, y, z);
                        if (y == start.getBlockY()) {
                            loc.getBlock().setType(Material.STONE_BRICKS); // Floor
                        } else if (y == start.getBlockY() + hallwayHeight - 1) {
                            loc.getBlock().setType(Material.STONE_BRICKS); // Ceiling
                        } else {
                            loc.getBlock().setType(Material.AIR); // Interior air
                        }
                    }
                }
            }
        });
    }

    private void buildHallwayLine(int xStart, int zStart, int xEnd, int zEnd, int height, int halfWidth) {
        int minX = Math.min(xStart, xEnd);
        int maxX = Math.max(xStart, xEnd);
        int minZ = Math.min(zStart, zEnd);
        int maxZ = Math.max(zStart, zEnd);

        for (int x = minX - halfWidth - 1; x <= maxX + halfWidth + 1; x++) {
            for (int z = minZ - halfWidth - 1; z <= maxZ + halfWidth + 1; z++) {
                for (int y = start.getBlockY(); y < start.getBlockY() + height; y++) {
                    boolean isWallEdgeX = x == minX - halfWidth - 1 || x == maxX + halfWidth + 1;
                    boolean isWallEdgeZ = z == minZ - halfWidth - 1 || z == maxZ + halfWidth + 1;
                    boolean isWallEdge = isWallEdgeX || isWallEdgeZ;

                    Location loc = new Location(start.getWorld(), x, y, z);

                    if (isInsideAnyRoom(loc) && y > start.getBlockY()) {
                        // Skip walls and air inside rooms, only build floor
                        continue;
                    }

                    if (y == start.getBlockY()) {
                        loc.getBlock().setType(Material.STONE_BRICKS);  // floor
                    } else if (y == start.getBlockY() + height - 1) {
                        loc.getBlock().setType(Material.STONE_BRICKS);  // ceiling
                    } else if (isWallEdge) {
                        loc.getBlock().setType(Material.STONE_BRICKS);  // side walls
                    } else {
                        loc.getBlock().setType(Material.AIR);  // interior air
                    }
                }
            }
        }
    }

    private boolean isInsideAnyRoom(Location loc) {
        int x = loc.getBlockX();
        int z = loc.getBlockZ();

        for (Room room : roomList) {
            int roomX1 = room.getX() + 1;
            int roomZ1 = room.getZ() + 1;
            int roomX2 = room.getX() + room.getWidth() - 1;
            int roomZ2 = room.getZ() + room.getLength() - 1;

            if (x >= roomX1 && x < roomX2 && z >= roomZ1 && z < roomZ2) {
                return true; // Block is inside the interior of this room
            }
        }
        return false; // Block is not inside any room
    }


    private boolean roomOverlapping(Room a, Room b){
        int ax1 = a.getX();
        int ay1 = a.getZ();
        int ax2 = a.getX() + a.getWidth()+1;
        int ay2 = a.getZ() + a.getLength()+1;
        int bx1 = b.getX();
        int by1 = b.getZ();
        int bx2 = b.getX() + b.getWidth ()+1;
        int by2 = b.getZ() + b.getLength()+1;

        return (ax1 < bx2 && ax2 > bx1 && ay1 < by2 && ay2 > by1);
    }

    public void EndGame() {
        DungeonPlaceManager.getInstance().deactivateDungeon(place);

        int minX = place.getMinX();
        int maxX = place.getMaxX();
        int minY = place.getMinY();
        int maxY = minY + 10; // 10 blocks high
        int minZ = place.getMinZ();
        int maxZ = place.getMaxZ();

        for (int x = minX; x <= maxX; x++) {
            for (int y = minY; y <= maxY; y++) {
                for (int z = minZ; z <= maxZ; z++) {
                    Block block = start.getWorld().getBlockAt(x, y, z);
                    block.setType(Material.AIR);
                }
            }
        }
    }

}

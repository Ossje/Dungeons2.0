package ossje.dungeon2.utils.dugeongen;

import com.j256.ormlite.dao.Dao;
import ossje.dungeon2.database.Database;
import ossje.dungeon2.database.dungeon.DungeonPlaces;

import java.sql.SQLException;
import java.util.*;

public class DungeonPlaceManager {
    private final HashMap<DungeonPlaces, Boolean> dungeonPlaces = new HashMap<>();
    private static DungeonPlaceManager instance;
    public static DungeonPlaceManager getInstance() {
        if (instance == null) {
            instance = new DungeonPlaceManager();
        }
        return instance;
    }

    public DungeonPlaceManager() {
        try{
            loadDungeonPlaces();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void loadDungeonPlaces() throws SQLException {
        Dao<DungeonPlaces, String> dungeonDao = Database.getPlacesDao();
        List<DungeonPlaces> allPlaces = dungeonDao.queryForAll();
        DungeonPlaceManager manager = DungeonPlaceManager.getInstance();
        allPlaces.forEach(manager::addPlace);
    }

    public void addPlace(DungeonPlaces place) {
        dungeonPlaces.put(place, false);
    }

    public void removePlace(DungeonPlaces place) {
        dungeonPlaces.remove(place);
    }

    public DungeonPlaces getDungeon(String id) {
        for (DungeonPlaces place : dungeonPlaces.keySet()) {
            if (place.getId().equals(id)) {
                return place;
            }
        }
        return null;
    }

    public void activateDungeon(DungeonPlaces place) {
        if (dungeonPlaces.containsKey(place)) {
            dungeonPlaces.put(place, true);
        }
    }

    public void deactivateDungeon(DungeonPlaces place) {
        if (dungeonPlaces.containsKey(place)) {
            dungeonPlaces.put(place, false);
        }
    }

    public DungeonPlaces getFirstFreeDungeon() {
        for (Map.Entry<DungeonPlaces, Boolean> entry : dungeonPlaces.entrySet()) {
            if (!entry.getValue()) {
                return entry.getKey();
            }
        }
        return null;
    }
}

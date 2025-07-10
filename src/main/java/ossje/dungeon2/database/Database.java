package ossje.dungeon2.database;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import com.j256.ormlite.jdbc.JdbcConnectionSource;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;
import lombok.Getter;
import ossje.dungeon2.database.dungeon.DungeonPlaces;
import ossje.dungeon2.utils.players.DungeonPlayer;

import java.sql.SQLException;

public class Database {
    private static ConnectionSource connectionSource;
    @Getter
    private static Dao<DungeonPlayer, String> playerDao;
    @Getter
    private static Dao<DungeonPlaces, String> placesDao;

    public static void init() throws SQLException {
        connectionSource = new JdbcConnectionSource(
                "jdbc:mysql://localhost:3306/minecraft?useSSL=false&allowPublicKeyRetrieval=true", "mcuser", "mcpass"
        );

        TableUtils.createTableIfNotExists(connectionSource, DungeonPlayer.class);
        TableUtils.createTableIfNotExists(connectionSource, DungeonPlaces.class);

        playerDao = DaoManager.createDao(connectionSource, DungeonPlayer.class);
        placesDao = DaoManager.createDao(connectionSource, DungeonPlaces.class);
    }

    public static void close() throws Exception {
        connectionSource.close();
    }
}

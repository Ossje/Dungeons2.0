package ossje.dungeon2.utils.game;

import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;

public abstract class GameState implements Listener {
    protected GameManager manager;

    public void enable(GameManager manager){
        this.manager = manager;
        this.manager.getPlugin().getServer().getPluginManager().registerEvents(this, manager.getPlugin());
        this.onEnable();
    }

    public void disable(){
        HandlerList.unregisterAll(this);

        this.onDisable();

    }

    public void onEnable(){

    }

    public void onDisable(){

    }
}

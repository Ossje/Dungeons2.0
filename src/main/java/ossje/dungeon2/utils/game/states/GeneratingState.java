package ossje.dungeon2.utils.game.states;

import org.bukkit.Bukkit;
import ossje.dungeon2.utils.game.GameState;

public class GeneratingState extends GameState {

    @Override
    public void onEnable(){
        this.manager.dungeonsetup();
        this.manager.setState(new StartingState());
    }


}
